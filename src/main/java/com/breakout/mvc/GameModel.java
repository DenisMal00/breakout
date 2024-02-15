package com.breakout.mvc;

import com.breakout.effects.GameEffect;
import com.breakout.effects.GameEffectManager;
import com.breakout.objects.*;
import com.breakout.utils.Collision;
import com.breakout.utils.GameConstants;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class GameModel {
    private Paddle paddle;
    private List<Ball> balls;
    private BrickMaps bricks;
    private int lives = GameConstants.INITIAL_LIVES; // Player's remaining lives.
    private final int panelWidth;
    private final int panelHeight;
    private List<GameEffect> droppingEffects = new ArrayList<>(); // Effects that are currently falling down the screen.
    private List<GameEffect> activeEffects = new ArrayList<>(); // Effects that are currently active.
    private ForceField forceField;
    public GameEffectManager powerUpManager; // Manages the power-ups available within bricks.

    // Constructor initializes game model, setting the game area dimensions and starting level.
    public GameModel(int panelWidth, int panelHeight, int level) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        initializeGame(level); // Set up game objects based on the level.
    }

    // Sets up initial game objects and their positions.
    private void initializeGame(int level) {
        forceField = new ForceField(panelHeight);
        paddle = new Paddle();
        bricks = new BrickMaps(level, panelWidth, panelHeight);
        powerUpManager = new GameEffectManager(bricks);
        resetGamePositions(); // Position the game objects for the start of the game.
    }

    // Resets the position of game objects to their starting positions.
    private void resetGamePositions() {
        balls = new ArrayList<>();
        addBall(new Ball()); // Add the initial ball.
        paddle.setPosition(panelWidth, panelHeight); // Set paddle position.
        // Set the initial position of the first ball relative to the paddle.
        balls.get(0).setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
    }

    // Activates or deactivates the force field.
    public void setForceFieldActive(boolean active) {
        forceField.setActive(active);
    }

    // Adds a new ball to the game.
    public void addBall(Ball newBall) {
        balls.add(newBall);
    }

    // Updates the game state: moves game objects, checks collisions, and updates effects.
    public void update() {
        checkEffectsStatus(); // Check status of dropping and active effects.
        checkActiveEffects(); // Update and remove expired effects.
        checkBallCollisions(); // Handle ball movements and collisions.
    }

    // Checks and handles active game effects.
    private void checkActiveEffects() {
        Iterator<GameEffect> iterator = activeEffects.iterator();
        while (iterator.hasNext()) {
            GameEffect effect = iterator.next();
            effect.refreshEffectState(this); // Update effect based on current game state.
            if (effect.isEffectExpired()) { // Remove effect if it has expired.
                effect.deactivate(this);
                iterator.remove();
            }
        }
    }

    // Checks status of dropping effects and applies them if they collide with the paddle.
    public void checkEffectsStatus() {
        Iterator<GameEffect> iterator = droppingEffects.iterator();
        while (iterator.hasNext()) {
            GameEffect effect = iterator.next();
            effect.moveDown(); // Move the effect down the screen.
            if (effect.getY() > panelHeight) { // Remove effect if it moves beyond the bottom of the screen.
                iterator.remove();
            } else if (Collision.checkGameEffectCollisionWithPaddle(effect, paddle)) { // Check collision with the paddle.
                addActiveEffect(effect); // Activate the effect and remove from dropping effects.
                iterator.remove();
            }
        }
    }

    // Adds a new effect, replacing any existing effect of the same type.
    private void addActiveEffect(GameEffect newEffect) {
        // Remove any active effect of the same type.
        activeEffects.removeIf(effect -> effect.getEffectType() == newEffect.getEffectType());
        activeEffects.add(newEffect); // Add the new effect to active list.
        newEffect.activate(this); // Activate the new effect.
    }

    // Checks and handles collisions for all balls in play.
    private void checkBallCollisions() {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            ball.move(); // Move the ball based on its current trajectory.
            // Check for collisions with paddle, bricks, and walls.
            Collision.checkCollisionWithPaddle(ball, paddle);
            Collision.checkCollisionWithBricks(ball, bricks, this);
            if (Collision.checkWallCollision(ball, panelWidth, panelHeight, getForceField())) { // Check if the ball has hit a wall and should be removed.
                // Remove a life if this is the only ball left.
                if (balls.size() == 1) {
                    lives--;
                    if (lives > 0) { // Reset game if there are remaining lives.
                        removeAllEffects();
                        resetGamePositions();
                    }
                }
                iterator.remove(); // Remove the ball from play.
            }
        }
    }

    // Disables and removes all effects from the game.
    private void removeAllEffects() {
        activeEffects.forEach(effect -> effect.deactivate(this)); // Deactivate each active effect.
        activeEffects.clear(); // Clear the list of active effects.
        droppingEffects.clear(); // Clear the list of dropping effects.
    }

    // Utility methods for game state management.
    public void incrementLives() { lives++; }
    public void setPaddlePosition(int newX) { paddle.setX(newX); }
    public void movePaddleLeft() { paddle.moveLeft(); }
    public void movePaddleRight() { paddle.moveRight(); }
    public void addEffect(GameEffect effect) { droppingEffects.add(effect); }
    public boolean isGameOver() { return lives == 0; }
    public boolean isVictory() { return bricks.areAllBricksGone(); }
    public boolean isControlInverted() { return paddle.isControlInverted(); }
    public void setControlInverted(boolean controlInverted) { paddle.setControlInverted(controlInverted); }
    public void setPaddleAboutToExpire(boolean aboutToExpire) { paddle.setAboutToExpire(aboutToExpire); }
    public void setForceFieldAboutToExpire(boolean aboutToExpire) { forceField.setAboutToExpire(aboutToExpire); }
    public void setBallAboutToExpire(boolean aboutToExpire) { balls.forEach(ball -> ball.setAboutToExpire(aboutToExpire)); }
    public Ball getFirstBall() { return balls.isEmpty() ? null : balls.get(0); } // Returns the first ball or null if no balls are in play.
    public GameEffect getPowerUpForBrick(Brick brick) { return powerUpManager.getPowerUpForBrick(brick); } // Retrieves a power-up associated with a brick.
}
