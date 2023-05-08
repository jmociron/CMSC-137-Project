package application.main;

import javafx.application.Application;
import javafx.stage.Stage;
import application.menu.Menu;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) { // method dor starting with the game stage
        // GameStage theGameStage = new GameStage(); // creates a gamestage
        // theGameStage.setStage(stage); // sets the stage
        Menu welcomeStage = new Menu();
		welcomeStage.setStage(stage);
    }
}
	