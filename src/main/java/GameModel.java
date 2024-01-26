import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel {
    private Paddle paddle;
    private Ball ball;
    private BrickMap bricks;
    private int lives;
    private static final int initialLives = 3;
    private final int panelWidth;
    private final int panelHeight;
    private List<PowerUp> powerUpsOnScreen = new ArrayList<>();

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
        bricks.createBricks(panelWidth);
        lives = initialLives;
        PowerUp testPowerUp = new PowerUp(PowerUp.Type.DOUBLE_BALL, 100, 100); // Example position
        powerUpsOnScreen.add(testPowerUp);
        resetGameObjects();
    }

    private void resetGameObjects() {
        // Reimposta paddle e ball
        paddle.setPosition(panelWidth, panelHeight);
        ball.setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
    }

    public void update() {
        // Aggiorna lo stato del gioco
        updatePowerUps();
        checkPowerUpCollisions();
        for (PowerUp powerUp : powerUpsOnScreen) {
            powerUp.moveDown();
        }
        ball.move();
        checkCollisions();

    }
    public void checkPowerUpCollisions() {
        for (Iterator<PowerUp> iterator = powerUpsOnScreen.iterator(); iterator.hasNext();) {
            PowerUp powerUp = iterator.next();
            if (Collision.checkPowerUpCollisionWithPaddle(powerUp, paddle)) {
                activatePowerUp(powerUp); // Attiva il power-up
                iterator.remove(); // Rimuovi il power-up dopo l'attivazione
            }
        }
    }

    private void activatePowerUp(PowerUp powerUp) {
        // Qui attivi l'effetto del power-up
        // Implementa la logica specifica per il tipo di power-up
    }
    public void updatePowerUps() {
        for (Iterator<PowerUp> iterator = powerUpsOnScreen.iterator(); iterator.hasNext();) {
            PowerUp powerUp = iterator.next();
            powerUp.moveDown();

            if (powerUp.getY() > panelHeight) {
                iterator.remove(); // Rimuove il power-up se è fuori dallo schermo
            }
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
        Collision.checkCollisionWithBricks(ball, bricks,this);
    }
    public void setPaddlePosition(int newX) {
        paddle.setX(newX);
    }
    public void movePaddleLeft() {
        paddle.moveLeft();
    }
    public void movePaddleRight() {
        paddle.moveRight(panelWidth);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUpsOnScreen.add(powerUp);
    }
    public List<PowerUp> getPowerUpsOnScreen() {
        return powerUpsOnScreen;
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
