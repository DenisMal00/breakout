import java.awt.*;

public class Brick implements Collidable{
    private final int x, y;
    private final int width,height;
    private boolean isVisible;
    private final boolean isDestructable;
    private int hitPoints; // Livelli di resistenza del mattone


    public Brick(int x, int y, int width, int height, boolean isDestructable,int hitPoints) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = true; // Initially, all bricks are visible
        this.isDestructable=isDestructable;
        this.hitPoints=hitPoints;
    }

    public void hit() {
        if (hitPoints == 0)
            isVisible = false;
        if (hitPoints > 0)
            hitPoints--;
    }
    public Color getColor() {
        switch (hitPoints) {
            case 0:
                return Color.GREEN; // Colore per mattoni che si rompono subito
            case 1:
                return Color.ORANGE; // Colore per mattoni che richiedono 1 colpo
            case 2:
                return Color.RED; // Colore per mattoni che richiedono 2 colpi
            default:
                return Color.gray; //colore per mattoni che non si distruggono
        }
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

    public boolean isDestructable() {
        return isDestructable;
    }
    // Add additional methods as needed, e.g., for collision detection
}
