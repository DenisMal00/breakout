package com.breakout.mvc;

import com.breakout.effects.GameEffect;
import com.breakout.objects.Ball;
import com.breakout.objects.BrickMaps;
import com.breakout.objects.ForceField;
import com.breakout.objects.Paddle;
import com.breakout.utils.GameConstants;
import lombok.Data;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

@Data
public class GameController implements ActionListener {
    private final GameModel model;
    private GamePanel view;
    private final Timer timer;
    private final Consumer<String> onGameEnd;
    private Consumer<Boolean> onGamePause;
    private long lastMouseActivityTime;
    private boolean isMouseControlActive;
    public static final int MAX_PADDLE_SPEED = GameConstants.PADDLE_SPEED_MOUSE;
    private boolean isPaused = false;

    // Constructor: Initializes the controller with a game model and end game handler
    public GameController(GameModel model, Consumer<String> onGameEnd) {
        this.model = model;
        this.onGameEnd = onGameEnd;
        this.lastMouseActivityTime = 0;
        this.isMouseControlActive = false;
        this.timer = new Timer(GameConstants.TIMER_INTERVAL, this);
        this.timer.start(); // Start the game timer
    }

    // Called every timer tick: Handles game updates if not paused
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            checkGameStatus(); // Check for win/lose conditions
            deactivateMouseControlIfNeeded();
            model.update(); // Update game state
            view.repaint(); // Refresh game view
        }
    }

    // Handles keyboard input for game controls
    public void processKey(int keyCode) {
        if (keyCode == KeyEvent.VK_P) pauseGame(); // Pause game on 'P' key press
        if (!isMouseControlActive && System.currentTimeMillis() - lastMouseActivityTime > GameConstants.MOUSE_INACTIVITY_THRESHOLD) {
            // Process paddle movement keys if mouse control is not active
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> model.movePaddleLeft();
                case KeyEvent.VK_RIGHT -> model.movePaddleRight();
                // No default action needed
            }
        }
    }

    // Pauses the game and triggers the pause event
    private void pauseGame() {
        isPaused = true;
        timer.stop(); // Stop game updates
        if (onGamePause != null) onGamePause.accept(isPaused);
    }

    // Updates paddle position based on mouse movements
    public void movePaddleTo(int mouseX) {
        lastMouseActivityTime = System.currentTimeMillis();
        isMouseControlActive = true;

        Paddle paddle = model.getPaddle();
        int targetX = model.isControlInverted() ? view.getWidth() - mouseX - paddle.getWidth() / 2 : mouseX - paddle.getWidth() / 2;

        int deltaX = targetX - paddle.getX();
        if (deltaX > MAX_PADDLE_SPEED) {
            deltaX = MAX_PADDLE_SPEED;
        } else if (deltaX < -MAX_PADDLE_SPEED) {
            deltaX = -MAX_PADDLE_SPEED;
        }

        int newX = Math.max(0, Math.min(paddle.getX() + deltaX, view.getWidth() - paddle.getWidth()));
        model.setPaddlePosition(newX);
    }


    // Checks the game status and triggers appropriate end game events
    private void checkGameStatus() {
        if (model.isGameOver()) {
            timer.stop(); // Stop the timer if game is over
            onGameEnd.accept("You lose!"); // Notify loss
        } else if (model.isVictory()) {
            timer.stop(); // Stop the timer if game is won
            onGameEnd.accept("You win!"); // Notify win
        }
    }

    // Resume the game from a paused state
    public void resumeGame() {
        isPaused = false;
        timer.start(); // Resume game updates
    }

    // Deactivate mouse control if there is no activity within the threshold
    private void deactivateMouseControlIfNeeded() {
        if (System.currentTimeMillis() - lastMouseActivityTime > GameConstants.MOUSE_INACTIVITY_THRESHOLD) {
            isMouseControlActive = false;
        }
    }

    // Getter methods for game elements
    public ForceField getForceField() { return model.getForceField(); }
    public List<Ball> getBalls() { return model.getBalls(); }
    public BrickMaps getBricks() { return model.getBricks(); }
    public Paddle getPaddle() { return model.getPaddle(); }
    public List<GameEffect> getDroppingEffects() { return model.getDroppingEffects(); }
    public int getLives() { return model.getLives(); }

}
