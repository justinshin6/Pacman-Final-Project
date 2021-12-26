package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

/**
 * Dot class that is in charge of the functionality associated with the dot. It implements the Collidable interface.
 */
public class Dot implements Collidable{

    //instance variables
    private Pane gamePane;
    private Circle dot;

    /**
     * Dot constructor
     * @param gamePane - gamePane where dot is located
     */
    public Dot(Pane gamePane) {
        this.gamePane = gamePane;
    }

    /**
     * Defining Collidable createCollidable() method for the dot class specifically.
     * @param y - y-position of the dot
     * @param x - x-position of the dot
     * @param color - color of the dot
     */
    @Override
    public void createCollidable(int y, int x, Color color) {
        this.dot = new Circle(Constants.DOT_RADIUS, color);
        this.dot.setCenterX(x + Constants.DOT_OFFSET);
        this.dot.setCenterY(y + Constants.DOT_OFFSET);
        this.gamePane.getChildren().add(this.dot);
    }

    /**
     * Defining Collidable collision() method for the dot class specifically. Whenever the pacman collides with
     * the dot, the dot is removed both graphically from the gamePane and logically (by setting to null).
     */
    @Override
    public void collision() {
        this.gamePane.getChildren().remove(this.dot); //graphically
        this.dot = null; //logically
    }

    /**
     * Collidable method that gets the current size of the dot
     * @return Constants.DOT_RADIUS - radius of the dot
     */
    @Override
    public int getSize() {
        return Constants.DOT_RADIUS;

    }

    /**
     * Collidable method that returns the score increment for colliding with a dot.
     * @return Constants.DOT_SCORE - the dot score increment
     */
    @Override
    public int getIncrement() {
        return Constants.DOT_SCORE;
    }

    /**
     * Getter method that gets the center x-location of the dot
     * @return this.dot.getCenterX() - the center x-location of the dot
     */
    public int getX() {
        return (int) this.dot.getCenterX();
    }

    /**
     * Getter method that gets the center y-location of the dot
     * @return this.dot.getCenterY() - the center y-location of the dot
     */
    public int getY() {
        return (int) this.dot.getCenterY();
    }

}
