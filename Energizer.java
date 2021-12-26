package pacman;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The Energizer class that is in charge of the Energizer functionality. It implements the Collidable interface.
 */
public class Energizer implements Collidable {

    //instance variables
    private Pane gamePane;
    private Circle energizer;

    /**
     * Energizer constructor
     * @param gamePane - the gamePane that the energizers are on
     */
    public Energizer(Pane gamePane) {
        this.gamePane = gamePane;
    }

    /**
     * Defining Collidable createCollidable() method for the energizer class specifically.
     * @param y - y-position of the energizer
     * @param x - x-position of the energizer
     * @param color - color of the energizer
     */
    @Override
    public void createCollidable(int y, int x, Color color) {
        this.energizer = new Circle(Constants.ENERGIZER_RADIUS, color);
        this.energizer.setCenterX(x + Constants.DOT_OFFSET);
        this.energizer.setCenterY(y + Constants.DOT_OFFSET);
        this.gamePane.getChildren().add(this.energizer);

    }

    /**
     * Defining Collidable collision() method for the energizer class specifically. Whenever the pacman collides with
     * the energizer, the energizer is removed both graphically from the gamePane and logically (by setting to null).
     */
    @Override
    public void collision() {
        this.gamePane.getChildren().remove(this.energizer); //graphically
        this.energizer = null; //logically
    }

    /**
     * Collidable method that returns the score increment for colliding with an energizer.
     * @return Constants.ENERGIZER_SCORE - the energizer score increment
     */
    @Override
    public int getIncrement() {
        return Constants.ENERGIZER_SCORE;
    }

    /**
     * Collidable method that gets the current size of the energizer
     * @return Constants.ENERGIZER_RADIUS - radius of the energizer
     */
    @Override
    public int getSize() {
        return Constants.ENERGIZER_RADIUS;
    }

}
