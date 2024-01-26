import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class GameController implements ActionListener {
    private  final GameModel model;
    private  GamePanel view;
    private  final Timer timer;
    private final Consumer<String> onGameEnd; // Callable per gestire la fine del gioco
    private long lastMouseActivityTime;
    private boolean isMouseControlActive;
    private static final long MOUSE_INACTIVITY_THRESHOLD = 300; // tempo in millisecondi
    public static final int MAX_PADDLE_SPEED = 8;



    public GameController(GameModel model, Consumer<String> onGameEnd) {
        this.model = model;
        this.onGameEnd = onGameEnd;
        this.lastMouseActivityTime=0;
        this.isMouseControlActive = false;
        timer = new Timer(5, this);
        timer.start();
    }

    public void setView(GamePanel view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (System.currentTimeMillis() - lastMouseActivityTime > MOUSE_INACTIVITY_THRESHOLD) {
            isMouseControlActive = false;
        }
        model.update(); // Aggiorna il modello di gioco
        view.repaint(); // Ridisegna il pannello di gioco

        // Controlla le condizioni di vittoria o sconfitta
        checkGameStatus();
    }

    public void processKey(int keyCode) {
        if (isMouseControlActive && System.currentTimeMillis() - lastMouseActivityTime < MOUSE_INACTIVITY_THRESHOLD) {
            return; // Ignora l'input della tastiera se il mouse è attivo
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            model.movePaddleLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            model.movePaddleRight();
        }
    }

    public void movePaddleTo(int mouseX) {
        lastMouseActivityTime = System.currentTimeMillis();
        isMouseControlActive = true;

        Paddle paddle = model.getPaddle();
        int targetX = mouseX - paddle.getWidth() / 2;

        // Limita la velocità di movimento del paddle
        int deltaX = targetX - paddle.getX();
        if (deltaX > MAX_PADDLE_SPEED) {
            deltaX = MAX_PADDLE_SPEED;
        } else if (deltaX < -MAX_PADDLE_SPEED) {
            deltaX = -MAX_PADDLE_SPEED;
        }

        // Calcola la nuova posizione del paddle
        int newX = Math.max(0, Math.min(paddle.getX() + deltaX, view.getWidth() - paddle.getWidth()));

        // Aggiorna la posizione del paddle nel modello
        model.setPaddlePosition(newX);
    }
    private void checkGameStatus() {
        if (model.isGameOver()) {
            timer.stop();
            onGameEnd.accept("Hai perso!");
        } else if (model.isVictory()) {
            timer.stop();
            onGameEnd.accept("Hai vinto!");
        }
    }

    public GameModel getModel(){
        return this.model;
    }
}
