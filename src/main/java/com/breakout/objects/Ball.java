package com.breakout.objects;

import com.breakout.utils.GameConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ball {
    private float x, y; // Current position of the ball.
    private final float radius = GameConstants.BALL_RADIUS; // Radius of the ball.
    private float dx = GameConstants.INITIAL_BALL_DX; // Horizontal speed of the ball.
    private float dy = GameConstants.INITIAL_BALL_DY; // Vertical speed of the ball.
    private float maxSpeedChange = GameConstants.INITIAL_BALL_MAX_SPEED_CHANGE; // Maximum speed change upon collision with paddle.
    private boolean isAboutToExpire = false; // Flag indicating if the ball speedboost is about to expire.

    // Sets the ball's initial position based on the paddle's position.
    public void setInitialPosition(int paddleX, int paddleY, int paddleWidth) {
        this.x = paddleX + paddleWidth / 2f;
        this.y = paddleY - radius - 30; // Position above the paddle.
    }

    // Updates the ball's position based on its speed.
    public void move() {
        x += dx;
        y += dy;
    }

    // Reverses the ball's horizontal direction.
    public void reverseDirectionX() {
        dx = -dx;
    }

    // Reverses the ball's vertical direction.
    public void reverseDirectionY() {
        dy = -dy;
    }

    // Adjusts the ball's direction and speed based on the collision impact point.
    public void reversePaddleCollision(float impactPoint) {
        reverseDirectionY();
        dx = dx + impactPoint * maxSpeedChange;
        dx = Math.min(Math.max(dx, -maxSpeedChange), maxSpeedChange);
    }

    // Increases the ball's speed.
    public void increaseSpeed(float speedBoost) {
        dx *= speedBoost;
        dy *= speedBoost;
        maxSpeedChange *= speedBoost;
    }

    // Decreases the ball's speed.
    public void decreaseSpeed(float speedBoost) {
        dx /= speedBoost;
        dy /= speedBoost;
        maxSpeedChange /= speedBoost;
    }
}
