package application.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // for keeping track of all client instances
    private Socket socket; // socket passed from the server class to establish connection
    private BufferedReader bufferedReader; // used to read messages 
    private BufferedWriter bufferedWriter; // used to send messages
    private String clientUsername;


    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.clientUsername = bufferedReader.readLine(); // waits for the client's message (from the sendMessage function in Client.java)
            clientHandlers.add(this); // adds client to the array list
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if(bufferedReader != null){ bufferedReader.close(); }
            if(bufferedWriter != null){ bufferedWriter.close(); }
            if(socket != null){ socket.close(); } // closes socket, as well as input/output streams
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message) {

        // loops through all clients and sends the message
        for(ClientHandler clientHandler : clientHandlers){ // for each client handler in the array list
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){ // checks if receiver is not the sender
                    clientHandler.bufferedWriter.write(message); // sends the message
                    clientHandler.bufferedWriter.newLine(); // explicitly sends a \n to end writing
                    clientHandler.bufferedWriter.flush(); // message has to be manually flushed to be sent
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this); // removes this client from the array
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!"); // informs rest of the clients
    }

    @Override
    public void run(){

        // the run method allows for the program to separately wait for messages
        // otherwise, program will be stuck only waiting for messages (and not running the game)

        while(socket.isConnected()){
            try{
                String messageFromClient = bufferedReader.readLine(); // waits for message from the client
                broadcastMessage(messageFromClient); // calls method that sends message to all clients
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

}
