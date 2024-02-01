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
    private List<GameEffect> droppingEffects = new ArrayList<>();
    private List<GameEffect> activeEffects = new ArrayList<>();
    private ForceField forceField;


    public GameModel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        initializeGame();
    }

    private void initializeGame() {
        // Inizializzazione degli oggetti di gioco
        forceField = new ForceField(panelHeight);
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
    public void setForceFieldActive(boolean active) {
        forceField.setActive(active);
    }

    public boolean isForceFieldActive() {
        return forceField.isActive();
    }

    public ForceField getForceField() {
        return forceField;
    }
    public void addBall(Ball newBall) {
        balls.add(newBall);
    }
    public void update() {
        // Aggiorna lo stato del gioco
        checkEffectsStatus();
        checkActiveEffects();
        checkBallCollisions();
    }
    private void checkActiveEffects() {
        Iterator<GameEffect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            GameEffect effect = iterator.next();
            effect.refreshEffectState(this);
            if (effect.isEffectExpired()) {
                effect.deactivate(this);
                iterator.remove();
            }
        }
    }

    public void checkEffectsStatus() {
        Iterator<GameEffect> iterator = droppingEffects.iterator();
        while (iterator.hasNext()) {
            GameEffect effect = iterator.next();
            effect.moveDown();
            if (effect.getY() > panelHeight) {
                iterator.remove(); // Rimuovi l'elemento usando l'iterator
            }
            else if (Collision.checkPowerUpCollisionWithPaddle(effect, paddle)) {
                addActiveEffect(effect);
                iterator.remove();
            }
        }
    }
    public void addActiveEffect(GameEffect newEffect) {
        // Rimuovi qualsiasi effetto attivo dello stesso tipo
        activeEffects.removeIf(effect -> {
            if (effect.getEffectType() == newEffect.getEffectType()) {
                effect.deactivate(this);
                return true;
            }
            return false;
        });

        // Aggiungi il nuovo effetto alla lista degli effetti attivi e attivalo
        activeEffects.add(newEffect);
        newEffect.activate(this);
    }
    private void checkBallCollisions() {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            ball.move();
            Collision.checkCollisionWithPaddle(ball, paddle);
            Collision.checkCollisionWithBricks(ball, bricks, this);
            boolean ballLost = Collision.checkWallCollision(ball, panelWidth, panelHeight, getForceField());
            if (ballLost) {
                if (balls.size() == 1) {
                    // Se questa è l'unica palla, rimuovi una vita
                    lives--;
                    if (lives > 0) {
                        resetGamePositions(); // Resetta le posizioni se il gioco continua
                        removeAllEffects();
                    }
                }
                iterator.remove(); // Rimuovi la palla uscita dal pannello
            }
        }
    }
    private void removeAllEffects() {
        // Disattiva e rimuovi tutti gli effetti attivi
        for (GameEffect effect : activeEffects) {
            effect.deactivate(this);
        }
        activeEffects.clear();

        // Rimuovi tutti gli effetti in caduta
        droppingEffects.clear();
    }
    public void incrementLives() {
        lives++;
    }
    public void setPaddlePosition(int newX) {
        paddle.setX(newX);
    }
    public void movePaddleLeft() {
        paddle.moveLeft(panelWidth);
    }
    public void movePaddleRight() {
        paddle.moveRight(panelWidth);
    }

    public void addEffect(GameEffect effect) {
        droppingEffects.add(effect);
    }
    public List<GameEffect> getEffectsOnScreen() {
        return droppingEffects;
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
