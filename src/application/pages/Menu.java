package application.pages;

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
import application.main.GameStage;


public class Menu {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private Text text;
	private VBox vbox;
	private VBox unameInput;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 408;
	public static final int WINDOW_WIDTH = 768;
	public final static String START = "start";
	public final static String INSTRUCTIONS = "instructions";
	public final static String ABOUT = "about";
	public static String userName = "";

	public Menu() {
		StackPane root = new StackPane();
		this.scene = new Scene(root, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);
		this.imgView = this.createBG();
		this.vbox = this.createVBox();
		this.unameInput = this.enterUsername(this.vbox,this);
		this.vbox.setVisible(false);
		root.getChildren().addAll(this.imgView, this.unameInput, this.vbox);
	}

	private ImageView createBG() {
		Image bg = new Image("images/menu.png");
		ImageView view = new ImageView();
		view.setImage(bg);

		return view;
	}

	private VBox enterUsername(VBox menu, Menu userInput) {
		TextArea inputField;
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(7));
        vbox.setSpacing(10);
		this.text = new Text("Enter Username");
		this.text.setX(0);
        this.text.setY(0);
        this.text.setFill(Color.web("#FFD7BD",1.0));
		Font titleFont = Font.font("Impact",FontWeight.EXTRA_BOLD,80);
		this.text.setFont(titleFont);
		this.text.setStroke(Color.web("#614635",1.0));
		this.text.setStrokeWidth(2);
		inputField = new TextArea();
		inputField.setPrefHeight(50);
		inputField.setPrefWidth(10); //dpesmt work due to contraints of vbox
		Button enterButton = new Button("    Confirm    ");
		vbox.getChildren().addAll(text);
	    vbox.getChildren().add(inputField);
	    vbox.getChildren().add(enterButton);

	    enterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	userName = inputField.getText();
            	menu.setVisible(true);
            	userInput.unameInput.setVisible(false);
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

		this.startGame(b1);
		this.startInstructions(b2);
		this.startAbout(b3);

        return vbox;
    }

    public void startGame(Button b1) { // for starting the game
    	b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	GameStage theGameStage = new GameStage();
        		theGameStage.setStage(stage);
                System.out.println(b1.getText());
            }
        });
    }

	public void startInstructions(Button b2) { //for instructions
    	b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	Instructions instructions = new Instructions();
            	instructions.setStage(stage);
                System.out.println(b2.getText());
            }
        });
    }

	public void startAbout(Button b3) { //for instructions
    	b3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	About about = new About();
            	about.setStage(stage);
                System.out.println(b3.getText());
            }
        });
    }

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Battle of the Best Empires");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	Scene getScene(){
		return this.scene;
	}

}