package application.sprite;

import javafx.scene.image.Image;

public class Dragon extends Invader{
	public final static Image BOSS_IMAGE = new Image("images/dragon.gif", Dragon.BOSS_WIDTH,
            Dragon.BOSS_WIDTH, false, false);

  public final static int BOSS_DAMAGE = 15; //damage of the boss
  public final static int BOSS_WIDTH=200; //width/size of the boss
  public final static int BOSS_LIFE = 10; //max health of the boss
	public final static int SI_DAMAGE = 5;

	public Dragon (int x, int y) {
		 super(x, y, BOSS_LIFE, BOSS_DAMAGE);
		 this.loadImage(BOSS_IMAGE); //load the image of boss
	}

  @Override
  public void checkCollision(Castle castle, Cannon cannon) {
  	for(int i = 0; i<cannon.getBullets().size(); i++){
			if(this.collidesWith(cannon.getBullets().get(i))){
//				this.playExplosionSound();
				cannon.getBullets().get(i).setVisible(false);
				this.decreaseLife();
				if(this.getLife() == 0) {
					this.isDead();
					this.setVisible(false);
				}

			}
		}
		if(this.collidesWith(castle)){
//			this.playCollisionSound();
//			if(ship.isImmune() == false){
//				ship.reduceStrength(Rock.ROCK_DAMAGE);
//			}
			this.isDead();
			this.setVisible(false);
			castle.decreaseHealth(BOSS_DAMAGE);
			System.out.println(castle.getHealth());

		}
  }
}

//public class Dragon extends Invader {
//	public final static Image BOSS_IMAGE = new Image("images/invader1.png", Invader.Invader_WIDTH,
//            Invader.Invader_WIDTH, false, false);
//    public final static Image BOSSR_IMAGE = new Image("images/bossR.png",Dragon.BOSS_WIDTH,Dragon.BOSS_WIDTH,false,false); //image of the boss
//    public final static int BOSS_DAMAGE = 15; //damage of the boss
//    public final static int BOSS_WIDTH=100; //width/size of the boss
//    public final static int BOSS_LIFE = 10; //max health of the boss
//
//
//    public Dragon(int x, int y) { //constructor
//        super(x, y, BOSS_LIFE, BOSS_DAMAGE);
//        this.loadImage(BOSS_IMAGE); //load the image of boss
//
//    }
//
//
//    @Override
//    public void checkCollision(Castle castle, Cannon cannon) {
//    	for(int i = 0; i<cannon.getBullets().size(); i++){
//			if(this.collidesWith(cannon.getBullets().get(i))){
////				this.playExplosionSound();
//				cannon.getBullets().get(i).setVisible(false);
//				this.decreaseLife();
//				if(this.getLife() == 0) {
//					this.isDead();
//					this.setVisible(false);
//				}
//
//			}
//		}
//		if(this.collidesWith(castle)){
////			this.playCollisionSound();
////			if(ship.isImmune() == false){
////				ship.reduceStrength(Rock.ROCK_DAMAGE);
////			}
//			this.isDead();
//			this.setVisible(false);
//			castle.decreaseHealth(BOSS_DAMAGE);
////			System.out.println(castle.getHealth());
//
//		}
//    }
//
//}