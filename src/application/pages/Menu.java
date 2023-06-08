package application.pages;
import application.main.GameStage;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Menu {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private Text text;
	private VBox vbox;
	private VBox userInputView;
	private GameStage gamestage;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 408;
	public static final int WINDOW_WIDTH = 768;
	public static final String CONFIRM = "CONFIRM";

	// VARIABLES:
	public static String userName = "";
	public static String hostIP = "";

	public Menu() {
		StackPane root = new StackPane();
		this.scene = new Scene(root, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);
		this.imgView = this.createBG();
		this.vbox = this.createVBox();
		this.userInputView = this.enterUsername(this.vbox,this);
		this.vbox.setVisible(false);
		root.getChildren().addAll(this.imgView, this.userInputView, this.vbox);
	}

	private ImageView createBG() {
		Image bg = new Image("images/menu.png");
		ImageView view = new ImageView();
		view.setImage(bg);

		return view;
	}

	private VBox enterUsername(VBox menu, Menu userInput) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(7));
        vbox.setSpacing(10);

		// Header
		this.text = new Text("Enter username:");
		this.text.setX(0);
        this.text.setY(0);
        this.text.setFill(Color.web("#FFD7BD",1.0));
		Font titleFont = Font.font("Impact",FontWeight.EXTRA_BOLD,35);
		this.text.setFont(titleFont);
		this.text.setStroke(Color.web("#614635",1.0));
		this.text.setStrokeWidth(1);

		// Text field for user input
		TextArea inputField;
		inputField = new TextArea();
		inputField.setStyle("-fx-font: 25 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #000000; " + "-fx-border-color:#369DC6; -fx-border-width: 3px;");
		inputField.setPrefHeight(10);

		// Confirm button
		Button enterButton = new Button("    Confirm    ");
		enterButton.setStyle("-fx-font: 15 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #FFFAFA; " + "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");

		// Prompt for empty username
		Text checkUsername = new Text();
		checkUsername.setFill(Color.web("#FFFFFF",1.0));
		Font checkFont = Font.font("Impact",FontWeight.EXTRA_BOLD,20);
		checkUsername.setFont(checkFont);
		checkUsername.setStroke(Color.web("#256B55",1.0));
		checkUsername.setStrokeWidth(1);

		// Prompt for host IP
		Text enterHost = new Text("Enter host IP:");
		enterHost.setFill(Color.web("#FFD7BD",1.0));
		Font hostFont = Font.font("Impact",FontWeight.EXTRA_BOLD,30);
		enterHost.setFont(hostFont);
		enterHost.setStroke(Color.web("#614635",1.0));
		enterHost.setStrokeWidth(1);

		// Prompt for empty username
		Text checkHost = new Text();
		checkHost.setFill(Color.web("#FFFFFF",1.0));
		Font checkHostFont = Font.font("Impact",FontWeight.EXTRA_BOLD,20);
		checkUsername.setFont(checkHostFont);
		checkUsername.setStroke(Color.web("#256B55",1.0));
		checkUsername.setStrokeWidth(1);

		// Text field for host IP
		TextArea hostField;
		hostField = new TextArea();
		hostField.setStyle("-fx-font: 25 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #000000; " + "-fx-border-color:#369DC6; -fx-border-width: 3px;");
		hostField.setPrefHeight(10);

		vbox.getChildren().addAll(text, checkUsername, inputField, enterHost, hostField, enterButton);
		enterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
				userName = inputField.getText();
				hostIP = hostField.getText();

				if(userName.length() == 0){
					checkUsername.setText("Please enter your username.");
				} else if (hostIP.length() == 0) {
					checkUsername.setText("Please enter your username.");
				} else {
					menu.setVisible(true);
					userInput.userInputView.setVisible(false);
				}
            }
        });

		return vbox;
	}

    private VBox createVBox() {
		VBox vbox = new VBox();
        this.text = new Text(" Battle of the\nBest Empires");
        this.text.setX(0);
        this.text.setY(0);
        this.text.setFill(Color.web("#FFD7BD",1.0));
		Font titleFont = Font.font("Impact",FontWeight.EXTRA_BOLD,80);
		this.text.setFont(titleFont);
		this.text.setStroke(Color.web("#614635",1.0));
		this.text.setStrokeWidth(2);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(7));
        vbox.setSpacing(10);


        Button b1 = new Button("    Start Game    ");
        Button b2 = new Button("    Instructions    ");
        Button b3 = new Button("    About    ");

        b1.setStyle("-fx-font: 15 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");
		b2.setStyle("-fx-font: 15 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");
		b3.setStyle("-fx-font: 15 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #FFFAFA; "
				+ "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");

        vbox.getChildren().addAll(text);
        vbox.getChildren().add(b1);
        vbox.getChildren().add(b2);
        vbox.getChildren().add(b3);

		this.startGame(b1, this.gamestage);
		this.startInstructions(b2);
		this.startAbout(b3);

        return vbox;
    }

    public void startGame(Button b, GameStage theGameStage) { // for starting the game
		b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ChatOverlay.socket.getOutputStream()));
					writer.write(CONFIRM);
					writer.newLine();
					writer.flush();
				} catch (IOException f) {
					f.printStackTrace();
				}
                System.out.println(b.getText());
            }
        });
    }

	public void startInstructions(Button b) { //for the instructions page
		b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Instructions instructions = new Instructions();
				instructions.setStage(stage);
				System.out.println(b.getText());
			}
		});
    }

	public void startAbout(Button b) { //for the about page
		b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				About about = new About();
				about.setStage(stage);
				System.out.println(b.getText());
			}
		});
    }

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Battle of the Best Empires");
		this.stage.setScene(this.scene);
		this.stage.show();
		this.gamestage = new GameStage(this.stage);
	}

	Scene getScene(){
		return this.scene;
	}

}