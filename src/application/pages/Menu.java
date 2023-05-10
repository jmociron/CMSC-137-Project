package application.pages;

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
import application.main.GameStage;


public class Menu {

	// PROPERTIES:
	private Scene scene;
	private Stage stage;
	private ImageView imgView;
	private Text text;
	private VBox vbox;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 408;
	public static final int WINDOW_WIDTH = 768;
	public final static String START = "start";
	public final static String INSTRUCTIONS = "instructions";
	public final static String ABOUT = "about";

	public Menu() {
		StackPane root = new StackPane();
		this.scene = new Scene(root, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);
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

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("CMSC 137 Project");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	Scene getScene(){
		return this.scene;
	}

}