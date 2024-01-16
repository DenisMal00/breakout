import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class Brick {
    private int x, y,width, height;
    private boolean isVisible;

    public Brick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true; // Initially, all bricks are visible
    }

    public void draw(Graphics2D g) {
        if (isVisible) {
            g.setColor(Color.GREEN); // Set the color of the brick
            g.fillRect(x, y, width, height); // Draw the brick as a filled rectangle

            // Optionally, draw the border of the brick
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
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
