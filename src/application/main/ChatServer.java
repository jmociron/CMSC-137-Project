package application.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

    private List<Socket> clients = new ArrayList<>(); // keeps track of all connected clients
    private Map<Socket, Integer> playerPoints = new HashMap<>(); // saves points per client

    // starts the server and waits for client connections
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.startServer();
    }

    public void startServer() {
        try {
            try (ServerSocket serverSocket = new ServerSocket(6062)) {
                System.out.println("Server started. Waiting for connections...");
                while (true) { // waits for client connections
                    Socket clientSocket = serverSocket.accept();
                    clients.add(clientSocket); // adds client to the list
                    System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                    Thread clientThread = new Thread(() -> handleClient(clientSocket)); // creates a new thread to handle each client
                    clientThread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            while ((message = reader.readLine()) != null) { // iterates through the message from the client until fully read
                System.out.println("Received message: " + message);
                if (message.startsWith("points: ")) {
                    try {
                        int newPoints = Integer.parseInt(message.substring(8));
                        playerPoints.put(clientSocket, newPoints);
                        System.out.println("Points updated for client " + clientSocket.getInetAddress().getHostAddress() + ": " + newPoints);
                        broadcastPoints();
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid points format: " + message);
                    }
                } else {
                    broadcastMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // remove the client from the list and playerPoints map when they disconnect
            clients.remove(clientSocket);
            playerPoints.remove(clientSocket);
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
        }
    }

    private void broadcastMessage(String message) {
        for (Socket client : clients) { // writes a message to all connected clients
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                writer.write(message);
                writer.newLine(); // marks end of message
                writer.flush(); // ensures message is sent
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastPoints() {
        for (Socket client : clients) {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                for (Map.Entry<Socket, Integer> entry : playerPoints.entrySet()) { // retrieves points per client
                    int points = entry.getValue();
                    String message = "points: " + String.valueOf(points);
                    writer.write(message);
                    writer.newLine(); // marks end of message
                    writer.flush(); // ensures message is sent
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<Socket, Integer> getPlayerPoints() {
        return playerPoints;
    }
}
