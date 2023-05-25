package application.main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

// PAGES:
import application.main.GameStage;
import application.pages.GameOver;

public class GameStage {
	public static final int WINDOW_HEIGHT = 768;
	public static final int WINDOW_WIDTH = 432;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private boolean isPaused;
	private long startPause;
	private long endPause;
	public final Image pause = new Image("images/pause.png",100,100	,false,false);
	public final Image bgGame = new Image("images/lawn.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);

	// the class constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gametimer = new GameTimer(this.gc,this.scene, this);
		this.isPaused = false;
		this.startPause = System.nanoTime();
		this.endPause = System.nanoTime();
	}

	// method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;

		// set stage elements here
		this.root.getChildren().add(canvas);
		Button btnPause=new Button("Pause");
		this.root.getChildren().add(btnPause);
		btnPause.setLayoutX(10);
		this.pauseGame(btnPause, this);
		this.stage.setTitle("Battle of the Best Empires");
		this.stage.setScene(this.scene);
		stage.setResizable(false); //makes the window not resizable

		// invoke the start method of the animation timer
		this.gametimer.start();
		this.stage.show();
	}

	void pauseGame(Button button, GameStage gameStage) {
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	if(!gameStage.isPaused) {
            		gameStage.gametimer.stop();
            		gameStage.startPause = System.nanoTime();
            		gameStage.isPaused = true;
            		button.setText("Resume");
            	} else {
            		gameStage.endPause = System.nanoTime();
            		gameStage.gametimer.addTime(gameStage.endPause-gameStage.startPause);
            		gameStage.gametimer.start();
            		gameStage.isPaused = false;
            		button.setText("Pause");
            	}
            }
        });
	}

	public void flashGameOver(int result) {
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				GameOver gameover = new GameOver(result);
				gameover.setStage(stage);
				stage.setScene(gameover.getScene());
			}
		});
	}

}