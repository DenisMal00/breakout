import lombok.Data;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

@Data
public class GameController implements ActionListener {
    private  final GameModel model;
    private  GamePanel view;
    private  final Timer timer;
    private final Consumer<String> onGameEnd;
    private Consumer<Boolean> onGamePause;
    private long lastMouseActivityTime;
    private boolean isMouseControlActive;
    public static final int MAX_PADDLE_SPEED = GameConstants.PADDLE_SPEED_MOUSE;
    private boolean isPaused = false;


    public GameController(GameModel model, Consumer<String> onGameEnd) {
        this.model = model;
        this.onGameEnd = onGameEnd;
        this.lastMouseActivityTime=0;
        this.isMouseControlActive = false;
        timer = new Timer(GameConstants.TIMER_INTERVAL, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            checkGameStatus();
            if (System.currentTimeMillis() - lastMouseActivityTime > GameConstants.MOUSE_INACTIVITY_THRESHOLD) {
                isMouseControlActive = false;
            }
            model.update(); // Aggiorna il modello di gioco
            view.repaint(); // Ridisegna il pannello di gioco
        }
    }

    public void processKey(int keyCode) {
        if(keyCode == KeyEvent.VK_P)
            pauseGame();
        if (!isMouseControlActive && System.currentTimeMillis() - lastMouseActivityTime > GameConstants.MOUSE_INACTIVITY_THRESHOLD) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> model.movePaddleLeft();
                case KeyEvent.VK_RIGHT -> model.movePaddleRight();
                default -> {}
            }
        }
    }

    private void pauseGame() {
        isPaused=true;
        timer.stop();
        if (onGamePause != null)
            onGamePause.accept(isPaused);
    }

    public void movePaddleTo(int mouseX) {
        lastMouseActivityTime = System.currentTimeMillis();
        isMouseControlActive = true;

        Paddle paddle = model.getPaddle();
        int targetX;

        if (model.isControlInverted()) {
            targetX = view.getWidth() - mouseX - paddle.getWidth() / 2;
        } else {
            targetX = mouseX - paddle.getWidth() / 2;
        }

        int deltaX = targetX - paddle.getX();
        if (deltaX > MAX_PADDLE_SPEED) {
            deltaX = MAX_PADDLE_SPEED;
        } else if (deltaX < -MAX_PADDLE_SPEED) {
            deltaX = -MAX_PADDLE_SPEED;
        }

        int newX = Math.max(0, Math.min(paddle.getX() + deltaX, view.getWidth() - paddle.getWidth()));
        model.setPaddlePosition(newX);
    }

    private void checkGameStatus() {
        if (model.isGameOver()) {
            timer.stop();
            onGameEnd.accept("You lose!");
        } else if (model.isVictory()) {
            timer.stop();
            onGameEnd.accept("You win!");
        }
    }

    public ForceField getForceField() {return model.getForceField();}

    public List<Ball> getBalls() {return model.getBalls();}

    public BrickMaps getBricks() {return model.getBricks();}

    public Paddle getPaddle() {return model.getPaddle();}

    public List<GameEffect> getDroppingEffects() {return model.getDroppingEffects();}

    public int getLives() {return model.getLives();}

    public void resumeGame() {
        isPaused=false;
        timer.start();
    }
}
