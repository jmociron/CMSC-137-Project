package application.main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

// PAGES:
import application.main.GameStage;
import application.pages.GameOver;
import application.pages.ChatOverlay;

public class GameStage {
	public static final int WINDOW_HEIGHT = 768;
	public static final int WINDOW_WIDTH = 432;

	private Scene scene;
	private Stage stage;
	private Stage currentstage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private boolean isPaused;
	public long startPause;
	public ImageView pauseButton;
	public long endPause;
	private ChatOverlay overlayPane;

	// CONSTANTS
	public final static Image pause = new Image("images/pause.png",50,50,false,false);
	public final static Image resume = new Image("images/resume.png",40,40,false,false);
	public final Image bgGame = new Image("images/lawn.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
	public static final String PAUSE = "PAUSE";

	// the class constructor
	public GameStage(Stage currentstage) {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.CADETBLUE);
		this.currentstage = currentstage;
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
	}

	// method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		this.gametimer = new GameTimer(this.gc,this.scene, this);
		this.isPaused = false;
		this.startPause = System.nanoTime();
		this.endPause = System.nanoTime();
		this.root.getChildren().add(canvas);
		pauseButton = new ImageView(pause);
		this.root.getChildren().add(pauseButton);
		pauseButton.setLayoutX(10);
		pauseButton.setLayoutY(10);
		this.root.getChildren().add(overlayPane);
		overlayPane.setVisible(false);
		this.pauseGame(pauseButton);
		this.stage.setTitle("Battle of the Best Empires");
		this.stage.setScene(this.scene);
		stage.setResizable(false); //makes the window not resizable

		// invoke the start method of the animation timer
		this.gametimer.start();
		this.stage.show();
	}

	void pauseGame(ImageView button) {
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
				try {
					System.out.println("Game paused.");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ChatOverlay.socket.getOutputStream()));
					writer.write(PAUSE);
					writer.newLine();
					writer.flush();
				} catch (IOException f) {
					f.printStackTrace();
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

	public void setOverlayPane() {
		this.overlayPane = new ChatOverlay(this);
	}

	public GameTimer getGameTimer() {
		return this.gametimer;
	}

	public Stage getCurrentStage() {
		return this.currentstage;
	}

	public boolean isPaused() {
		return this.isPaused;
	}

	public void setPaused(boolean paused) {
		this.isPaused = paused;
	}

	public Pane getOverlayPane() {
		return this.overlayPane;
	}

}