import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Paddle {
    private int x, y;
    private int width = 120;
    private int height = 20;
    private int marginFromBottom = 40;
    private int speed = 40; // Regola questa per cambiare la velocità del paddle

    public Paddle(){

    }
    public void setPosition(int panelWidth, int panelHeight) {
        // Initialize the paddle at the bottom center of the panel
        x = panelWidth / 2 - width / 2; // Center the paddle horizontally
        y = panelHeight - height - marginFromBottom; // Position the paddle above the bottom
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void moveLeft() {
        x -= speed;
        if (x < 0) {
            x = 0; // Impedisce al paddle di uscire dal pannello
        }
    }

    public void moveRight(int panelWidth) {
        x += speed;
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

    public void checkCollision(Ball ball) {
        if (new Rectangle(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), ball.getRadius()*2 , ball.getRadius()*2 ).intersects(new Rectangle(x, y, width, height))) {
            ball.reverseDirectionY(); // Inverti la direzione verticale della pallina
        }
    }
    // Additional methods for movement, collision, etc.
}
