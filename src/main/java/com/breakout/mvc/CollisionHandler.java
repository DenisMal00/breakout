package com.breakout.mvc;

import com.breakout.effects.GameEffect;
import com.breakout.objects.*;
import com.breakout.utils.Collidable;

import java.awt.*;

public class CollisionHandler {
    // Checks if the ball has collided with the paddle and adjusts its direction and speed accordingly.
    public void checkCollisionWithPaddle(Ball ball, Paddle paddle) {
        Rectangle collisionArea = getCollisionArea(ball, paddle);
        // If collision detected, calculate the impact point and reverse ball's direction.
        if (!collisionArea.isEmpty()) {
            double ballBottomY = ball.getY() + ball.getRadius() * 2;
            double paddleTopY = paddle.getY();
            // Check if ball hits the top part of the paddle.
            if (ballBottomY >= paddleTopY && ball.getY() < paddleTopY && ball.getDy() > 0) {
                // Calculate impact point for ball speed adjustment.
                float relativeImpactPoint = (ball.getX() - (paddle.getX() + paddle.getWidth() / 2.0f)) / (paddle.getWidth() / 2.0f);
                ball.reversePaddleCollision(relativeImpactPoint);
            }
        }
    }

    // Checks if the ball has collided with any brick and updates game state accordingly.
    public void checkCollisionWithBricks(Ball ball, BrickMaps bricks, GameModel gameModel) {
        Brick collidedBrick = null;
        double largestOverlap = 0;
        Rectangle largestOverlapRect = new Rectangle();
        // Detect the brick with the largest overlapping area with the ball.
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                Rectangle overlap = getCollisionArea(ball, brick);
                double overlapArea = overlap.getWidth() * overlap.getHeight();
                if (overlapArea > largestOverlap) {
                    largestOverlap = overlapArea;
                    largestOverlapRect = overlap;
                    collidedBrick = brick;
                }
            }
        }
        // Change ball direction and handle brick collision effects.
        changeBallDirection(ball, gameModel, collidedBrick, largestOverlapRect);
    }

    // Adjusts ball's direction after collision with a brick and applies any resulting effects.
    private void changeBallDirection(Ball ball, GameModel gameModel, Brick collidedBrick, Rectangle largestOverlapRect) {
        if (collidedBrick != null) {
            // Reverse ball direction based on the collision area.
            if (largestOverlapRect.getWidth() > largestOverlapRect.getHeight()) {
                ball.reverseDirectionY();
            } else {
                ball.reverseDirectionX();
            }
            // Apply effects if the brick was hit and destroyed.
            if (collidedBrick.hit()) {
                GameEffect newEffect = gameModel.getPowerUpForBrick(collidedBrick);
                if (newEffect != null) {
                    newEffect.setVisible(true);
                    gameModel.addEffect(newEffect);
                }
            }
        }
    }

    // Checks for ball collision with the walls and force field, reversing direction as necessary.
    public boolean checkWallCollision(Ball ball, int panelWidth, int panelHeight, ForceField forceField) {
        float x = ball.getX();
        float y = ball.getY();
        float radius = ball.getRadius();
        // Reverse ball direction if it hits the left or right wall.
        if (x < radius || x > panelWidth - radius) {
            ball.reverseDirectionX();
        }
        // Reverse ball direction if it hits the top wall or the force field.
        if (y < radius) {
            ball.reverseDirectionY();
        } else if (forceField.isActive() && y >= forceField.getYPosition() - radius) {
            ball.reverseDirectionY();
        } else return y > panelHeight; // Return true if the ball falls below the bottom edge.
        return false;
    }

    // Calculates the area of collision between the ball and another collidable object.
    private Rectangle getCollisionArea(Ball ball, Collidable object) {
        // Create rectangles representing the positions of the ball and the object.
        Rectangle ballRect = new Rectangle(
                (int) (ball.getX() - ball.getRadius()),
                (int) (ball.getY() - ball.getRadius()),
                (int) (ball.getRadius() * 2),
                (int) (ball.getRadius() * 2));

        Rectangle objectRect = new Rectangle(
                object.getX(),
                object.getY(),
                object.getWidth(),
                object.getHeight());

        // Return the intersection area if the ball and object collide.
        return ballRect.intersects(objectRect) ? ballRect.intersection(objectRect) : new Rectangle();
    }

    // Checks if a power-up has collided with the paddle.
    public boolean checkGameEffectCollisionWithPaddle(GameEffect effect, Paddle paddle) {
        // Create rectangles for the power-up and the paddle to check for intersection.
        Rectangle powerUpRect = new Rectangle(effect.getX(), effect.getY(), effect.getWidth(), effect.getHeight());
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        return powerUpRect.intersects(paddleRect); // Return true if they intersect.
    }
}
