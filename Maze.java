package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import cs15.fnl.pacmanSupport.CS15SupportMap;
import cs15.fnl.pacmanSupport.CS15SquareType;

import java.util.ArrayList;

/**
 * Maze class that is in charge of initially setting up the Maze component of the pacman game. Associated with the
 * game class so that the ghosts, which are instantiated in this class, can also know about the game class.
 */
public class Maze {

    //instance variables
    private MazeSquare[][] maze;
    private Pane gamePane;
    private Pacman pacman;
    private ArrayList<Ghost> ghostArrayList;
    private ArrayList<MazeSquare> wallList;
    private Game game;

    /**
     * Maze constructor
     * @param gamePane - the gamePane that the maze is going to be set on
     * @param game - association with the game class
     */
    public Maze(Pane gamePane, Game game) {
        this.gamePane = gamePane;
        this.game = game;
        this.createMaze();
    }

    /**
     * Helper method that sets up the initial positions for all the elements (walls, free spaces, dots, energizers,
     * pacman, and ghost starting locations) using a support map from the CS15SupportMap class.
     */
    public void createMaze() {
        CS15SquareType[][] supportMap = CS15SupportMap.getSupportMap();
        this.maze = new MazeSquare[Constants.MAZE_DIMENSION][Constants.MAZE_DIMENSION];
        this.wallList = new ArrayList<>();

        for (int row = 0; row < Constants.MAZE_DIMENSION; row++) {
            for (int col = 0; col < Constants.MAZE_DIMENSION; col++) {
                int y = row * Constants.SQUARE_SIZE;
                int x = col * Constants.SQUARE_SIZE;
                switch (supportMap[row][col]) {
                    case WALL:
                        MazeSquare wall = new MazeSquare(this.gamePane, Color.NAVY, y, x);
                        this.maze[row][col] = wall;
                        this.wallList.add(wall);
                        break;
                    case FREE:
                        this.maze[row][col] = new MazeSquare(this.gamePane, Color.BLACK, y, x);
                        break;
                    case DOT:
                        this.dotEnergizerSetUp(true, row, col, y, x);
                        break;
                    case ENERGIZER:
                        this.dotEnergizerSetUp(false, row, col, y, x);
                        break;
                    case PACMAN_START_LOCATION:
                        this.maze[row][col] = new MazeSquare(this.gamePane, Color.BLACK, y, x);
                        this.pacman = new Pacman(this.gamePane, this, y, x);
                        break;
                    case GHOST_START_LOCATION:
                        this.ghostArrayList = new ArrayList<>();
                        this.maze[row][col] = new MazeSquare(this.gamePane, Color.BLACK, y, x);
                        int newCol2 = (x + Constants.SQUARE_SIZE) / Constants.SQUARE_SIZE;
                        this.maze[row][newCol2] = new MazeSquare(this.gamePane, Color.BLACK, y, x
                                + Constants.SQUARE_SIZE);

                        this.setUpGhostsHelper(y, x, row, col, Constants.INKY_COLOR);
                        this.setUpGhostsHelper(y, x + Constants.SQUARE_SIZE, row,
                                (x + Constants.SQUARE_SIZE) / Constants.SQUARE_SIZE, Constants.CLYDE_COLOR);
                        this.setUpGhostsHelper(y, x - Constants.SQUARE_SIZE, row,
                                (x - Constants.SQUARE_SIZE) / Constants.SQUARE_SIZE, Constants.PINKY_COLOR);
                        this.setUpGhostsHelper(y - Constants.SQUARE_SIZE * 2, x, (y -
                                Constants.SQUARE_SIZE * 2) / Constants.SQUARE_SIZE, col, Constants.BLINKY_COLOR);

                        break;
                    default:
                        break;
                }

            }
        }
        //set all the Ghosts to the front
        for (Ghost ghost : this.ghostArrayList) {
            ghost.setToFront();
        }
    }

    /**
     * Helper method to set up the dots and the energizers to reduce redundant code.
     * @param dot - boolean that returns true if collidable is a dot
     * @param row - row in Maze
     * @param col - col in Maze
     * @param y - y-location of the collidable
     * @param x - x-location of the collidable
     */
    private void dotEnergizerSetUp(boolean dot, int row, int col, int y, int x) {
        this.maze[row][col] = new MazeSquare(this.gamePane, Color.BLACK, y, x);
        Collidable collidable;
        if (dot) {
            collidable = new Dot(this.gamePane);
        }
        else {
            collidable = new Energizer(this.gamePane);
        }
        collidable.createCollidable(y, x, Color.WHITE);
        this.maze[row][col].getArrayList().add(collidable);
    }

    /**
     * Helper method that sets up the ghost.
     * @param y - y-position of the ghost
     * @param x - x-position of the ghost
     * @param row - row of the Maze
     * @param col - col of the Maze
     * @param color - color of the ghost
     */
    private void setUpGhostsHelper(int y, int x, int row, int col, Color color) {
        Ghost ghost = new Ghost(this.gamePane, this, this.game);
        ghost.createCollidable(y, x, color);
        this.ghostArrayList.add(ghost);
        this.maze[row][col].getArrayList().add(ghost);
    }

    /**
     * Getter method that returns pacman.
     * @return this.pacman - returns pacman
     */
    public Pacman getPacman() {
        return this.pacman;
    }

    /**
     * Getter method that returns the Maze 2D array
     * @return this.maze - the Maze 2D array
     */
    public MazeSquare[][] getMaze() {
        return this.maze;
    }

    /**
     * Checks to see if a location at a certain row and col is a wall or not.
     * @param row - row that is being checked
     * @param col - col that is being checked
     * @return true if there is a wall
     */
    public boolean isThereWall(int row, int col) {
        //checks for edges cases when wrapping
        if (col < 0 || col > Constants.MAZE_DIMENSION - 1) {
            return true;
        }
        return this.wallList.contains(this.maze[row][col]);
    }

    /**
     * Getter method that returns the ArrayList that contains all the instances of the ghosts.
     * @return this.ghostArrayList - the ArrayList of the ghosts.
     */
    public ArrayList<Ghost> getGhostArrayList() {
        return this.ghostArrayList;
    }
}
