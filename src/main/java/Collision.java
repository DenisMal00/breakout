import java.awt.*;

public class Collision {
    public static void checkCollisionWithPaddle(Ball ball, Paddle paddle) {
        Rectangle collisionArea = getCollisionArea(ball, paddle);
        if (!collisionArea.isEmpty()) {
            double ballBottomY = ball.getY() + ball.getRadius() * 2;
            double paddleTopY = paddle.getY();
            // Verifica che la pallina stia colpendo la parte superiore del paddle
            // e che si muova verso il basso
            if (ballBottomY >= paddleTopY && ball.getY() < paddleTopY && ball.getDy() > 0) {
                float relativeImpactPoint = (ball.getX() - (paddle.getX() + paddle.getWidth() / 2.0f)) / (paddle.getWidth() / 2.0f);
                ball.reversePaddleCollision(relativeImpactPoint);
            }
            // Nessuna azione è intrapresa se la pallina colpisce i lati o la parte inferiore del paddle
        }
    }
    public static void checkCollisionWithBricks(Ball ball, BrickMap bricks,GameModel gameModel) {
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
            // Determina la direzione della collisione
            if (largestOverlapRect.getWidth() > largestOverlapRect.getHeight()) {
                // Collisione sui lati orizzontali del mattone
                ball.reverseDirectionY();
            } else {
                // Collisione sui lati verticali del mattone
                ball.reverseDirectionX();
            }
            // Rendi invisibile il mattone e gestisci la collisione
            if (collidedBrick.isDestructable()) {
                int centerX = collidedBrick.getX() + collidedBrick.getWidth() / 2;
                int centerY = collidedBrick.getY() + collidedBrick.getHeight() / 2;
                GameEffect newEffect = GameEffectFactory.createRandomPowerUp(centerX, centerY);
                if (newEffect != null) {
                    gameModel.addEffect(newEffect);
                }
                collidedBrick.hit(); // Decrementa i hitPoints e aggiorna il colore
            }
        }
    }

    public static boolean checkWallCollision(Ball ball, int panelWidth,int panelHeight, ForceField forceField) {
        float x = ball.getX();
        float y = ball.getY();
        float radius = ball.getRadius();
        if (x < radius || x > panelWidth - radius) {
            ball.reverseDirectionX(); // Inverti la direzione orizzontale
        }
        if (y < radius) {
            ball.reverseDirectionY(); // Inverti la direzione verticale
        } else if (forceField.isActive() && y >= forceField.getYPosition() - radius) {
            // La pallina collide con il campo di forza
            ball.reverseDirectionY(); // Inverti la direzione verticale
        } else if (y > panelHeight) {
            return true; // La pallina è uscita dal pannello
        }
        return false; // La pallina è ancora all'interno del pannello
    }

    private static Rectangle getCollisionArea(Ball ball, Collidable object) {
        // Calcola la bounding box della pallina
        Rectangle ballRect = new Rectangle(
                (int) (ball.getX() - ball.getRadius()),
                (int) (ball.getY() - ball.getRadius()),
                (int) (ball.getRadius() * 2),
                (int) (ball.getRadius() * 2));

        // Calcola la bounding box dell'oggetto
        Rectangle objectRect = new Rectangle(
                object.getX(),
                object.getY(),
                object.getWidth(),
                object.getHeight());

        // Verifica se c'è una sovrapposizione e restituisci il rettangolo di intersezione
        if (ballRect.intersects(objectRect)) {
            return ballRect.intersection(objectRect);
        }
        return new Rectangle(); // Nessuna collisione
    }
    public static boolean checkPowerUpCollisionWithPaddle(GameEffect effect, Paddle paddle) {
        // Calcola la bounding box del power-up
        Rectangle powerUpRect = new Rectangle((int) effect.getX(), (int) effect.getY(), (int) effect.getSize(), (int) effect.getSize());

        // Calcola la bounding box del paddle
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        // Verifica la collisione
        return powerUpRect.intersects(paddleRect);
    }
}
