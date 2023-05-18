package application.sprite;

import javafx.scene.image.Image;

public class Bomb extends GameModifier {
	public final static Image BOMB_IMAGE = new Image("images/bomb.png",GameModifier.GAMEMODIFIER_WIDTH,GameModifier.GAMEMODIFIER_WIDTH,false,false); //fuel image
    public final static int DAMAGE = 15; //the additional power that fuel has

    public Bomb(int xPos, int yPos) { //constructor
        super(xPos, yPos); //calls thesuperclass' constructor
        this.loadImage(BOMB_IMAGE); //loads the fuel image
    }


	public void checkCollision(Castle castle, Cannon cannon) { //method that checks if the fuel collides with the ship
		for(int i = 0; i<cannon.getBullets().size(); i++){
			if(this.collidesWith(cannon.getBullets().get(i))){
//				this.playExplosionSound();
				cannon.getBullets().get(i).setVisible(false);
				this.setVisible(false); //the powerup disappears
	            castle.decreaseHealth(DAMAGE);
	            if(castle.getHealth() <= 0) {
					castle.die();
				}
			}else{ //if it does not collide with the ship
	            if(this.isStartTimer() == false) { //if the timer has not been started yet
	                this.timer =  new GameModifierTimer(this); //creates a timer
	                this.timer.start(); //starts the timer
	                this.setStartTimer(); //sets the start timer to true
	            }
	        }
		}
    }
}
