package application.sprite;

import javafx.scene.image.Image;

public class Castle extends Sprite {
    private String name;
    private boolean alive;
    public final static Image Castle_IMAGE = new Image("images/castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);
    public final static Image DCastle_IMAGE = new Image("images/destroyed_castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);
    
    public final static int Castle_WIDTH = 432;
    public final static int Castle_HEIGHT = 178;
    private int health;
    private int score;
    private boolean isBoosted;
    private BoosterTimer timer;


    public Castle(String name, int x, int y) {
        super(x, y);
        this.name = name;
        this.health = 50;
        this.alive = true;
        this.isBoosted = false;
        this.loadImage(Castle.Castle_IMAGE);
    }

    public boolean isAlive() {
        if (this.alive)
            return true;
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void die() {
    	this.loadImage(Castle.DCastle_IMAGE);
        this.alive = false;

    }

    void decreaseHealth(int damage) {
    	this.health -= damage;
    }

    public int getHealth() {
    	return this.health;
    }

    void increaseScore() {
    	if(isBoosted()) {
    		this.score++;
    	}
    	this.score++;
    }

    public int getScore() {
    	return this.score;
    }

    boolean isBoosted() {
    	return this.isBoosted;
    }

    void setBoosted(){ //setter
		if(!this.isBoosted()) { //if the ship is not immune
            this.isBoosted = true; //the ship is immune
            this.timer =  new BoosterTimer(this); //creates a timer
            this.timer.start(); //starts the timer
        }
	}

    void resetBoost() {
    	this.isBoosted = false;
    }

    void increaseHealth(int health) {
    	this.health += health;
    }


}
