package application.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

    private List<Socket> clients = new ArrayList<>();
    private Map<Socket, Integer> playerPoints = new HashMap<>();

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

                if (message.startsWith("points: ")) {
                    try {
                        int newPoints = Integer.parseInt(message.substring(8));
                        playerPoints.put(clientSocket, newPoints);
                        System.out.println("Points updated for client " + clientSocket.getInetAddress().getHostAddress() + ": " + newPoints);
                        broadcastPointsToClients();
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid points format: " + message);
                    }
                } else {
                    broadcastMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client from the list and playerPoints map when they disconnect
            clients.remove(clientSocket);
            playerPoints.remove(clientSocket);
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

    private void broadcastPointsToClients() {
        for (Socket client : clients) {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                for (Map.Entry<Socket, Integer> entry : playerPoints.entrySet()) {
                    Socket playerSocket = entry.getKey();
                    int points = entry.getValue();
//                    String message = "Player " + playerSocket.getInetAddress().getHostAddress() + " points: " + points;
                    String message = "points: " + String.valueOf(points);
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
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
