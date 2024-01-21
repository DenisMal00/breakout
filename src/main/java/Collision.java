import java.awt.*;

public class Collision {
    public static void checkCollisionWithPaddle(Ball ball, Paddle paddle) {
        if (new Rectangle((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int) (ball.getRadius() * 2), (int) (ball.getRadius() * 2)).intersects(new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight()))) {
            if (ball.getY() + ball.getRadius() >= paddle.getY() && ball.getDy() > 0) { // Assicurati che la pallina si muova verso il basso
                float relativeImpactPoint = (ball.getX() - (paddle.getX() + paddle.getWidth() / 2.0f)) / (paddle.getWidth() / 2.0f);
                ball.reversePaddleCollision(relativeImpactPoint);
            }
        }

    }

    public static void checkCollisionWithBricks(Ball ball, BrickMap bricks) {
        Brick collidedBrick = null;
        double largestOverlap = 0;

        // Controlla la collisione con tutti i mattoncini
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                Rectangle ballRect = new Rectangle((int) (ball.getX() - ball.getRadius()),
                        (int) (ball.getY() - ball.getRadius()),
                        (int) (ball.getRadius() * 2),
                        (int) (ball.getRadius() * 2));
                Rectangle brickRect = new Rectangle(brick.getX(),
                        brick.getY(),
                        brick.getWidth(),
                        brick.getHeight());
                if (ballRect.intersects(brickRect)) {
                    // Calcola l'area di sovrapposizione
                    Rectangle overlap = ballRect.intersection(brickRect);
                    double overlapArea = overlap.getWidth() * overlap.getHeight();

                    // Memorizza il mattone con la maggiore area di sovrapposizione
                    if (overlapArea > largestOverlap) {
                        largestOverlap = overlapArea;
                        collidedBrick = brick;
                    }
                }
            }
        }
        // Gestisci la collisione con il mattone colpito
        if (collidedBrick != null) {
            boolean withinVerticalEdges = ball.getY() + ball.getRadius() > collidedBrick.getY() &&
                    ball.getY() - ball.getRadius() < collidedBrick.getY() + collidedBrick.getHeight();

            if (!withinVerticalEdges) {
                // Collisione con un lato verticale
                ball.reverseDirectionX();
            } else {
                // Collisione con un lato orizzontale
                ball.reverseDirectionY();
            }

            // Rendi il mattone invisibile
            collidedBrick.setVisible(false);
        }
    }

}
