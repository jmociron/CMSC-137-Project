package application.sprite;

import javafx.scene.image.Image;

public class Castle extends Sprite {
    private String name;
    private boolean alive;
    public final static Image Castle_IMAGE = new Image("images/castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);
    public final static Image DCastle_IMAGE = new Image("images/destroyed_castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);
    private final static int Castle_HEIGHT = 178;
    private final static int Castle_WIDTH = 432;
    private int health;
    private int score;


    public Castle(String name, int x, int y) {
        super(x, y);
        this.name = name;
        this.health = 50;
        this.alive = true;
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

    int getHealth() {
    	return this.health;
    }

    void increaseScore() {
    	this.score++;
    }

    int getScore() {
    	return this.score;
    }


}
