import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Paddle implements Collidable {
    private int x, y;
    private final int width = GameConstants.PADDLE_WIDTH;
    private final int height = GameConstants.PADDLE_HEIGHT;
    private final int marginFromBottom = GameConstants.PADDLE_MARGIN_FROM_BOTTOM;
    private final int speed = GameConstants.PADDLE_SPEED_KEYBOARD;
    private boolean isControlInverted = false;
    private boolean isAboutToExpire = false;
    private int panelWidth;
    public void setPosition(int panelWidth, int panelHeight) {
        this.panelWidth=panelWidth;
        x = panelWidth / 2 - width / 2;
        y = panelHeight - height - marginFromBottom;
    }

    public void moveLeft() {
        if (isControlInverted)
            x += speed;
        else
            x -= speed;
        checkMovement(panelWidth);
    }

    public void moveRight() {
        if (isControlInverted)
            x -= speed;
        else
            x += speed;
        checkMovement(panelWidth);
    }

    private void checkMovement(int panelWidth) {
        if (x < 0) {
            x = 0;
        }
        if (x + width > panelWidth) {
            x = panelWidth - width;
        }
    }
}
