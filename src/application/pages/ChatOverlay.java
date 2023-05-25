package application.pages;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.Socket;

public class ChatOverlay extends Pane{
	private TextArea chatArea;
    private TextArea inputField;

	public ChatOverlay() {
		setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Set the background color and transparency
        setPrefSize(432, 768); // Set the preferred size of the overlay screen
        System.out.println("haYS");
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(500);
        chatArea.setWrapText(true);
        chatArea.setFocusTraversable(false);
        chatArea.setLayoutX(10);
        chatArea.setLayoutY(100);
        chatArea.setPrefWidth(412);

        inputField = new TextArea();
        inputField.setPrefHeight(70);
        inputField.setWrapText(true);
        inputField.setLayoutX(10);
        inputField.setLayoutY(630);
        inputField.setPrefWidth(412);

        getChildren().addAll(chatArea, inputField);

        connectToChatServer();

    }

	public void focusInputField() {
        inputField.requestFocus();
    }

    private void connectToChatServer() {
        try {
            Socket socket = new Socket("localhost", 12345);

            // Create a separate thread to handle incoming messages
            Thread receiveThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) {
                        chatArea.appendText(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // Add event handler to send messages when Enter is pressed
            inputField.setOnKeyPressed(event -> {
                if (event.getCode().toString().equals("ENTER")) {
                    String message = inputField.getText().trim();
                    if (!message.isEmpty()) {
                        try {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            writer.write(message);
                            writer.newLine();
                            writer.flush();

                            inputField.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
