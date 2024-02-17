import static org.junit.jupiter.api.Assertions.*;
import com.breakout.mvc.GameController;
import com.breakout.mvc.GameModel;
import com.breakout.utils.GameConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

class GameControllerTest {
    private GameController gameController;
    @BeforeEach
    public void setUp() {
        GameModel gameModel = new GameModel(800, 600, 1);

        Consumer<String> onGameEndMock = message -> assertTrue(message.equals("You win!") || message.equals("You lose!"), "Invalid game end message");


        gameController = new GameController(gameModel, onGameEndMock);
    }
    @Test
    public void testGameControllerInitialization() {
        // Initialize a minimal GameModel for testing
        assertNotNull(gameController.getModel(), "GameController should have a valid gameModeldel");
        assertNotNull(gameController.getTimer(), "GameController should have a valid timer");
        assertFalse(gameController.isPaused(), "GameController should not be paused initially");
    }

    @Test
    public void testGameControllerProcessPauseKey() {
        // Simulate key press event for 'P' to pause the game
        gameController.processKey(KeyEvent.VK_P);
        assertTrue(gameController.isPaused(), "GameController should be paused after processing 'P' key");
    }

    @Test
    public void testGameControllerProcessLeftKey() {
        // Simulate key press event for left arrow while not in mouse control
        int initialX = gameController.getPaddle().getX();
        gameController.processKey(KeyEvent.VK_LEFT);
        int newX = gameController.getPaddle().getX();
        assertEquals(initialX - GameConstants.PADDLE_SPEED_KEYBOARD, newX,"paddle should move left");
    }

    @Test
    public void testGameControllerProcessRightKey() {
        // Simulate key press event for right arrow while not in mouse control
        int initialX = gameController.getPaddle().getX();
        gameController.processKey(KeyEvent.VK_RIGHT);
        int newX = gameController.getPaddle().getX();
        assertEquals(initialX + GameConstants.PADDLE_SPEED_KEYBOARD, newX,"paddle should move right");
    }
}
