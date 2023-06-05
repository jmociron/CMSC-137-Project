package application.pages;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import application.main.GameStage;
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

    private void connectToChatServer() {
        try {
            socket = new Socket("localhost", 5050);

            // Create a separate thread to handle incoming messages
            Thread receiveThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) {
                        if(!message.startsWith("points: ") && !message.startsWith("confirm") && !message.startsWith("pause")) {
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
                                } else {
                                    if (message.startsWith("pause")) {
                                        Platform.runLater(() -> {
                                            if(!gamestage.isPaused()) {
                                                gamestage.getGameTimer().stop();
                                                gamestage.startPause = System.nanoTime();
                                                gamestage.pauseButton.setImage(GameStage.resume);
                                                gamestage.setPaused(true);
                                                gamestage.getOverlayPane().setVisible(true);
                                                gamestage.pauseButton.toFront();
                                            } else {
                                                gamestage.endPause = System.nanoTime();
                                                gamestage.getGameTimer().addTime(gamestage.endPause-gamestage.startPause);
                                                gamestage.getGameTimer().start();
                                                gamestage.pauseButton.setImage(GameStage.pause);
                                                gamestage.setPaused(false);
                                                gamestage.getOverlayPane().setVisible(false);
                                                gamestage.pauseButton.toFront();
                                            }
                                        });
                                    }
                                }
                            }
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

}
