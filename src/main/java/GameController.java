import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class GameController implements ActionListener {
    private final GameModel model;
    private final GamePanel view;
    private final Timer timer;
    private final Consumer<String> onGameEnd; // Callable per gestire la fine del gioco

    public GameController(GameModel model, GamePanel view, Consumer<String> onGameEnd) {
        this.model = model;
        this.view = view;
        this.onGameEnd = onGameEnd;

        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.update(); // Aggiorna il modello di gioco
        view.repaint(); // Ridisegna il pannello di gioco

        // Controlla le condizioni di vittoria o sconfitta
        checkGameStatus();
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
}
