package application.main;

import application.pages.Menu;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) { // method for starting with the game stage
        Menu welcomeStage = new Menu();
		welcomeStage.setStage(stage);
    }
}
