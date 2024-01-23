import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class Brick implements Collidable{
    private final int x, y;
    private final int width,height;
    private boolean isVisible;

    public Brick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true; // Initially, all bricks are visible
    }


    // Setter for isVisible - call this when the brick is hit by the ball
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    // Getters for the brick's properties
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return isVisible;
    }

    // Add additional methods as needed, e.g., for collision detection
}
