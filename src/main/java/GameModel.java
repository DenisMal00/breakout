import java.awt.event.KeyEvent;

public class GameModel {
    private Paddle paddle;
    private Ball ball;
    private BrickMap bricks;
    private int lives;
    private final int initialLives = 3;
    private int panelWidth;
    private int panelHeight;

    public GameModel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        initializeGame();
    }

    private void initializeGame() {
        // Inizializzazione degli oggetti di gioco
        paddle = new Paddle();
        ball = new Ball();
        bricks = new BrickMap();
        bricks.createBricks(panelWidth,panelHeight);
        lives = initialLives;
        resetGameObjects();
    }

    public void update() {
        // Aggiorna lo stato del gioco
        ball.move();
        checkCollisions();
        // Altre logiche di aggiornamento
    }

    private void checkCollisions() {
        // Gestisci le collisioni qui
        if (Collision.checkWallCollision(ball, panelWidth, panelHeight)) {
            lives--;
            if (lives >= 0) {
                resetGameObjects();
            }
        }

        Collision.checkCollisionWithPaddle(ball, paddle);
        Collision.checkCollisionWithBricks(ball, bricks);
    }

    private void resetGameObjects() {
        // Reimposta paddle e ball
        paddle.setPosition(panelWidth, panelHeight);
        ball.setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
    }

    public void processKey(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.moveRight(panelWidth);
        }
    }

    // Metodi getter, isGameOver, isVictory, ecc...
    public boolean isGameOver() {
        return lives <= 0;
    }

    public boolean isVictory() {
        return bricks.areAllBricksGone();
    }

    // Getter e setter
    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public BrickMap getBricks() {
        return bricks;
    }

    public int getLives() {
        return lives;
    }

    // Altri metodi necessari...
}
