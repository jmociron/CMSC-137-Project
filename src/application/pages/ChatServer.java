package application.pages;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.startServer();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Server started. Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received message: " + message);
                broadcastMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client from the list when they disconnect
            clients.remove(clientSocket);
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
        }
    }

    private void broadcastMessage(String message) {
        for (Socket client : clients) {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
