package application.sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    public Image img;
    public int x, y, dx, dy;
    public boolean visible;
    public double width;
    public double height;

    public Sprite(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
        this.visible = true;
    }

    // method to set the object's image
    public void loadImage(Image img) {
        try {
            this.img = img;
            this.setSize();
        } catch (Exception e) {
        }
    }

    // method to set the image to the image view node
    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, this.x, this.y);

    }

    // method to set the object's width and height properties
    private void setSize() {
        this.width = this.img.getWidth();
        this.height = this.img.getHeight();
    }

    // method that will check for collision of two sprites
    public boolean collidesWith(Sprite rect2) {
        Rectangle2D rectangle1 = this.getBounds();
        Rectangle2D rectangle2 = rect2.getBounds();

        return rectangle1.intersects(rectangle2);
    }

    // method that will return the bounds of an image
    private Rectangle2D getBounds() {
        return new Rectangle2D(this.x, this.y, this.width, this.height);
    }

    // method to return the image
    Image getImage() {
        return this.img;
    }

    // getters
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public int getDX() {
        return this.dx;
    }

    public int getDY() {
        return this.dy;
    }

    public boolean getVisible() {
        return visible;
    }

    public boolean isVisible() {
        if (visible)
            return true;
        return false;
    }

    // setters
    public void setX(int x) {
        this.dx = x;
    }

    public void setY(int y) {
        this.dy = y;
    }
    public void setDX(int dx) {
        this.dx = dx;
    }

    public void setDY(int dy) {
        this.dy = dy;
    }

    public void setWidth(double val) {
        this.width = val;
    }

    public void setHeight(double val) {
        this.height = val;
    }

    public void setVisible(boolean value) {
        this.visible = value;
    }

}
