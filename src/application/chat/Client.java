package application.chat;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username; // represents the client instance

    public Client(Socket socket, String username){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            /* TODO: Implement writing messages from GUI (Code below only reads messages from the terminal). */
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader != null){ bufferedReader.close(); }
            if(bufferedWriter != null){ bufferedWriter.close(); }
            if(socket != null){ socket.close(); } // closes socket, as well as input/output streams
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForMessage(){ // waiting for messages broadcasted and prints to the console
        new Thread(new Runnable(){
            @Override
            public void run() {
                String message;
                while(socket.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        /* TODO: Implement reading messages from GUI (Code below only prints messages to the terminal). */
                        System.out.println(message);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }

            }
        }).start();;

    }

    public static void main(String[] args) throws UnknownHostException, IOException{

        /* TODO: Implement writing messages from GUI (Code below only reads messages from the terminal). */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1236); // make sure port is same with Server.java
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();

    }
}
