package application.sprite;

import javafx.scene.image.Image;

public class Castle extends Sprite {
    private String name;
    private boolean alive;
    public final static Image Castle_IMAGE = new Image("images/castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);
    private final static int Castle_HEIGHT = 216;
    private final static int Castle_WIDTH = 432;

    public Castle(String name, int x, int y) {
        super(x, y);
        this.name = name;
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
        this.alive = false;
    }

}
