package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * This is the MazeSquare class that wraps one square of the maze.
 */
public class MazeSquare {

    //instance variables
    private Rectangle mazeSquare;
    private Pane gamePane;
    private int row;
    private int col;
    private ArrayList<Collidable> collidableArrayList;

    /**
     * This is the constructor of the MazeSquare class that constructs a MazeSquare.
     * @param gamePane the pane where the MazeSquare is going to be added
     * @param color the specific color of that MazeSquare
     * @param x x-location of the MazeSquare
     * @param y y-location of the MazeSquare
     */
    public MazeSquare(Pane gamePane, Color color, double y, double x) {
        this.gamePane = gamePane;
        this.collidableArrayList = new ArrayList<>();
        this.row = (int) y / Constants.SQUARE_SIZE;
        this.col = (int) x / Constants.SQUARE_SIZE;
        this.mazeSquare = new Rectangle(x,y, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.setColor(color);
        this.gamePane.getChildren().add(this.mazeSquare);
    }

    /**
     * Setter method that sets the fill of a MazeSquare to a certain color.
     * @param color - the new color of the MazeSquare
     */
    public void setColor(Color color) {
        this.mazeSquare.setFill(color);
    }

    /**
     * Getter method that gets the current row of the MazeSquare.
     * @return this.row - the row of the MazeSquare
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Getter method that gets the current col of the MazeSquare.
     * @return this.col - the col of the MazeSquare
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Getter method that gets the current ArrayList for the MazeSquare
     * @return this.collidableArrayList - the ArrayList for the MazeSquare
     */
    public ArrayList<Collidable> getArrayList() {
        return this.collidableArrayList;
    }
}
