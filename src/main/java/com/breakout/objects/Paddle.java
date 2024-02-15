package com.breakout.objects;

import com.breakout.utils.Collidable;
import com.breakout.utils.GameConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Paddle implements Collidable {
    private int x, y; // Position of the paddle.
    private final int width = GameConstants.PADDLE_WIDTH; // Width of the paddle.
    private final int height = GameConstants.PADDLE_HEIGHT; // Height of the paddle.
    private final int marginFromBottom = GameConstants.PADDLE_MARGIN_FROM_BOTTOM; // Distance from bottom of the panel.
    private final int speed = GameConstants.PADDLE_SPEED_KEYBOARD; // Movement speed of the paddle.
    private boolean isControlInverted = false; // Flag for reversed control effects.
    private boolean isAboutToExpire = false; // Flag for expiring effects (like control inversion).
    private int panelWidth; // Width of the game panel for boundary checks.

    // Sets the initial position of the paddle in the panel.
    public void setPosition(int panelWidth, int panelHeight) {
        this.panelWidth=panelWidth;
        // Center the paddle horizontally and place it above the bottom margin.
        x = panelWidth / 2 - width / 2;
        y = panelHeight - height - marginFromBottom;
    }

    // Moves the paddle left, considering inversion and panel bounds.
    public void moveLeft() {
        if (isControlInverted)
            x += speed;
        else
            x -= speed;
        checkMovement(panelWidth);
    }

    // Moves the paddle right, considering inversion and panel bounds.
    public void moveRight() {
        if (isControlInverted)
            x -= speed;
        else
            x += speed;
        checkMovement(panelWidth);
    }

    // Ensures the paddle does not move beyond the panel's bounds.
    private void checkMovement(int panelWidth) {
        if (x < 0) {
            x = 0;
        }
        if (x + width > panelWidth) {
            x = panelWidth - width;
        }
    }
}
