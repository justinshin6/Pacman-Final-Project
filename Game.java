package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This is the Game class of the Pacman game. The Game class is in charge of top-level logic components of the game
 * such as keyboard input, animation, timeline, movement, etc. The two panes instantiated in the PaneOrganizer class,
 * the gamePane and the scorePane, are associated with the Game class shown by the two parameters in the constructor
 * of the Game class.
 */
public class Game {

    //instance variables
    private Maze maze;
    private Pane gamePane;
    private HBox scorePane;
    private Label scoreLabel;
    private Label livesLabel;
    private Label gameLabel;
    private Timeline timeline;
    private Pacman pacman;
    private Ghost inky;
    private Ghost clyde;
    private Ghost pinky;
    private Ghost blinky;
    private int livesCounter;
    private int scoreCounter;
    private int gameLabelDisCounter;
    private double gameOverCounter;
    private double chaseCounter;
    private double ghostPenCounter;
    private double scatterCounter;
    private double frightenedCounter;
    private GhostBehavior ghostBehavior;
    private LinkedList<Ghost> ghostPen = new LinkedList<>();

    /**
     * Game constructor
     * @param gamePane - gamePane associated with the gamePane instantiated in the PaneOrganizer class
     * @param scorePane - scorePane associated with the scorePane instantiated in the PaneOrganizer class
     */
    public Game(Pane gamePane, HBox scorePane) {
        this.setUpPanes(gamePane, scorePane);
        this.setUpPacman();
        this.setUpCounters();
        this.setUpScorePane();
        this.setUpGhosts();
        this.startGame();

    }

    /**
     * Helper method that helps set up gamePane, scorePane, and an instance of the maze class.
     * @param gamePane - gamePane associated with the gamePane instantiated in the PaneOrganizer class
     * @param scorePane - scorePane associated with the scorePane instantiated in the PaneOrganizer class
     */
    private void setUpPanes(Pane gamePane, HBox scorePane) {
        this.gamePane = gamePane;
        this.scorePane = scorePane;
        this.gamePane.setFocusTraversable(true);
        this.maze = new Maze(this.gamePane, this);
    }

    /**
     * Helper method that helps set up the Pacman and sets it to the front of the scene.
     */
    private void setUpPacman() {
        this.pacman = this.maze.getPacman();
        this.pacman.getPacman().toFront();
    }

    /**
     * Helper method that helps set up the Labels found in the scorePane. These labels include the livesLabel, the
     * scoreLabel, and the gameLabel. Also, the quitButton is added to the scorePane.
     */
    private void setUpScorePane() {
        this.livesLabel = this.setUpLabel("Lives: " + this.livesCounter, new Font(Constants.LABEL_FONT,
                Constants.LABEL_FONT_SIZE));
        this.scoreLabel = this.setUpLabel("Score: " + this.scoreCounter, new Font(Constants.LABEL_FONT,
                Constants.LABEL_FONT_SIZE));
        this.scorePane.getChildren().addAll(this.livesLabel, this.scoreLabel);
        this.gameLabel = this.setUpLabel("Ready? ", new Font(Constants.LABEL_FONT,
                Constants.GAME_LABEL_FONT_SIZE));
        this.gameLabel.setLayoutX(Constants.GAME_LABEL_LAYOUT_X_READY);
        this.gameLabel.setLayoutY(Constants.GAME_LABEL_LAYOUT_Y);
        this.gamePane.getChildren().add(this.gameLabel);
        this.setUpQuitButton();
    }

    /**
     * Helper method that helps set up the labels.
     * @param labelText - the text of the label
     * @param font - the font of the label
     * @return label - the new label instance
     */
    private Label setUpLabel(String labelText, Font font) {
        Label label = new Label(labelText);
        label.setTextFill(Color.YELLOW);
        label.setFont(font);
        return label;
    }

    /**
     * Helper method that helps set up the quitButton found in the scorePane.
     */
    private void setUpQuitButton() {
        Button quitButton = new Button("Quit");
        this.scorePane.getChildren().add(quitButton);
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        this.scorePane.setFocusTraversable(false);
        quitButton.setFocusTraversable(false);
    }

