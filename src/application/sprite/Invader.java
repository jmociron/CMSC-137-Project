package application.sprite;

import java.util.Random;

import javafx.scene.image.Image;

public class Invader extends Sprite {
    public static final int MAX_Invader_SPEED = 5;
    public final static Image Invader_IMAGE = new Image("images/invader1.png", Invader.Invader_WIDTH,
            Invader.Invader_WIDTH, false, false);
    public final static int Invader_WIDTH = 80;
    private boolean alive;
    // attribute that will determine if a Invader will initially move to the right
//    private boolean moveRight;
    private int speed;

    public Invader(int x, int y) {
        super(x, y);
        this.alive = true;
        this.loadImage(Invader.Invader_IMAGE);
        Random r = new Random(); //instantiate a randomizer
		this.speed = (r.nextInt(Invader.MAX_Invader_SPEED) + 1); //set the speed of the rock to a random value between 1 to 5
//		this.moveRight = r.nextBoolean();

    }

    // method that changes the x position of the Invader
    public void move() {
    	this.setDY(speed); //call setDX method and pass speed as parameter
    	this.y += this.dy;
		//checker if the the rock's x position is still within the GameStage width


        /*
         * TODO: If moveRight is true and if the Invader hasn't reached the right
         * boundary yet,
         * move the Invader to the right by changing the x position of the Invader
         * depending on its speed
         * else if it has reached the boundary, change the moveRight value / move to the
         * left
         * Else, if moveRight is false and if the Invader hasn't reached the left
         * boundary yet,
         * move the Invader to the left by changing the x position of the Invader
         * depending on its speed.
         * else if it has reached the boundary, change the moveRight value / move to the
         * right
         */
    }

    // getter
    public boolean isAlive() {
        return this.alive;
    }
}
