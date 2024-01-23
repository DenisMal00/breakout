import java.awt.event.KeyEvent;

public class GameModel {
    private Paddle paddle;
    private Ball ball;
    private BrickMap bricks;
    private int lives;
    private static final int initialLives = 3;
    private final int panelWidth;
    private final int panelHeight;
    private static final int MAX_PADDLE_SPEED = 8;// massima velocità del paddle per aggiornamento
    private static final long MOUSE_INACTIVITY_THRESHOLD = 300; // tempo in millisecondi
    private long lastMouseActivityTime;
    private boolean isMouseControlActive;



    public GameModel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.lastMouseActivityTime=0;
        this.isMouseControlActive = false;
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
        if (System.currentTimeMillis() - lastMouseActivityTime > MOUSE_INACTIVITY_THRESHOLD) {
            isMouseControlActive = false;
        }
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
        if (isMouseControlActive && System.currentTimeMillis() - lastMouseActivityTime < MOUSE_INACTIVITY_THRESHOLD) {
            return; // Ignora l'input della tastiera se il mouse è attivo
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.moveRight(panelWidth);
        }
    }

    public void moveBallTo(int x, int y) {
        Ball ball = getBall();
        ball.setX(x);
        ball.setY(y);
    }

    public void movePaddleTo(int mouseX) {
        Paddle paddle = getPaddle();
        int targetX = mouseX - paddle.getWidth() / 2;
        // Limita la velocità di movimento del paddle
        int deltaX = targetX - paddle.getX();
        if (deltaX > MAX_PADDLE_SPEED) {
            deltaX = MAX_PADDLE_SPEED;
        } else if (deltaX < -MAX_PADDLE_SPEED) {
            deltaX = -MAX_PADDLE_SPEED;
        }
        // Aggiorna la posizione del paddle assicurandoti che rimanga all'interno dei confini
        int newX = Math.max(0, Math.min(paddle.getX() + deltaX, panelWidth - paddle.getWidth()));
        paddle.setX(newX);
        isMouseControlActive = true;
        lastMouseActivityTime = System.currentTimeMillis();
    }
    public boolean isGameOver() {
        return lives <= 0;
    }
    public boolean isVictory() {
        return bricks.areAllBricksGone();
    }
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
}
