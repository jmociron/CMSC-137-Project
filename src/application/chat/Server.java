package application.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept(); // waits for client to connect
                System.out.println("Player has connected to the server!");
                ClientHandler clientHandler = new ClientHandler(socket); // responsible for connecting to the client

                Thread thread = new Thread(clientHandler); // creates a thread for each client to allow for multiple clients to connect
                thread.start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeServerSocket(){
        try{ // checks if server is not null to avoid null exception
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(4000);
        Server server = new Server(serverSocket);
        server.startServer();

        System.out.println("Server has started!");


    }

}
