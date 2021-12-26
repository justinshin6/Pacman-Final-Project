package pacman;

import javafx.scene.paint.Color;

/**
 * Collidable interface that is implemented in the dot, energizer, and the ghost classes.
 */
public interface Collidable {

    //declaring Collidable methods
    void createCollidable(int y, int x, Color color);
    void collision();
    int getSize();
    int getIncrement();

}
