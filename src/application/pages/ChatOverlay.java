package application.pages;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

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
    }

}
