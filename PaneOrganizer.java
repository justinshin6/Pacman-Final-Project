package pacman;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This is the PaneOrganizer class which is the top-level graphical class for the Pacman game. The two main Panes, the
 * gamePane and the scorePane are instantiated in this class. Simply put, this PaneOrganizer class's purpose is
 * self-explanatory: set up the main graphical components of the Pacman game. Additionally, an instance of the Game
 * is called which has two parameters which takes in the two panes mentioned above as the arguments.
 */
public class PaneOrganizer {

    //instance variables
    private BorderPane root;
    private Pane gamePane;
    private HBox scorePane;

    //PaneOrganizer constructor
    public PaneOrganizer() {
        this.root = new BorderPane();
        this.createGamePane();
        this.createScorePane();
        new Game(this.gamePane, this.scorePane);
    }

    /**
     * Helper method that helps set up the gamePane method. This gamePane is set to the top of the BorderPane.
     */
    private void createGamePane() {
        this.gamePane = new Pane();
        this.gamePane.setPrefSize(Constants.SCENE_WIDTH, Constants.GAME_PANE_HEIGHT);
        this.gamePane.setStyle(Constants.BACKGROUND_COLOR);
        this.root.setTop(this.gamePane);
    }

    /**
     * Helper method that helps set up the scorePane which is where the score and the life labels are located. This pane
     * is set at the bottom of the BorderPane.
     */
    private void createScorePane() {
        this.scorePane = new HBox();
        this.scorePane.setPrefSize(Constants.SCENE_WIDTH, Constants.SCORE_PANE_HEIGHT);
        this.scorePane.setStyle(Constants.BACKGROUND_COLOR);
        this.root.setBottom(this.scorePane);
        this.scorePane.setAlignment(Pos.CENTER);
        this.scorePane.setFocusTraversable(false);
        this.scorePane.setSpacing(Constants.SCORE_PANE_SPACING);
        this.scorePane.setAlignment(Pos.CENTER);
    }

    /**
     * @return this.root - the BorderPane
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
