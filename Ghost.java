package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.LinkedList;

/**
 * This is the Ghost class which is in charge of the main functionality associated with the four different ghosts found
 * in the Pacman game. The main design choice here was that the Game class is associated with the Ghost class because
 * it was difficult to achieve some functionality (such as the Ghost collision method) without having this association
 * with the gameClass. Since the Ghost class is technically only in charge of one ghost, and the four ghosts are found
 * in the Game class, making an association between the two classes made it easier to achieve certain functionalities.
 * It also implements the Collidable interface.
 */
public class Ghost implements Collidable{

    //instance variable
    private Pane gamePane;
    private Maze maze;
    private Rectangle ghost;
    private Direction curDirection;
    private Direction nextDirection;
    private Game ghostGame;

    /**
     * Game constructor
     * @param gamePane - gamePane where Ghosts will be located
     * @param maze - association with the Maze class
     * @param game - association with the Game class
     */
    public Ghost(Pane gamePane, Maze maze, Game game) {
        this.gamePane = gamePane;
        this.maze = maze;
        this.ghostGame = game;
        this.setUpConstants();
    }

    /**
     * Helper method that sets up the Direction constants.
     */
    private void setUpConstants() {
        this.curDirection = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;

    }

    /**
     * Defining Collidable createCollidable() method for the ghost class specifically.
     * @param y - y-position for the Ghost
     * @param x - x-position for the Ghost
     * @param color - specific color for the Ghost
     */
    @Override
    public void createCollidable(int y, int x, Color color) {
        this.ghost = new Rectangle(x, y, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.ghost.setFill(color);
        this.gamePane.getChildren().add(this.ghost);
    }

    /**
     * Defining Collidable collision() method for the ghost class specifically. The frightened ghost to the front of
     * the Queue retrieved from the Game class because we want the first Ghost to be eaten to be the first Ghost that
     * exits the ghostPen.
     */
    @Override
    public void collision() {
        //if currently in frightened mode
        if (this.ghostGame.getGhostBehavior() == GhostBehavior.FRIGHTENED) {
            //retrieve ghostPen from Game class
            LinkedList<Ghost> ghostPen = this.ghostGame.getGhostPen();
            ghostPen.addFirst(this);
            this.ghost.setX(Constants.CLYDE_START_X);
            this.ghost.setY(Constants.CLYDE_START_Y);
            this.ghostGame.updateScores(Constants.GHOST_SCORE);
        }
        //if not in frightened mode, restart game
        else {
            this.ghostGame.restartGame();
        }

    }

    /**
     * Getter Collidable getSize() method.
     * @return Constants.SQUARE.SIZE - size of the Ghost
     */
    @Override
    public int getSize() {
        return Constants.SQUARE_SIZE;
    }

    /**
     * Getter Collidable getIncrement() method.
     * @return Constants.GHOST_SCORE - the score increment for Ghost collision
     */
    @Override
    public int getIncrement() {
        return Constants.GHOST_SCORE;
    }

    /**
     * Method that moves the Ghost using direction from this.ghostBFS() (see method below for more information).
     * @param target - the target that the ghost moves towards
     * @param ghost - the ghost that conducts the movement to the target
     */
    public void ghostMove(BoardCoordinate target, Ghost ghost) {
        //retrieve direction from BFS algorithm
        this.nextDirection = this.ghostBFS(target);
        //wrapping ghost
        if (this.nextDirection == null) {
            int newRow = Constants.MAZE_DIMENSION / 2;
            int newCol = 0;
            if (ghost.getX() == 0) {
                newCol = Constants.MAZE_DIMENSION - 1;
            }
            this.checkMoveValidity(ghost, newRow, newCol);
        }
        else {
            this.curDirection = this.nextDirection;
            int newRow = this.nextDirection.newRow((int) this.ghost.getY() / Constants.SQUARE_SIZE);
            int newCol = this.nextDirection.newCol((int) this.ghost.getX() / Constants.SQUARE_SIZE);
            this.checkMoveValidity(ghost, newRow, newCol);
        }

    }

    /**
     * Helper method that checks to see if move is valid for the ghost.
     * @param ghost - ghost that the method is checking
     * @param newRow - the row that the ghost wants to move into
     * @param newCol - the col that the ghost wants to move into
     */
    private void checkMoveValidity(Ghost ghost, int newRow, int newCol) {
        int curRow = (int) this.ghost.getY() / Constants.SQUARE_SIZE;
        int curCol = (int) this.ghost.getX() / Constants.SQUARE_SIZE;
        //Remove from ArrayList at currentPosition
        this.maze.getMaze()[curRow][curCol].getArrayList().remove(ghost);
        //is there is no wall, graphically move
        if (!this.maze.isThereWall(newRow, newCol)) {
            this.ghost.setY(newRow * Constants.SQUARE_SIZE);
            this.ghost.setX(newCol * Constants.SQUARE_SIZE);
        }
        //logically add to new ArrayList
        this.maze.getMaze()[newRow][newCol].getArrayList().add(ghost);

    }

    /**
     * Helper method that calculates minimum distance between two BoardCoordinates.
     * @param startingTarget - the starting BoardCoordinate
     * @param finalTarget - the target BoardCoordinate
     * @return the minimum distance between the two BoardCoordinates
     */
    public double calculateMinDistance(BoardCoordinate startingTarget, BoardCoordinate finalTarget) {
        double xSquared = Math.pow(finalTarget.getColumn() - startingTarget.getColumn(), 2);
        double ySquared = Math.pow(finalTarget.getRow() - startingTarget.getRow(), 2);
        return Math.sqrt(xSquared + ySquared);
    }

    /**
     * Method that implements the BFS algorithm which determines the shortest direction from the ghost to a target point.
     * @param target - the target that the ghost wants to reach.
     * @return - the direction that will take the ghost to the target.
     */
    public Direction ghostBFS(BoardCoordinate target) {
        //instantiate variable with large positive value
        double actualDistance = Double.POSITIVE_INFINITY;
        //set up direction to return
        Direction direction = null;
        //set up 2D array to contain BFS directions
        Direction[][] directionArray = new Direction[Constants.MAZE_DIMENSION][Constants.MAZE_DIMENSION];
        //set up Queue to add BoardCoordinates
        LinkedList<BoardCoordinate> ghostQueue = new LinkedList<>();

        int ghostCol = (int) this.ghost.getX() / Constants.SQUARE_SIZE;
        int ghostRow = (int) this.ghost.getY() / Constants.SQUARE_SIZE;
        BoardCoordinate ghostRoot = new BoardCoordinate(ghostRow, ghostCol, true);
        //check for valid neighbors starting from current ghost BoardCoordinate
        this.checkValidNeighbors(ghostRoot, directionArray, ghostQueue);
        //while the Ghost Queue is not empty
        while (!ghostQueue.isEmpty()) {
            //remove the first BoardCoordinate in Queue
            ghostRoot = ghostQueue.removeFirst();
            //retrieve row and column from that respective BoardCoordinate
            ghostCol = ghostRoot.getColumn();
            ghostRow = ghostRoot.getRow();
            //retrieve direction from 2D array for that respective BoardCoordinate
            Direction currentDirection = directionArray[ghostCol][ghostRow];
            //calculate minimum distance from that BoardCoordinate to a target location
            double minDistance = this.calculateMinDistance(ghostRoot, target);
            if (actualDistance > minDistance) {
                actualDistance = minDistance;
                direction = currentDirection;
            }
        }
        //return direction towards target
        return direction;
    }

    /**
     * Helper method that checks for valid neighbors around a specific Ghost position.
     * @param ghostRoot - the specific ghost position
     * @param directionArray - the 2D array containing directions
     * @param ghostQueue - the Queue data structure that contains BoardCoordinates
     */
    private void checkValidNeighbors(BoardCoordinate ghostRoot, Direction[][] directionArray,
                                    LinkedList<BoardCoordinate> ghostQueue ) {
        int newCol = ghostRoot.getColumn();
        int newRow = ghostRoot.getRow();
        Direction opposite = this.curDirection.opposite();
        this.validNeighborHelper(newRow + 1, newCol, opposite, Direction.DOWN, directionArray, ghostQueue);
        this.validNeighborHelper(newRow - 1, newCol, opposite, Direction.UP, directionArray, ghostQueue);
        this.validNeighborHelper(newRow, newCol + 1, opposite, Direction.RIGHT, directionArray, ghostQueue);
        this.validNeighborHelper(newRow, newCol - 1, opposite, Direction.LEFT, directionArray, ghostQueue);

    }

    /**
     * Helper method for the checkValidNeighbors() method.
     * @param newRow - the row for the ghost that is being checked
     * @param newCol - the col for the ghost that is being checked
     * @param oppositeDir - the direction opposite of the ghost's current direction
     * @param actualDirection - the actual direction that the ghsot is going
     * @param directionArray - the 2D array containing directions
     * @param ghostQueue - the Queue data structure that contains BoardCoordinates
     */
    private void validNeighborHelper(int newRow, int newCol, Direction oppositeDir, Direction actualDirection,
                                     Direction[][] directionArray, LinkedList<BoardCoordinate> ghostQueue) {
        //checks if there is wall, if the opposite direction is not the actual direction, and if there is
        //nothing currently in the 2D direction array
        if (!this.maze.isThereWall(newRow, newCol) && oppositeDir != actualDirection && directionArray[newRow][newCol]
                == null) {
            //add the new BoardCoordinate to the Queue
            ghostQueue.addLast(new BoardCoordinate(newRow, newCol, false));
            //log the direction into the 2D direction array
            directionArray[newCol][newRow] = actualDirection;
        }
    }

    /**
     * Method that conducts scatter movement on the ghost that differs based on the index.
     * @param index - index in GhostArray
     * @param ghost - Ghost that is conducting scatter move
     */
    public void scatterMove(int index, Ghost ghost) {
        switch(index) {
            //inky (sky blue)
            case 0:
                ghost.ghostMove(Constants.INKY_SCATTER_COORD, ghost);
                break;
            //clyde (orange)
            case 1:
                ghost.ghostMove(Constants.CLYDE_SCATTER_COORD, ghost);
                break;
            //pinky lavender
            case 2:
                ghost.ghostMove(Constants.PINKY_SCATTER_COORD, ghost);
                break;
            //blinky red
            case 3:
                ghost.ghostMove(Constants.BLINKY_SCATTER_COORD, ghost);
                break;
            default:
                break;

        }
    }
    /**
     * Method that conducts chase movement on the ghost that differs based on the index.
     * @param index - index in GhostArray
     * @param ghost - Ghost that is conducting scatter move
     * @param rowPacman - the current row of the pacman
     * @param colPacman - the current col of the pacman
     */
    public void chaseMove(int index, Ghost ghost, int rowPacman, int colPacman) {
        switch(index) {
            //inky (sky blue)
            case 0:
                ghost.ghostMove(new BoardCoordinate(rowPacman, colPacman + Constants.INKY_CHASE_OFFSET,
                        true), ghost);
                break;
            //clyde (orange)
            case 1:
                ghost.ghostMove(new BoardCoordinate(rowPacman - Constants.CLYDE_CHASE_OFFSET,
                        colPacman, true), ghost);
                break;
            //pinky lavender
            case 2:
                ghost.ghostMove(new BoardCoordinate(rowPacman + Constants.PINKY_CHASE_OFFSET_Y, colPacman -
                        Constants.PINKY_CHASE_OFFSET_X, true), ghost);
                break;
            //blinky red
            case 3:
                ghost.ghostMove(new BoardCoordinate(rowPacman, colPacman, true), ghost);
                break;
            default:
                break;

        }
    }

    /**
     * Getter method that returns the ghost's current x-position.
     * @return - current x-position
     */
    public int getX() {
        return (int) this.ghost.getX();
    }

    /**
     * Getter method that returns the ghost's current x-position.
     * @return - current x-position
     */
    public int getY() {
        return (int) this.ghost.getY();
    }


    /**
     * Setter method that sets a new x-location for the ghost.
     * @param newLoc - new x-position
     */
    public void setX(int newLoc) {
        this.ghost.setX(newLoc);
    }
    /**
     * Setter method that sets a new y-location for the ghost.
     * @param newLoc - new y-position
     */
    public void setY(int newLoc) {
        this.ghost.setY(newLoc);
    }

    /**
     * Setter method that sets a new color for the ghost.
     * @param color - new color
     */
    public void setColor(Color color) {
        this.ghost.setFill(color);
    }

    /**
     * Setter method that sets the Ghost to the front of the scene.
     */
    public void setToFront() {
        this.ghost.toFront();
    }

}
