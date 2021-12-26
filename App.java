package pacman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the App class where the Pacman game will start. The App class sets up the Scene where the main BorderPane
 * that displays the Pacman Game will be shown. An instance of the PaneOrganizer class is called in the start() method
 * of the App class where the PaneOrganizer class is the top-level graphical class.
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        PaneOrganizer organizer = new PaneOrganizer();

        //Creating the scene for the Cartoon
        Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("PACMAN");
        stage.show();
    }

    /*
    * Here is the mainline!
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
