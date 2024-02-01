import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Paddle implements Collidable {
    private int x, y;
    private final int width = 130;
    private final int height = 20;
    private final int marginFromBottom = 40;
    private final int speed = 40; // Regola questa per cambiare la velocità del paddle
    private boolean isControlInverted = false;
    private boolean isAboutToExpire = false;
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
}
