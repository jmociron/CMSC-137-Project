package application.sprite;


public class GameModifierTimer extends Thread{
	private GameModifier gameModifier; //powerup attribute
    private int time; //time

    public final static int UP_TIME = 5; //5 seconds for the visibility of the powerup

    public GameModifierTimer(GameModifier gameModifier){ //constructor
        this.gameModifier = gameModifier; //initializes the powerup
        this.time = GameModifierTimer.UP_TIME; //initialiozes the time
    }

    private void countDown(){ //method for the countdown
        while(this.time!=0){ //while time is not yet 0
            try{ //try
                Thread.sleep(1000); //sleep
                this.time--; //decrements the time
            }catch(InterruptedException e){ //catch
                System.out.println(e.getMessage());
            }
        }
        this.gameModifier.setVisible(false); //makes the power-up invisible
    }

    public void run(){ //runs the countdown
        this.countDown();
    }
}