    /**
     * Helper method that sets up the ghosts which were initially instantiated in the maze class.
     */
    private void setUpGhosts() {
        this.inky = this.maze.getGhostArrayList().get(0); //sky blue
        this.clyde = this.maze.getGhostArrayList().get(1); //orange
        this.pinky = this.maze.getGhostArrayList().get(2); //lavender
        this.blinky = this.maze.getGhostArrayList().get(3); //red
        this.ghostPen = new LinkedList<>();
        this.ghostBehavior = GhostBehavior.CHASED;
        this.resetGhostPen();
    }

    /**
     * Helper method that sets up the different counters. These counters are altered with the Timeline to achieve
     * different functionality such as tracking lives, tracking the score, tracking the different Ghost modes, etc.
     */
    private void setUpCounters() {
        this.livesCounter = Constants.LIVES_COUNTER;
        this.scoreCounter = 0;
        this.gameLabelDisCounter = 0;
        this.scatterCounter = 0;
        this.chaseCounter = 0;
        this.frightenedCounter = 0;
        this.gameOverCounter = Constants.GAME_OVER_COUNTER;
    }

    /**
     * Sets up the key handler and timeline in order to start the game.
     */
    private void startGame() {
        this.gamePane.setOnKeyPressed((KeyEvent event) -> this.handleKeyInput(event));
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.TIMELINE_DURATION),
                (ActionEvent event) -> this.update());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Handles key input by changing direction of pacman on up, down, left, and
     * right arrow keys.
     *
     * @param e code of the key pressed
     */
    private void handleKeyInput(KeyEvent e) {
        if (this.gameOverCounter > 0 && this.livesCounter > 0) {
            this.timeline.play();
            switch (e.getCode()) {
                case UP:
                    this.pacman.changeDirection(Direction.UP);
                    break;
                case DOWN:
                    this.pacman.changeDirection(Direction.DOWN);
                    break;
                case LEFT:
                    this.pacman.changeDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    this.pacman.changeDirection(Direction.RIGHT);
                    break;
                default:
                    break;
            }
        }
        e.consume();

    }

    /**
     * Timeline method that is called practically every second. Helper methods inside the body of this method
     * are constantly updated.
     */
    private void update() {
        this.gameLabelDisCounter ++;
        this.ghostPenCounter ++;
        this.removeGameLabelText();
        this.scatterOrChase();
        this.letOutGhosts();
        this.checkCollisionsTwice();
        this.checkGameOver(this.gameOverCounter, true);
        this.checkGameOver(this.livesCounter, false);
    }

    /**
     * Helper method that checks for collisions twice to avoid any edge cases.
     */
    private void checkCollisionsTwice() {
        this.pacman.movePacman();
        this.checkCollisions();
        this.moveGhosts();
        this.checkCollisions();
    }

    /**
     * Method that is in charge of ghost movement that changes based off the different ghost modes. The different
     * ghost modes are tracked by the instance variable this.ghostBehavior. The three different modes are
     * frightened, scattered, chased.
     */
    private void moveGhosts() {
        int rowPac = this.pacman.getCenterY() / Constants.SQUARE_SIZE;
        int colPac = this.pacman.getCenterX() / Constants.SQUARE_SIZE;
        for (int i = 0; i < this.maze.getGhostArrayList().size(); i++) {
            Ghost ghost = this.maze.getGhostArrayList().get(i);
            switch(this.ghostBehavior) {
                case FRIGHTENED:
                    this.frightenedMove(ghost);
                    break;
                case SCATTERED:
                    ghost.scatterMove(i, ghost);
                    break;
                case CHASED:
                    ghost.chaseMove(i, ghost, rowPac, colPac);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Helper method in charge of frightened movement.
     * @param ghost - the ghost that is conducting the frightened movement
     */
    private void frightenedMove(Ghost ghost) {
        int randCol = (int) (Math.random() * Constants.MAZE_DIMENSION);
        int randRow = (int) (Math.random() * Constants.MAZE_DIMENSION);
        //Ghost moves to constantly updating random BoardCoordinate in Maze
        ghost.ghostMove(new BoardCoordinate(randRow, randCol, true), ghost);
        //Changes ghost color to white to warn user about to exit frightened mode
        if (this.frightenedCounter > Constants.FRIGHTENED_CHANGE_COLOR_TIME) {
            ghost.setColor(Color.WHITE);
        }
        else {
            ghost.setColor(Constants.GHOST_FRIGHTENED_COLOR);
        }
        //exit frightened mode
        if (this.frightenedCounter > Constants.FRIGHTENED_TIME) {
            this.ghostResetColor();
            this.ghostBehavior = GhostBehavior.CHASED;
            this.frightenedCounter = 0;
        }
        else {
            this.frightenedCounter += Constants.TIME_COUNTER_INCREMENT;
        }

    }

    /**
     * Helper method that manipulates the this.ghostBehavior variable to be in scatter or chase mode.
     */
    private void scatterOrChase() {
        //if currently in chase mode
        if (this.ghostBehavior != GhostBehavior.SCATTERED) {
            //end chase mode / change to scatter mode
            if (this.chaseCounter > Constants.CHASE_TIME) {
                this.ghostBehavior = GhostBehavior.SCATTERED;
                this.chaseCounter = 0;
            }
            else {
                this.chaseCounter += Constants.TIME_COUNTER_INCREMENT;
            }
        }
        //if currently in scatter mode
        else  {
            //end scatter mode / change to chase mode
            if (this.scatterCounter > Constants.SCATTER_TIME) {
                this.ghostBehavior = GhostBehavior.CHASED;
                this.scatterCounter = 0;
            }
            else {
                this.scatterCounter+= Constants.TIME_COUNTER_INCREMENT;
            }
        }
    }

    /**
     * Method that checks for collisions between Pacman and each collidable element and the respective MazeSquare that
     * the pacman is intersecting.
     */
    private void checkCollisions() {
        int col = this.pacman.getCenterX() / Constants.SQUARE_SIZE;
        int row = this.pacman.getCenterY() / Constants.SQUARE_SIZE;
        //get the ArrayList at Pacman's current position
        ArrayList<Collidable> mazeSquareCollidables = this.maze.getMaze()[row][col].getArrayList();
        //loop through Collidable ArrayList
        for (int i = 0; i < mazeSquareCollidables.size(); i++) {
            mazeSquareCollidables.get(i).collision();
            this.updateScores(mazeSquareCollidables.get(i).getIncrement());
            //remove Collidable from the MazeSquare's ArrayList
            //if Collidable is an energizer, change mode to frightened
            if (mazeSquareCollidables.get(i).getSize() == Constants.ENERGIZER_RADIUS) {
                this.ghostBehavior = GhostBehavior.FRIGHTENED;
            }
            //if Collidable is not a Ghost, then decrement this.gameOverCounter
            if (mazeSquareCollidables.get(i).getSize() != Constants.SQUARE_SIZE) {
                this.gameOverCounter --;
            }
            mazeSquareCollidables.remove(mazeSquareCollidables.get(i));

        }

    }

    /**
     * Method that releases ghosts one by one until the ghostPen is empty. Blinky starts outside the pen initially,
     * so the order goes Blinky -> Pinky -> Inky -> Clyde on the order of exiting the pen.
     */
    private void letOutGhosts() {
        if (this.ghostPenCounter % 20 == 0) {
            if (this.ghostPen.size() != 0) {
                Ghost removedGhost = this.ghostPen.getFirst();
                removedGhost.setY(Constants.BLINKY_START_Y);
                removedGhost.setX(Constants.BLINKY_START_X);
                this.ghostPen.remove(removedGhost);
            }
        }
    }

    /**
     * Helper method that changes the gameLabel from Ready -> Go. After a couple of seconds, the "Go" label disappears.
     */
    private void removeGameLabelText() {
        if (this.timeline.getStatus() == Animation.Status.RUNNING) {
            if (this.gameLabelDisCounter == Constants.GAME_LABEL_TIME) {
                this.gameLabel.setTextFill(Color.NAVY);
            }
            this.gameLabel.setText("Go!");
            this.gameLabel.setLayoutX(Constants.GAME_LABEL_LAYOUT_X_GO);
        }
    }


    /**
     * Method that restarts the Game whenever a Ghost comes into contact with the Pacman.
     */
    public void restartGame() {
        this.timeline.stop();
        this.resetGhostPen();
        this.setStartLoc();
        this.restartChangeLabels();
    }



    /**
     * Helper method that restarts the labels. It decrements a Pacman life and changes the gameLabel text back to
     * "Ready."
     */
    private void restartChangeLabels() {
        this.gameLabelDisCounter = 0;
        this.livesCounter --;
        this.livesLabel.setText(" Lives: " + this.livesCounter);
        this.gameLabel.setTextFill(Color.YELLOW);
        this.gameLabel.setText("Ready!");
        this.gameLabel.setLayoutX(Constants.GAME_LABEL_LAYOUT_X_READY);
    }

    /**
     * Updates scores whenever the Pacman collides with a Collidable.
     * @param scoreIncrement - the increment that the score increases.
     */
    public void updateScores(int scoreIncrement) {
        this.scoreCounter += scoreIncrement;
        this.scoreLabel.setText("Score: " + this.scoreCounter);
    }

    /**
     * Helper method that resets the ghostPen whenever the game restarts. The ghosts get added back to the Queue data
     * structure in order of first in first out.
     */
    private void resetGhostPen() {
        this.ghostPen.clear();
        this.ghostPen.addLast(this.pinky);
        this.ghostPen.addLast(this.inky);
        this.ghostPen.addLast(this.clyde);
        this.ghostResetColor();
        this.ghostPenCounter = 0;
    }

    /**
     * Helper method that resets the color of the respective ghosts.
     */
    private void ghostResetColor() {
        this.inky.setColor(Constants.INKY_COLOR);
        this.clyde.setColor(Constants.CLYDE_COLOR);
        this.pinky.setColor(Constants.PINKY_COLOR);
        this.blinky.setColor(Constants.BLINKY_COLOR);
    }

    /**
     * Helper method that sets up the starting locations of the pacman and the respective ghosts.
     */
    private void setStartLoc() {
        this.pacman.setCenterX(Constants.PACMAN_START_X);
        this.pacman.setCenterY(Constants.PACMAN_START_Y);
        this.inky.setX(Constants.INKY_START_X);
        this.inky.setY(Constants.INKY_START_Y);
        this.clyde.setX(Constants.CLYDE_START_X);
        this.clyde.setY(Constants.CLYDE_START_Y);
        this.pinky.setX(Constants.PINKY_START_X);
        this.pinky.setY(Constants.PINKY_START_Y);
        this.blinky.setX(Constants.BLINKY_START_X);
        this.blinky.setY(Constants.BLINKY_START_Y);
    }

    /**
     * Method that checks to see if the game is over. The game is over in two different scenarios. First, the game
     * could end in a win where there are no more dots or energizers left, or in the game could end in a loss where
     * there are no Pacman lives left.
     * @param counter - double that tracks livesCounter or gameOverCounter
     * @param win - boolean that tracks if the user won
     */
    private void checkGameOver(double counter, boolean win) {
        if (counter <= 0) {
            //if there are no more dots/energizers left on the screen
            if (win) {
                this.timeline.stop();
                this.gameLabel.setLayoutX(Constants.GAME_OVER_LABEL_LAYOUT_X);
                this.gameLabel.setTextFill(Color.YELLOW);
                this.gameLabel.setText("You Won!!");
                this.ghostResetColor();
            }
            //if there are no lives left
            else {
                this.timeline.stop();
                this.gameLabel.setLayoutX(Constants.GAME_OVER_LABEL_LAYOUT_X);
                this.gameLabel.setTextFill(Color.YELLOW);
                this.gameLabel.setText("Game Over!!");
            }
        }

    }

    /**
     * Getter method that returns the current Ghost mode.
     * @return this.ghostBehavior - current ghost mode
     */
    public GhostBehavior getGhostBehavior() {
        return this.ghostBehavior;
    }

    /**
     * Getter method that returns the ghostPen Queue.
     * @return this.ghostPen - the ghostPen Queue data structure
     */
    public LinkedList<Ghost> getGhostPen() {
        return this.ghostPen;
    }

}
