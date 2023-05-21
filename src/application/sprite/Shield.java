package application.sprite;

import javafx.scene.image.Image;

public class Shield extends GameModifier {
	public final static Image SHIELD_IMAGE = new Image("images/shield.png",GameModifier.GAMEMODIFIER_WIDTH,GameModifier.GAMEMODIFIER_WIDTH,false,false); //fuel image
    public final static int RESTOREHEALTH = 10; //the additional power that fuel has

    public Shield(int xPos, int yPos) { //constructor
        super(xPos, yPos); //calls thesuperclass' constructor
        this.loadImage(SHIELD_IMAGE); //loads the fuel image
    }


	public void checkCollision(Castle castle, Cannon cannon) { //method that checks if the fuel collides with the cannon
		for(int i = 0; i<cannon.getBullets().size(); i++){
			if(this.collidesWith(cannon.getBullets().get(i))){
				cannon.getBullets().get(i).setVisible(false);
				this.setVisible(false); //the powerup disappears
				castle.increaseHealth(RESTOREHEALTH);
			}else{ //if it does not collide with the cannon
	            if(this.isStartTimer() == false) { //if the timer has not been started yet
	                this.timer =  new GameModifierTimer(this); //creates a timer
	                this.timer.start(); //starts the timer
	                this.setStartTimer(); //sets the start timer to true
	            }
	        }
		}
    }

}
