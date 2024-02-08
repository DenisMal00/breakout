import java.awt.*;

public class Collision {
    public static void checkCollisionWithPaddle(Ball ball, Paddle paddle) {
        Rectangle collisionArea = getCollisionArea(ball, paddle);
        if (!collisionArea.isEmpty()) {
            double ballBottomY = ball.getY() + ball.getRadius() * 2;
            double paddleTopY = paddle.getY();
            if (ballBottomY >= paddleTopY && ball.getY() < paddleTopY && ball.getDy() > 0) {
                float relativeImpactPoint = (ball.getX() - (paddle.getX() + paddle.getWidth() / 2.0f)) / (paddle.getWidth() / 2.0f);
                ball.reversePaddleCollision(relativeImpactPoint);
            }
        }
    }
    public static void checkCollisionWithBricks(Ball ball, BrickMaps bricks, GameModel gameModel) {
        Brick collidedBrick = null;
        double largestOverlap = 0;
        Rectangle largestOverlapRect = new Rectangle();
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
        changeBallDirection(ball, gameModel, collidedBrick, largestOverlapRect);
    }

    private static void changeBallDirection(Ball ball, GameModel gameModel, Brick collidedBrick, Rectangle largestOverlapRect) {
        if (collidedBrick != null) {
            if (largestOverlapRect.getWidth() > largestOverlapRect.getHeight()) {
                ball.reverseDirectionY();
            } else {
                ball.reverseDirectionX();
            }
            if (collidedBrick.hit()) {
                GameEffect newEffect = gameModel.getPowerUpForBrick(collidedBrick);
                if (newEffect != null) {
                    newEffect.setVisible(true);
                    gameModel.addEffect(newEffect);
                }
            }

        }
    }

    public static boolean checkWallCollision(Ball ball, int panelWidth,int panelHeight, ForceField forceField) {
        float x = ball.getX();
        float y = ball.getY();
        float radius = ball.getRadius();
        if (x < radius || x > panelWidth - radius) {
            ball.reverseDirectionX();
        }
        if (y < radius) {
            ball.reverseDirectionY();
        } else if (forceField.isActive() && y >= forceField.getYPosition() - radius) {
            ball.reverseDirectionY();
        } else return y > panelHeight;
        return false;
    }

    private static Rectangle getCollisionArea(Ball ball, Collidable object) {
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

        if (ballRect.intersects(objectRect)) {
            return ballRect.intersection(objectRect);
        }
        return new Rectangle();
    }
    public static boolean checkPowerUpCollisionWithPaddle(GameEffect effect, Paddle paddle) {
        Rectangle powerUpRect = new Rectangle((int) effect.getX(), (int) effect.getY(), (int) effect.getSize(), (int) effect.getSize());

        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        return powerUpRect.intersects(paddleRect);
    }
}
