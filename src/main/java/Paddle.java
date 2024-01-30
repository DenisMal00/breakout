public class Paddle implements Collidable {
    private int x, y;
    private static final int width = 130;
    private static final int height = 20;
    private static final int marginFromBottom = 40;
    private static final int speed = 40; // Regola questa per cambiare la velocità del paddle
    private boolean isControlInverted = false;

    public Paddle(){}
    public void setPosition(int panelWidth, int panelHeight) {
        // Initialize the paddle at the bottom center of the panel
        x = panelWidth / 2 - width / 2; // Center the paddle horizontally
        y = panelHeight - height - marginFromBottom; // Position the paddle above the bottom
    }


    public void moveLeft(int panelWidth) {
        if (isControlInverted)
            x += speed;
        else
            x -= speed;
        checkMovement(panelWidth);
    }

    public void moveRight(int panelWidth) {
        if (isControlInverted)
            x -= speed;
        else
            x += speed;
        checkMovement(panelWidth);
    }

    private void checkMovement(int panelWidth) {
        if (x < 0) {
            x = 0; // Impedisce al paddle di uscire dal pannello
        }
        if (x + width > panelWidth) {
            x = panelWidth - width; // Impedisce al paddle di uscire dal pannello
        }
    }

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

    public void setControlInverted(boolean isControlInverted) {
        this.isControlInverted = isControlInverted;
    }

    public boolean isControlInverted() {
        return isControlInverted;
    }

    public void setX(int x) {
        this.x=x;
    }
}
