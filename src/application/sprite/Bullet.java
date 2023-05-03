package application.sprite;

import javafx.scene.image.Image;
import application.sprite.Bullet;
import application.main.GameStage;

public class Bullet extends Sprite {
    private final int BULLET_SPEED = 20;
    public final static Image BULLET_IMAGE = new Image("images/bullet.png", Bullet.BULLET_WIDTH, Bullet.BULLET_WIDTH,
            false, false);
    public final static int BULLET_WIDTH = 30;

    public Bullet(int x, int y) {
        super(x, y);
        this.loadImage(Bullet.BULLET_IMAGE);
    }

    // method that will move/change the x position of the bullet
    public void move() {
        // if the bullet has not reached the right edge of the window
        this.y -= this.BULLET_SPEED;
        // checker if the x position of the bullet is beyond the GameStage width;
        // if so, it will disappear
        if (this.getY() >= GameStage.WINDOW_HEIGHT) {
            this.setVisible(false);
        }
    }
}