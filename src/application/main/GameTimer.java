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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import application.sprite.Dragon;
import application.sprite.GameModifier;
import application.sprite.ShieldedInvader;
import application.sprite.UnshieldedInvader;
import application.sprite.Bomb;
import application.sprite.Bullet;
import application.sprite.Cannon;
import application.sprite.CannonBooster;
import application.sprite.Invader;
import application.sprite.Shield;
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
	private ArrayList<GameModifier> gameModifiers;
	public static final int MAX_NUM_INVADERS = 3;
	private long endGame;
	private long startSpawn;
	private long launchBoss;
	private long launchGM;
	private GameStage gameStage;
	public final Image bgGame = new Image("images/lawn.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);

	private final static double SPAWN_DELAY = 3; //interval time for the rocks to be spawned
	private final static double SPAWN_BOSS = 10; //time stamp when the boss shall be spawned
	private final static double GM_INTERVAL = 5; //time stamp when the boss shall be spawned
	public static final int LOSE_NUM = 0;
    public static final int WIN_NUM = 1;

	GameTimer(GraphicsContext gc, Scene theScene, GameStage gameStage) {
		this.gc = gc;
		this.gameStage = gameStage;
		this.theScene = theScene;
		this.myCannon = new Cannon("My Cannon", 178, 520);
		this.myCastle = new Castle("My Castle", 0, 590);

		// instantiate the ArrayList of Cannon
		this.invaders = new ArrayList<Invader>();
		this.gameModifiers = new ArrayList<GameModifier>();

		// call the spawnInvaders method
		this.spawnInvaders();
		this.endGame = System.nanoTime();
		this.startSpawn = System.nanoTime();
		this.launchBoss = System.nanoTime();
		this.launchGM = System.nanoTime();

		// call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		System.out.println(currentNanoTime);
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc.drawImage(bgGame, 0, 0);
		double gameTimer = (currentNanoTime - this.endGame) / 1000000000.0;
        double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
        double bossCountdown = (currentNanoTime - this.launchBoss) / 1000000000.0;
        double spawnGM = (currentNanoTime - this.launchGM) / 1000000000.0;

		this.myCannon.move();

		// render the ship
		if(myCastle.isAlive() == true) {
			this.myCannon.render(this.gc);
			moveBullets();
			renderBullets();
			renderInvaders();
			moveInvaders();
			renderGameModifiers();
			checkGM();
		}

		this.myCastle.render(this.gc);

		if(spawnElapsedTime >= GameTimer.SPAWN_DELAY) { //spawn rocks every 5 seconds
            this.spawnInvaders();
            this.startSpawn = System.nanoTime();
        }

		if(bossCountdown >= GameTimer.SPAWN_BOSS){ //spawn boss when the time reaches 30 seconds
        	this.spawnBoss();
        	this.launchBoss = System.nanoTime();
        }

		if(spawnGM >= GameTimer.GM_INTERVAL){ //spawn powerups every 10 seconds
        	this.spawnGameModifiers();
        	this.launchGM = System.nanoTime();
        	System.out.println("as");
        }

		this.showStatus(gameTimer);

		if(!this.myCastle.isAlive()){ // player loses when castle runs out of health
			this.gameStage.flashGameOver(LOSE_NUM);
			this.stop();
		} else if ((int)(60-gameTimer + 1) == 0){ // player wins if castle is still alive after time runs out
			this.gameStage.flashGameOver(WIN_NUM);
			this.stop();
		}
	}

	// method that will render/draw the invaders to the canvas
	private void renderInvaders() {
		for (Invader i : this.invaders) {
			i.render(this.gc);
		}
	}

	// method that will render/draw the bullets to the canvas
	private void renderBullets() {
		for (Bullet b : this.myCannon.getBullets()) {
			b.render(gc);
		}
	}

	private void renderGameModifiers(){
    	//iterate through the powerups and render them into the canvas
    	for(GameModifier gm: this.gameModifiers){
    		gm.render(this.gc);
    	}
    }

	// method that will spawn/instantiate three invaders at a random x,y location
	private void spawnInvaders() {
		Random r = new Random();
		Random invaderType = new Random();
		for (int i = 0; i < GameTimer.MAX_NUM_INVADERS; i++) {
			int x = r.nextInt(GameStage.WINDOW_WIDTH- Invader.Invader_WIDTH);
			int itype = invaderType.nextInt(2);

			if(itype == 0){
	        	ShieldedInvader invader = new ShieldedInvader(x,0); //instantiate a shielded invader
	        	this.invaders.add(invader);
	        }else if(itype == 1){
	        	UnshieldedInvader invader = new UnshieldedInvader(x,0); //instantiate a unshielded invader
	        	this.invaders.add(invader);
	        }
		}

	}

	private void spawnBoss(){
    	Random r = new Random();
    	int x = r.nextInt(GameStage.WINDOW_WIDTH- Dragon.BOSS_WIDTH);

        //instantiate the dragon (boss)
    	Dragon boss = new Dragon(x,0);
    	this.invaders.add(boss); //add the dragon into the arraylist of invaders

    }

	private void spawnGameModifiers(){

    	Random r = new Random();
    	int gmType = r.nextInt(3); //randomizer for the powerup type that will appear

		// randomizes the spawning position
    	int x = r.nextInt(GameStage.WINDOW_WIDTH - GameModifier.GAMEMODIFIER_WIDTH);
    	int y = r.nextInt(GameStage.WINDOW_HEIGHT - Cannon.Cannon_WIDTH - Castle.Castle_HEIGHT);

        if(gmType == 0){
        	Bomb gameModifier = new Bomb(x,y); //instantiate a fuel
        	this.gameModifiers.add(gameModifier); //add to powerup arraylist
        }else if(gmType == 1){
        	CannonBooster gameModifier = new CannonBooster(x,y); //instantiate a star
        	this.gameModifiers.add(gameModifier);//add to the powerup arraylsit
        } else {
        	Shield gameModifier = new Shield(x,y);
        	this.gameModifiers.add(gameModifier);//add to the powerup arraylsit
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

			if(f.isAlive()){ //if alive, call move method
                f.move();
                f.checkCollision(this.myCastle, this.myCannon); //check if the rock collides with myShip
            }else{ //if not alive, remove the rock from the rock arraylist
                invaders.remove(i);
            }
			/*
			 * TODO: *If a Invader is alive, move the Invader. Else, remove the Invader
			 * from the
			 * invaders arraylist.
			 */
		}
	}

	private void checkGM(){
    	for(int i = 0; i<this.gameModifiers.size(); i++){
    		GameModifier gm = this.gameModifiers.get(i);
    		//checker if the powerup is visible
    		if(gm.isVisible()){ //if visible, check if it collides with myShip by calling checkCollision method
    			gm.checkCollision(this.myCastle, this.myCannon);
    		}else{ //if not visible, remove from powerups arraylist
    			gameModifiers.remove(i);
    		}
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

	private void showStatus(double gametimer){

    	//this.gc.drawImage(imgView, 0, 0);
        Font theFont = Font.font("Showcard Gothic",FontWeight.BOLD,20); //set the font type, style and size
        this.gc.setFont(theFont);
        this.gc.setFill(Color.WHITE); //set the text color
        //text for the runtime of the game
        this.gc.fillText(String.valueOf((int)(60-gametimer + 1) + " secs"), 350, 30);
        //text for the score of the ship
        this.gc.fillText("Score: "+String.valueOf(this.myCastle.getScore()), 330, 750);
        //text for the strength of the ship
		this.gc.fillText("Health: "+String.valueOf(this.myCastle.getHealth()), 10, 750);
	}

	void addTime(long time) {
		this.endGame += time;
		this.startSpawn += time;
		this.launchBoss += time;
		this.launchGM += time;
	}

}
