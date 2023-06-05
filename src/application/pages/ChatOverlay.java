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
            socket = new Socket("localhost", 6062);
            Thread receiveThread = new Thread(() -> { // create a separate thread to handle incoming messages
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) { // iterates through message until message is read
                        if (message.startsWith("points: ")) { // reads client score
                            String scoreString = message.substring(8);
                            int score = Integer.parseInt(scoreString);
                            if (score > gamestage.getGameTimer().getCastle().getHighestScore()) { // sets highest scorer
                                gamestage.getGameTimer().getCastle().setHighestScore(score);
                                System.out.println("New highest score: " + gamestage.getGameTimer().getCastle().getHighestScore());
                            }
                            continue;
                        }
                        if (message.startsWith("confirm")) {
                            Platform.runLater(() -> { // allows for code to be executed within the thread
                                gamestage.setStage((gamestage.getCurrentStage()));
                            });
                            continue;
                        }
                        if (message.startsWith("pause")) {
                            Platform.runLater(() -> { // allows for code to be executed within the thread
                                if(!gamestage.isPaused()) {
                                    gamestage.getGameTimer().stop();
                                    gamestage.startPause = System.nanoTime(); // saves time at pause for the game to revert to later
                                    gamestage.pauseButton.setImage(GameStage.resume);
                                    gamestage.setPaused(true);
                                    gamestage.getOverlayPane().setVisible(true);
                                    gamestage.pauseButton.toFront();
                                } else {
                                    gamestage.endPause = System.nanoTime();
                                    gamestage.getGameTimer().addTime(gamestage.endPause-gamestage.startPause); // reverts to time at pause
                                    gamestage.getGameTimer().start();
                                    gamestage.pauseButton.setImage(GameStage.pause);
                                    gamestage.setPaused(false);
                                    gamestage.getOverlayPane().setVisible(false); // removes overlay from view
                                    gamestage.pauseButton.toFront();
                                }
                            });
                            continue;
                        }
                        chatArea.appendText(message + "\n"); // reads chat message
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            inputField.setOnKeyPressed(event -> { // event handler to send messages when Enter is pressed
                if (event.getCode().toString().equals("ENTER")) {
                    String message = inputField.getText().trim();
                    if (!message.isEmpty()) { // sends message to all clients if message is not empty
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
