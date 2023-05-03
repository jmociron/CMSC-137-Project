package application.main;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import application.main.GameStage;
import application.sprite.Bullet;
import application.sprite.Cannon;
import application.sprite.Invader;
import application.sprite.Castle;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer {

	private GraphicsContext gc;
	private Scene theScene;
	private Cannon myCannon;
	private Castle myCastle;
	private ArrayList<Invader> invaders;
	public static final int MAX_NUM_INVADERS = 3;

	GameTimer(GraphicsContext gc, Scene theScene) {
		this.gc = gc;
		this.theScene = theScene;
		this.myCannon = new Cannon("Going merry", 178, 520);
		this.myCastle = new Castle("Going merry", 0, 552);
		// instantiate the ArrayList of Cannon
		this.invaders = new ArrayList<Invader>();

		// call the spawnInvaders method
		// this.spawnInvaders();
		// call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);

		this.myCannon.move();
		/*
		 * TODO: Call the moveBullets and moveInvaders methods
		 */

		// render the ship
		this.myCannon.render(this.gc);
		this.myCastle.render(this.gc);

		moveBullets();
		renderBullets();

		/*
		 * TODO: Call the renderInvaders and renderBullets methods
		 */

	}

	// method that will render/draw the invaders to the canvas
	private void renderInvaders() {
		for (Invader i : this.invaders) {
			i.render(this.gc);
		}
	}

	// method that will render/draw the bullets to the canvas
	private void renderBullets() {
		/*
		 * TODO: Loop through the bullets arraylist of myCannon
		 * and render each bullet to the canvas
		 */
		for (Bullet b : this.myCannon.getBullets()) {
			b.render(gc);
		}
	}

	// method that will spawn/instantiate three invaders at a random x,y location
	private void spawnInvaders() {
		Random r = new Random();
		for (int i = 0; i < GameTimer.MAX_NUM_INVADERS; i++) {
			int x = r.nextInt(GameStage.WINDOW_WIDTH);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT);
			/*
			 * TODO: Add a new object Invader to the invaders arraylist
			 */
		}

	}

	// method that will move the bullets shot by a ship
	private void moveBullets() {
		// create a local arraylist of Bullets for the bullets 'shot' by the ship
		ArrayList<Bullet> bList = this.myCannon.getBullets();

		// Loop through the bullet list and check whether a bullet is still visible.
		for (int i = 0; i < bList.size(); i++) {
			Bullet b = bList.get(i);

			if (b.isVisible()) {
				b.move();
			} else { // if the bullet is not visible, remove from the bullet from the bullet
						// arraylist
				bList.remove(i);
			}

			/*
			 * // * TODO: If a bullet is visible, move the bullet, else, remove the bullet
			 * from
			 * // the bullet array list.
			 * //
			 */
		}
	}

	// method that will move the invaders
	private void moveInvaders() {
		// Loop through the invaders arraylist
		for (int i = 0; i < this.invaders.size(); i++) {
			Invader f = this.invaders.get(i);
			/*
			 * TODO: *If a Invader is alive, move the Invader. Else, remove the Invader
			 * from the
			 * invaders arraylist.
			 */
		}
	}

	// method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				moveCan(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				stopMyShip(code);
			}
		});
	}

	// method that will move the ship depending on the key pressed
	private void moveCan(KeyCode ke) {
		if (ke == KeyCode.UP)
			this.myCannon.setDY(-3);

		if (ke == KeyCode.LEFT)
			this.myCannon.setDX(-3);

		if (ke == KeyCode.DOWN)
			this.myCannon.setDY(3);

		if (ke == KeyCode.RIGHT)
			this.myCannon.setDX(3);

		if (ke == KeyCode.SPACE)
			this.myCannon.shoot();

		System.out.println(ke + " key pressed.");
	}

	// method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke) {
		this.myCannon.setDX(0);
		this.myCannon.setDY(0);
	}

}
