package application.pages;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import application.main.GameStage;
import application.pages.Menu;
import application.sprite.Castle;
import javafx.application.Platform;
import java.io.*;
import java.net.Socket;

public class ChatOverlay extends Pane{
	private TextArea chatArea;
    private TextArea inputField;
    public static Socket socket;
    private GameStage gamestage;


	public ChatOverlay(GameStage gamestage) {
		setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Set the background color and transparency
        setPrefSize(432, 768); // Set the preferred size of the overlay screen
        this.gamestage = gamestage;
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

	private boolean isNotPureInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return false; // It is a pure integer
	    } catch (NumberFormatException e) {
	        return true; // It is not a pure integer
	    }
	}

    private void connectToChatServer() {
        try {
            socket = new Socket("localhost", 6000);

            // Create a separate thread to handle incoming messages
            Thread receiveThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) {
                    	if(!message.startsWith("points: ") && !message.startsWith("confirm")) {
                    		chatArea.appendText(message + "\n");
                    	} else {
		                	System.out.println(message);
		                	if (message.startsWith("points: ")) {
		                        String scoreString = message.substring(8);
		                        int score = Integer.parseInt(scoreString);
		                        if (score > gamestage.getGameTimer().getCastle().getHighestScore()) {
		                        	gamestage.getGameTimer().getCastle().setHighestScore(score);
		                            System.out.println("New highest score: " + gamestage.getGameTimer().getCastle().getHighestScore());
		                        }
		                    } else {
		                    	if (message.startsWith("confirm")) {
		                    		Platform.runLater(() -> {
		                    		    gamestage.setStage((gamestage.getCurrentStage()));
		                    		});
//		                    		gamestage.setStage(gamestage.getCurrentStage());
//		                    		gamestage.getGameTimer().start();
//		                    		gamestage.getStage().show();
			                    }
		                    }
//                	                chatArea.appendText(message + "\n");

                    	}

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
                            writer.write(Menu.userName +": "+message);
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

//    public Socket getSocket() {
//    	return this.socket;
//    }

}
