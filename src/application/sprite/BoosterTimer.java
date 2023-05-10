package application.sprite;

public class BoosterTimer extends Thread {
    private Castle castle; //ship attribute
    private int time; //int time
    private final static int BOOSTED_TIME = 5; //3 seconds immune time

    public BoosterTimer(Castle castle){ //constructor
        this.castle = castle; //initializes the ship
        this.time = BoosterTimer.BOOSTED_TIME; //initializes the time
    }

    private void countDown(){ //method for the countdown of timer
        while(this.time!=0){ //while the time is not yet 0
            try{ //try
                Thread.sleep(1000); //sleep
                this.time--; //decrements time
            }catch(InterruptedException e){ //catch
                System.out.println(e.getMessage());
            }
        }
        this.castle.resetBoost();; //resets the immunity of the ship
    }

    public void run(){ //run the countdown
        this.countDown();
    }
}