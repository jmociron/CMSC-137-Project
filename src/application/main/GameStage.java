package application.main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import application.main.GameStage;
import javafx.scene.image.Image;

public class GameStage {
	public static final int WINDOW_HEIGHT = 768;
	public static final int WINDOW_WIDTH = 432;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;

	public final Image bgGame = new Image("images/lawn.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);

	// the class constructor
	public GameStage() {

		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene);
	}

	// method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;

		// set stage elements here
		this.root.getChildren().add(canvas);

		this.stage.setTitle("Battle of the Best Empires");
		this.stage.setScene(this.scene);
		stage.setResizable(false); //makes the window not resizable

		// invoke the start method of the animation timer
		this.gametimer.start();

		this.stage.show();
	}

}