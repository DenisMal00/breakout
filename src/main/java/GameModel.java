import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel {
    private Paddle paddle;
    private List<Ball> balls;
    private BrickMap bricks;
    private int lives;
    private static final int initialLives = 3;
    private final int panelWidth;
    private final int panelHeight;
    private List<GameEffect> effects = new ArrayList<>();

    public GameModel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        initializeGame();
    }

    private void initializeGame() {
        // Inizializzazione degli oggetti di gioco
        paddle = new Paddle();
        bricks = new BrickMap();
        bricks.createBricks(panelWidth);
        lives = initialLives;
        resetGamePositions();
    }

    private void resetGamePositions(){
        balls = new ArrayList<>();
        addBall(new Ball());
        paddle.setPosition(panelWidth, panelHeight);
        balls.get(0).setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
    }
    public void addBall(Ball newBall) {
        balls.add(newBall);
    }
    public void removeBall(Ball ball) {
        balls.remove(ball);
    }
    public void update() {
        // Aggiorna lo stato del gioco
        updateEffects();
        checkEffectsCollisions();
        for (GameEffect powerUp : effects) {
            powerUp.moveDown();
        }
        checkBallCollisions();
    }
    public void checkEffectsCollisions() {
        for (Iterator<GameEffect> iterator = effects.iterator(); iterator.hasNext();) {
            GameEffect effect = iterator.next();
            if (Collision.checkPowerUpCollisionWithPaddle(effect, paddle)) {
                effect.setCollected(true); // Segna il power-up come raccolto
                activateEffect(effect); // Attiva il power-up
            }
        }
        effects.removeIf(GameEffect::isCollected); // Rimuovi i power-up raccolti
    }

    private void activateEffect(GameEffect effect) {
        effect.activate(this);
    }

    public void updateEffects() {
        Iterator<GameEffect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            GameEffect effect = iterator.next();
            effect.moveDown();
            if (!effect.isCollected() && effect.getY() > panelHeight) {
                iterator.remove(); // Rimuovi l'elemento usando l'iterator
            }
        }
    }

    private void checkBallCollisions() {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            ball.move();
            boolean ballLost = Collision.checkWallCollision(ball, panelWidth, panelHeight);
            if (ballLost) {
                if (balls.size() == 1) {
                    // Se questa è l'unica palla, rimuovi una vita
                    lives--;
                    if (lives > 0) {
                        resetGamePositions(); // Resetta le posizioni se il gioco continua
                    }
                }
                iterator.remove(); // Rimuovi la palla uscita dal pannello
                continue; // Prosegui con la prossima palla
            }
            Collision.checkCollisionWithPaddle(ball, paddle);
            Collision.checkCollisionWithBricks(ball, bricks, this);
        }
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

    public void addEffect(GameEffect effect) {
        effects.add(effect);
    }
    public List<GameEffect> getEffectsOnScreen() {
        return effects;
    }
    public boolean isGameOver() {
        return lives ==0;
    }
    public boolean isVictory() {
        return bricks.areAllBricksGone();
    }
    public Paddle getPaddle() {
        return paddle;
    }
    public List<Ball> getBalls() {
        return balls;
    }
    public BrickMap getBricks() {
        return bricks;
    }
    public int getLives() {
        return lives;
    }

}
