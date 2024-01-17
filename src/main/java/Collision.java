import java.awt.*;

public class Collision {
    public static void checkCollisionWithPaddle(Ball ball, Paddle paddle) {
        if (new Rectangle((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int) (ball.getRadius() * 2), (int) (ball.getRadius() * 2)).intersects(new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight()))) {
            float relativeImpactPoint = (ball.getX() - (paddle.getX() + paddle.getWidth() / 2.0f)) / (paddle.getWidth() / 2.0f);
            ball.reversePaddleCollision(relativeImpactPoint);
        }

    }

    public static void checkCollisionWithBricks(Ball ball, BrickMap bricks) {
        // Collisione con i mattoncini
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible() && new Rectangle((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int) (ball.getRadius() * 2), (int) (ball.getRadius() * 2)).intersects(new Rectangle(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight()))) {
                ball.reverseBrickCollision();
                brick.setVisible(false);
                // Rendi il mattone invisibile
                // Cambia la direzione verticale della pallina
                // Aggiorna il punteggio o altre logiche di gioco
            }
        }
    }
}
