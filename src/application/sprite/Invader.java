package application.sprite;

import java.util.Random;

public class Invader extends Sprite {
    public static final int MAX_Invader_SPEED = 2;

    public final static int Invader_WIDTH = 80;
    private boolean alive;
    private int life;
    protected int speed;
    private int damage;

    public Invader(int x, int y, int life, int damage) {
        super(x, y);
        this.alive = true;
        this.life = life;
        this.damage = damage;
        Random r = new Random(); //instantiate a randomizer
		this.speed = (r.nextInt(Invader.MAX_Invader_SPEED) + 1); //set the speed of the rock to a random value between 1 to 5

    }

    // method that changes the x position of the Invader
    public void move() {
    	this.y += this.speed;
    }

    void isDead(){
		this.alive = false;
	}

    void decreaseLife() {
    	this.life -= 1;
    }

    protected int getLife() {
    	return this.life;
    }

    public void checkCollision(Castle castle, Cannon cannon) {
    	for(int i = 0; i<cannon.getBullets().size(); i++){
			if(this.collidesWith(cannon.getBullets().get(i))){
				cannon.getBullets().get(i).setVisible(false);
				this.decreaseLife();
				if(this.getLife() == 0) {
					this.isDead();
					castle.increaseScore();
					this.setVisible(false);
				}


			}
		}
		if(this.collidesWith(castle)){
			this.isDead();
			this.setVisible(false);
			castle.decreaseHealth(this.damage);
			System.out.println(castle.getHealth());
			if(castle.getHealth() <= 0) {
				castle.die();
			}

		}
    }

    // getter
    public boolean isAlive() {
        return this.alive;
    }
}
