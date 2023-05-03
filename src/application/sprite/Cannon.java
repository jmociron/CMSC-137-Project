package application.sprite;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import application.main.GameStage;
import application.main.GameTimer;

public class Cannon extends Sprite {
    private String name;
    private int strength;
    private GameTimer gametimer;
    private boolean alive;

    private ArrayList<Bullet> bullets;
    public final static Image Cannon_IMAGE = new Image("images/cannon.png", Cannon.Cannon_WIDTH, Cannon.Cannon_WIDTH,
            false, false);
    private final static int Cannon_WIDTH = 75;

    public Cannon(String name, int x, int y) {
        super(x, y);
        this.name = name;
        Random r = new Random();
        this.strength = r.nextInt(151) + 100;
        this.alive = true;
        this.bullets = new ArrayList<Bullet>();
        this.loadImage(Cannon.Cannon_IMAGE);
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
        this.alive = false;
    }

    // method that will get the bullets 'shot' by the Cannon
    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    // method called if spacebar is pressed
    public void shoot() {
        // compute for the x and y initial position of the bullet
        int x = (int) (this.x + this.width + 20);
        int y = (int) (this.y + this.height / 2);

    }

    // method called if up/down/left/right arrow key is pressed.
    public void move() {
        int tempx = this.x + this.dx; // computes for the position to be moved horizontally

        // early return if the starter has reached either ends of the screen
        if (tempx < 0 || tempx > (GameStage.WINDOW_WIDTH - Cannon_WIDTH)) { // deducts starter's width
            return;
        }

        this.x += this.dx; // moves horizontally if still within bounds
    }

    public int getStrength() {
        return this.strength;
    }

}
