package pacman;

import javafx.scene.paint.Color;

/**
 * Constants class that is in charge of final variables.
 */
public class Constants {

    //Scene
    public final static int SCENE_WIDTH = 575;
    public final static int SCENE_HEIGHT = 635;
    public final static int MAZE_DIMENSION = 23;

    //Panes
    public final static int SCORE_PANE_HEIGHT = 60;
    public final static int SCORE_PANE_SPACING = 100;
    public final static int GAME_PANE_HEIGHT = SCENE_HEIGHT - SCORE_PANE_HEIGHT;

    //Colors
    public final static String BACKGROUND_COLOR = "-fx-background-color: #000080";
    public final static Color INKY_COLOR = Color.DEEPSKYBLUE;
    public final static Color CLYDE_COLOR = Color.ORANGE;
    public final static Color PINKY_COLOR = Color.LAVENDER;
    public final static Color BLINKY_COLOR = Color.RED;
    public final static Color GHOST_FRIGHTENED_COLOR = Color.POWDERBLUE;




    //Shapes
    public final static int SQUARE_SIZE = 25;
    public final static int PACMAN_RADIUS = SQUARE_SIZE / 2;
    public final static int DOT_RADIUS = 2;
    public final static int ENERGIZER_RADIUS = 7;
    public final static int DOT_OFFSET = SQUARE_SIZE / 2;

    //Label
    public final static String LABEL_FONT = "Arial";
    public final static int LABEL_FONT_SIZE = 12;
    public final static int GAME_LABEL_FONT_SIZE = 20;
    public final static int GAME_LABEL_LAYOUT_X_READY = 260;
    public final static int GAME_LABEL_LAYOUT_X_GO = 270;
    public final static int GAME_LABEL_LAYOUT_Y = 350;
    public final static int GAME_LABEL_TIME = 7;
    public final static int GAME_OVER_LABEL_LAYOUT_X = 243;

    //Scores
    public final static int DOT_SCORE = 10;
    public final static int ENERGIZER_SCORE = 100;
    public final static int GHOST_SCORE = 200;

    //Counters
    public final static int GAME_OVER_COUNTER = 186;
    public final static int LIVES_COUNTER = 3;

    //Starting Locations
    public final static int PACMAN_START_X = 287;
    public final static int PACMAN_START_Y = 437;

    public final static int BLINKY_START_X = 11 * Constants.SQUARE_SIZE;
    public final static int BLINKY_START_Y = 8 * Constants.SQUARE_SIZE;

    public final static int INKY_START_X = 11 * Constants.SQUARE_SIZE;
    public final static int INKY_START_Y = 10 * Constants.SQUARE_SIZE;

    public final static int CLYDE_START_X = 12 * Constants.SQUARE_SIZE;
    public final static int CLYDE_START_Y = 10 * Constants.SQUARE_SIZE;

    public final static int PINKY_START_X = 10 * Constants.SQUARE_SIZE;
    public final static int PINKY_START_Y = 10 * Constants.SQUARE_SIZE;

    //Scatter & Chase Durations/Offsets
    public final static double TIMELINE_DURATION = 0.2;
    public final static int CHASE_TIME = 100;
    public final static int SCATTER_TIME = 35;
    public final static int FRIGHTENED_CHANGE_COLOR_TIME = 28;
    public final static int FRIGHTENED_TIME = 35;
    public final static double TIME_COUNTER_INCREMENT = 0.25;
    public final static BoardCoordinate INKY_SCATTER_COORD = new BoardCoordinate(21, 21, true);
    public final static BoardCoordinate CLYDE_SCATTER_COORD = new BoardCoordinate(21, 1, true);
    public final static BoardCoordinate PINKY_SCATTER_COORD = new BoardCoordinate(1, 1, true);
    public final static BoardCoordinate BLINKY_SCATTER_COORD = new BoardCoordinate(1, 21, true);
    public final static int INKY_CHASE_OFFSET = 2;
    public final static int CLYDE_CHASE_OFFSET = 4;
    public final static int PINKY_CHASE_OFFSET_X = 3;
    public final static int PINKY_CHASE_OFFSET_Y = 1;




}
