import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Data
public class GameModel {
    private Paddle paddle;
    private List<Ball> balls;
    private BrickMaps bricks;
    private int lives=GameConstants.INITIAL_LIVES;
    private final int panelWidth;
    private final int panelHeight;
    private List<GameEffect> droppingEffects = new ArrayList<>();
    private List<GameEffect> activeEffects = new ArrayList<>();
    private ForceField forceField;
    public GameEffectManager powerUpManager;

    public GameModel(int panelWidth, int panelHeight,int level) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        initializeGame(level);
    }

    private void initializeGame(int level) {
        // Inizializzazione degli oggetti di gioco
        forceField = new ForceField(panelHeight);
        paddle = new Paddle();
        bricks = new BrickMaps(level,panelWidth,panelHeight);
        powerUpManager = new GameEffectManager(bricks);
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
    private void addActiveEffect(GameEffect newEffect) {
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
                        removeAllEffects();
                        resetGamePositions(); // Resetta le posizioni se il gioco continua
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
    public void incrementLives() {lives++;}
    public void setPaddlePosition(int newX) {paddle.setX(newX);}
    public void movePaddleLeft() {paddle.moveLeft();}
    public void movePaddleRight() {paddle.moveRight();}
    public void addEffect(GameEffect effect) {droppingEffects.add(effect);}
    public boolean isGameOver() {return lives ==0;}
    public boolean isVictory() {return bricks.areAllBricksGone();}
    public boolean isControlInverted(){return paddle.isControlInverted();}
    public void setControlInverted(boolean controlInverted) {paddle.setControlInverted(controlInverted);}
    public void setPaddleAboutToExpire(boolean aboutToExpire) {paddle.setAboutToExpire(aboutToExpire);}
    public void setForceFieldAboutToExpire(boolean aboutToExpire) {forceField.setAboutToExpire(aboutToExpire);}
    public void setBallAboutToExpire(boolean aboutToExpire) {
        for (Ball ball:balls)
            ball.setAboutToExpire(aboutToExpire);
    }
    public Ball getFirstBall() {return balls.get(0);}
    public GameEffect getPowerUpForBrick(Brick brick) {return powerUpManager.getPowerUpForBrick(brick);}
}
