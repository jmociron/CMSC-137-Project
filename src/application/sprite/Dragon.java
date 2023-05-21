package application.sprite;


import java.util.Random;

import application.main.GameStage;
import javafx.scene.image.Image;

public class Dragon extends Invader{
	public final static Image BOSS_IMAGE = new Image("images/dragon.gif", Dragon.BOSS_WIDTH,
            Dragon.BOSS_WIDTH, false, false);

	private boolean moveRight;


	public final static int BOSS_DAMAGE = 15; //damage of the boss
	public final static int BOSS_WIDTH = 150; //width/size of the boss
	public final static int BOSS_LIFE = 10; //max health of the boss
	public final static int SI_DAMAGE = 5;

	public Dragon (int x, int y) {
		super(x, y, BOSS_LIFE, BOSS_DAMAGE);
		this.loadImage(BOSS_IMAGE); //load the image of boss

		Random r = new Random();
		int intRight = r.nextInt(2); // randomizes moveRight's initial value

		if(intRight == 0){
			this.moveRight = true;
		} else {
			this.moveRight = false;
		}
	}

	@Override
	public void checkCollision(Castle castle, Cannon cannon) {
	for(int i = 0; i<cannon.getBullets().size(); i++){
			if(this.collidesWith(cannon.getBullets().get(i))){
				cannon.getBullets().get(i).setVisible(false);
				this.decreaseLife();
				if(this.getLife() == 0) {
					this.isDead();
					this.setVisible(false);
				}

			}
		}
		if(this.collidesWith(castle)){
			this.isDead();
			this.setVisible(false);
			castle.decreaseHealth(BOSS_DAMAGE);
			System.out.println(castle.getHealth());

		}
	}

	public void moveLeft(){
		this.x -= this.speed;
	}

	public void moveRight(){
		this.x += this.speed;
	}

	public void moveY(){
		this.y += this.speed;
	}

	public void setMoveRight(boolean direction){
		this.moveRight = direction;
	}

	@Override
	public void move() {
		
		// checks if the dragon has reached the edge in terms of x-position
		if(!this.moveRight && this.x <= 0){ // left edge
			this.setMoveRight(true);
		}else if(this.moveRight && this.x >= (GameStage.WINDOW_WIDTH-this.width)){ // right edge
			this.setMoveRight(false);
		}

		// incrementing/decrementing x to allow horizontal movement
		if(this.moveRight && this.x <= (GameStage.WINDOW_WIDTH-this.width)){ // move to the right until edge is reached
			this.moveRight();
			this.moveY();
		}else if(!this.moveRight && this.x >= 0){ // move to the left until edge is reached
			this.moveLeft();
			this.moveY();
		}
	}

}
