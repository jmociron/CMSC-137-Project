package application.pages;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import application.main.GameTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOver {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private Text text;
	private VBox vbox;
	private int gameOverNum;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 408;
	public static final int WINDOW_WIDTH = 768;
	public static final String EXIT = "EXIT";
	
	public GameOver(int gameOverNum) {
		StackPane root = new StackPane();
		this.gameOverNum = gameOverNum;
		this.scene = new Scene(root, GameOver.WINDOW_WIDTH, GameOver.WINDOW_HEIGHT);
		this.imgView = this.createBG();
		this.vbox = this.createVBox();

		root.getChildren().addAll(this.imgView, this.vbox);
	}

	private ImageView createBG() {
		Image bg = new Image("images/menu.png");
		ImageView view = new ImageView();
		view.setImage(bg);

		return view;
	}

    private VBox createVBox() {
    	VBox vbox = new VBox();

    	switch(gameOverNum){
	    	case GameTimer.LOSE_NUM:
	    		this.text = new Text("YOU LOSE!");
		        this.text.setX(250);
		        this.text.setY(0);
		        this.text.setFill(Color.web("#FFD7BD",1.0)); 
				Font winTitleFont = Font.font("Impact",FontWeight.EXTRA_BOLD,80);
                this.text.setFont(winTitleFont);
                this.text.setStroke(Color.web("#614635",1.0));
                this.text.setStrokeWidth(2);
				break;
			case GameTimer.WIN_NUM:
				this.text = new Text("YOU WIN!");
				this.text.setX(250);
				this.text.setY(0);
				this.text.setFill(Color.web("#FFD7BD",1.0)); 
				Font loseTitleFont = Font.font("Impact",FontWeight.EXTRA_BOLD,80);
				this.text.setFont(loseTitleFont);
				this.text.setStroke(Color.web("#614635",1.0));
				this.text.setStrokeWidth(2);
				break;
		}

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);


        Button b1 = new Button("    Exit    ");

        b1.setStyle("-fx-font: 15 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #FFFAFA; "
        + "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");
        
        vbox.getChildren().add(text);
        vbox.getChildren().add(b1);

        this.exitGame(b1);

        return vbox;
    }

	private void exitGame(Button btn) {
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ChatOverlay.socket.getOutputStream()));
					writer.write(EXIT);
					writer.newLine(); // marks end of message
					writer.flush(); // ensures message is sent
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("CMSC 137 Project");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	public Scene getScene(){
		return this.scene;
	}

}