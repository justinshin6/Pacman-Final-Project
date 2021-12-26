package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Pacman class that is in charge of the functionality of the pacman. Associated with the maze since the pacman is
 * initially instantiated in the maze.
 */
public class Pacman {

    //instance variables
    private Pane gamePane;
    private Maze maze;
    private Direction curDirection;
    private Direction nextDirection;
    private Circle pacman;

    /**
     * Pacman constructor
     * @param gamePane - gamePane that pacman is on
     * @param maze - association with the maze class
     * @param y - y-position of the pacman
     * @param x - x-position of the pacman
     */
    public Pacman(Pane gamePane, Maze maze, int y, int x) {
        this.gamePane = gamePane;
        this.maze = maze;
        this.setUpPacman(y, x);
        this.curDirection = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;

    }

    /**
     * Helper method that helps set up the pacman.
     * @param y - y-position of the pacman
     * @param x - x-position of the pacman
     */
    private void setUpPacman(int y, int x) {
        this.pacman = new Circle(x + Constants.DOT_OFFSET, y + Constants.DOT_OFFSET,
                Constants.PACMAN_RADIUS, Color.YELLOW);
        this.pacman.setFill(Color.YELLOW);
        this.gamePane.getChildren().add(this.pacman);
    }

    /**
     * Changes the direction that the pacman is moving in if the direction is not into a wall.
     * @param newDirection - the newDirection for the pacman
     */
    public void changeDirection(Direction newDirection) {
        if (!(newDirection == this.curDirection)) {
            int newRow = newDirection.newRow((int) this.pacman.getCenterY() / Constants.SQUARE_SIZE);
            int newCol = newDirection.newCol((int) this.pacman.getCenterX() / Constants.SQUARE_SIZE);
            //checks if newDirection is not into a wall
            if (!this.maze.isThereWall(newRow, newCol)) {
                this.nextDirection = newDirection;
            }
        }
    }

    /**
     * Method that moves the pacman based off the this.nextDirection instance variable.
     */
    public void movePacman() {
        this.curDirection = this.nextDirection;
        int newRow = this.nextDirection.newRow((int) this.pacman.getCenterY() / Constants.SQUARE_SIZE);
        int newCol = this.nextDirection.newCol((int) this.pacman.getCenterX() / Constants.SQUARE_SIZE);
        this.checkMoveValidity(newRow, this.wrapPacman(newRow, newCol));
    }

    /**
     * Checks to see if the move for the pacman in the movePacman() method is valid.
     * @param newRow - the row that the pacman wants to move into
     * @param newCol - the col that the pacman wants to move into
     */
    private void checkMoveValidity(int newRow, int newCol) {
        //if there's no wall, move pacman
        if (!this.maze.isThereWall(newRow, newCol)) {
            this.pacman.setCenterY(newRow * Constants.SQUARE_SIZE + Constants.DOT_OFFSET);
            this.pacman.setCenterX( newCol * Constants.SQUARE_SIZE + Constants.DOT_OFFSET);
        }
    }

    /**
     * Helper method that is in charge of wrapping the pacman.
     * @param newRow - the newRow that the pacman wants to move into
     * @param newCol - the newCol that the pacman wants to move into
     * @return newCol - adjusted newCol for wrapping
     */
    private int wrapPacman(int newRow, int newCol) {
        //wrap on right side
        if (newRow == Constants.MAZE_DIMENSION / 2 && newCol == Constants.MAZE_DIMENSION) {
            newCol -= Constants.MAZE_DIMENSION;
        }
        //wrap on left side
        else if (newRow == Constants.MAZE_DIMENSION / 2 && newCol == -1) {
            newCol += Constants.MAZE_DIMENSION;
        }
        //return the new col value
        return newCol;
    }

    /**
     * Getter method that gets the pacman.
     * @return this.pacman - gets the pacman
     */
    public Circle getPacman() {
        return this.pacman;
    }

    /**
     * Getter method that returns the center x-location of the pacman.
     * @return this.pacman.getCenterX() - the center x-location of the pacman.
     */
    public int getCenterX() {
        return (int) this.pacman.getCenterX();
    }

    /**
     * Getter method that returns the center y-location of the pacman.
     * @return this.pacman.getCenterY() - the center y-location of the pacman.
     */
    public int getCenterY() {
        return (int) this.pacman.getCenterY();
    }

    /**
     * Setter method that sets a new center x-location for the pacman.
     * @param newCenterX - the new center x-location for the pacman.
     */
    public void setCenterX(int newCenterX) {
        this.pacman.setCenterX(newCenterX);
    }
    /**
     * Setter method that sets a new center y-location for the pacman.
     * @param newCenterY - the new center y-location for the pacman.
     */
    public void setCenterY(int newCenterY) {
        this.pacman.setCenterY(newCenterY);
    }

}