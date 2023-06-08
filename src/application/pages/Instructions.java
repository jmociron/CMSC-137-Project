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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Instructions {
    // PROPERTIES:
	private Scene scene;
	private Scene menuScene;
	private Stage stage;
	private ImageView imgView;
	private Text title;
	private Text body;
	private Text controls;
	private VBox vbox;

	// CONSTANTS:
	public static final int WINDOW_HEIGHT = 408;
	public static final int WINDOW_WIDTH = 768;

	public Instructions(Scene scene) {
		StackPane root = new StackPane();
		this.scene = new Scene(root, Menu.WINDOW_WIDTH, Menu.WINDOW_HEIGHT);
		this.menuScene = scene;
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
        this.title = new Text("Instructions");
        this.title.setX(0);
        this.title.setY(0);
        this.title.setFill(Color.web("#FFD7BD",1.0)); 
		Font titleFont = Font.font("Impact",FontWeight.EXTRA_BOLD,45);
		this.title.setFont(titleFont);
		this.title.setStroke(Color.web("#614635",1.0));
		this.title.setStrokeWidth(2);

        this.body = new Text("A player has a base (castle) and a cannon where it can\n attack the invaders. Killing an invader earns the player\n a point. When the invader reaches the base, the castle\n loses 5 health points. The castle with the highest points after\n 60 seconds, provided that the castle is still alive,\n is declared the winner.");
        this.body.setX(0);
        this.body.setY(0);
        this.body.setFill(Color.web("#FFFFFF",1.0)); 
		Font bodyFont = Font.font("Impact",FontWeight.EXTRA_BOLD,26);
		this.body.setFont(bodyFont);
		this.body.setStroke(Color.web("#31484B",1.0));
		this.body.setStrokeWidth(1);
        this.body.setTextAlignment(TextAlignment.CENTER);

        this.controls = new Text("Move using LEFT and RIGHT keys.\n Shoot using the SPACEBAR.");
        this.controls.setX(0);
        this.controls.setY(0);
        this.controls.setFill(Color.web("#43839D",1.0)); 
		Font controlsFont = Font.font("Impact",FontWeight.EXTRA_BOLD,20);
		this.controls.setFont(controlsFont);
		this.controls.setStroke(Color.web("#FFFFFF",1.0));
		this.controls.setStrokeWidth(1);
        this.controls.setTextAlignment(TextAlignment.CENTER);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(7));
        vbox.setSpacing(10);


        Button b1 = new Button("    Main Menu    ");

        b1.setStyle("-fx-font: 15 Verdana; -fx-background-color: #369DC6; -fx-text-fill: #FFFAFA; "
			+ "-fx-border-color:#FFFAFA; -fx-border-width: 3px; -fx-border-radius: 20; -fx-background-radius: 20;");

        vbox.getChildren().addAll(title, body, controls);
        vbox.getChildren().add(b1);
        
		this.startMenu(b1);

        return vbox;
    }

    public void startMenu(Button b1) { // for starting the game
		b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
				// Menu welcomeStage = new Menu();
				// welcomeStage.setStage(stage);
				stage.setScene(menuScene);
                System.out.println(b1.getText());
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
