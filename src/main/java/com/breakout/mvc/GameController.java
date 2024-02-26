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
    /*
     Indicates whether the paddle is currently being controlled by mouse movements.
     This is used to prevent concurrent control conflicts between mouse and keyboard inputs.
     If the mouse is active, it takes precedence for controlling the paddle movement.
     The keyboard controls will only take effect if there has been a period of mouse inactivity,
     allowing the user to switch between control modes seamlessly.
    */
    private long lastMouseActivityTime;
    private boolean isMouseControlActive;
    private final GameModel model; //Main game logic and state holder
    private GamePanel view; //Graphical interface for game rendering
    private final Timer timer; //Game update scheduling timer
    private final Consumer<String> onGameEnd; //Callback to MemuManager for game end scenarios
    private Consumer<Boolean> onGamePause; //Callback to MemuManager for game pause state changes
    public static final int MAX_PADDLE_SPEED = GameConstants.PADDLE_SPEED_MOUSE; //Maximum speed the paddle can move, set based on mouse control
    private boolean isPaused = false; //Flag for game's paused status

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
            checkAndDeactivateMouseControl();
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
        // Update the last mouse activity time to track control mode
        lastMouseActivityTime = System.currentTimeMillis();
        isMouseControlActive = true;

        // Retrieve the current paddle from the game model
        Paddle paddle = model.getPaddle();

        // Calculate the target x-coordinate based on mouse position and paddle width
        int targetX = model.isControlInverted() ?
                view.getWidth() - mouseX - paddle.getWidth() / 2 :
                mouseX - paddle.getWidth() / 2;

        // Calculate the change in x-coordinate to limit paddle speed
        int deltaX = targetX - paddle.getX();
        if (deltaX > MAX_PADDLE_SPEED) {
            deltaX = MAX_PADDLE_SPEED;
        } else if (deltaX < -MAX_PADDLE_SPEED) {
            deltaX = -MAX_PADDLE_SPEED;
        }

        // Calculate the new x-coordinate for the paddle within panel bounds
        int newX = Math.max(0, Math.min(paddle.getX() + deltaX, view.getWidth() - paddle.getWidth()));

        // Set the updated paddle position in the game model
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
    private void checkAndDeactivateMouseControl() {
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
