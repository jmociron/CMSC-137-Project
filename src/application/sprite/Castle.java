package application.sprite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import application.pages.ChatOverlay;
import javafx.scene.image.Image;

public class Castle extends Sprite {
    private String name;
    private boolean alive;
    public final static Image Castle_IMAGE = new Image("images/castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);
    public final static Image DCastle_IMAGE = new Image("images/destroyed_castle.png", Castle.Castle_WIDTH, Castle.Castle_HEIGHT,
            false, false);

    public final static int Castle_WIDTH = 432;
    public final static int Castle_HEIGHT = 178;
    private int health;
    private int score;
    private int highestScore;
    private boolean isBoosted;
    private BoosterTimer timer;


    public Castle(String name, int x, int y) {
        super(x, y);
        this.name = name;
        this.health = 50;
        this.alive = true;
        this.isBoosted = false;
        this.highestScore = 0;
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
    	this.loadImage(Castle.DCastle_IMAGE);
        this.alive = false;

    }

    void decreaseHealth(int damage) {
    	this.health -= damage;
    }

    public int getHealth() {
    	return this.health;
    }

    void increaseScore() {
    	if(isBoosted()) {
    		this.score++;

    	}
    	this.score++;
    	updateMap();
    }

    public int getScore() {
    	return this.score;
    }

    boolean isBoosted() {
    	return this.isBoosted;
    }

    void setBoosted(){ //setter
		if(!this.isBoosted()) { //if the ship is not immune
            this.isBoosted = true; //the ship is immune
            this.timer =  new BoosterTimer(this); //creates a timer
            this.timer.start(); //starts the timer
        }
	}

    void resetBoost() {
    	this.isBoosted = false;
    }

    void increaseHealth(int health) {
    	this.health += health;
    }

    public void updateMap() {
		try {
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ChatOverlay.socket.getOutputStream()));
	        writer.write("points: " + this.getScore());
	        writer.newLine();
	        writer.flush();
	        System.out.println("point sent!!");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

//		try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(ChatOverlay.socket.getInputStream()));
//            String message;
//            System.out.println("dito!!");
//            while ((message = reader.readLine()) != null) {
//            	System.out.println(message);
//            	if (message.startsWith("points: ")) {
//                    String scoreString = message.substring(8);
//                    int score = Integer.parseInt(scoreString);
//                    if (score > highestScore) {
//                        highestScore = score;
//                        System.out.println("New highest score: " + highestScore);
//                    }
//                }
////	                chatArea.appendText(message + "\n");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public int getHighestScore() {
    	return this.highestScore;
    }

    public void setHighestScore(int score) {
    	this.highestScore = score;
    }


}
