import com.breakout.objects.Paddle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaddleTest {

    @Test
    public void testMoveLeft() {
        Paddle paddle = new Paddle();
        paddle.setPosition(800, 600); // Set panel width and height for initialization

        int initialX = paddle.getX();
        paddle.moveLeft();

        // Ensure that the paddle's X position is decremented by the speed
        assertEquals(initialX - paddle.getSpeed(), paddle.getX(), "Paddle should move left");
    }

    @Test
    public void testMoveRight() {
        Paddle paddle = new Paddle();
        paddle.setPosition(800, 600); // Set panel width and height for initialization

        int initialX = paddle.getX();
        paddle.moveRight();

        // Ensure that the paddle's X position is incremented by the speed
        assertEquals(initialX + paddle.getSpeed(), paddle.getX(), "Paddle should move right");
    }

    @Test
    public void testCheckMovement() {
        Paddle paddle = new Paddle();
        paddle.setPosition(800, 600); // Set panel width and height for initialization

        // Move the paddle to the left edge
        paddle.setX(0);
        paddle.moveLeft();

        // Ensure that the paddle's X position remains at 0 and does not go below
        assertEquals(0, paddle.getX(), "Paddle should not move below the left edge of the panel");

        // Move the paddle to the right edge
        paddle.setX(800 - paddle.getWidth());
        paddle.moveRight();

        // Ensure that the paddle's X position remains at the right edge and does not go beyond
        assertEquals(800 - paddle.getWidth(), paddle.getX(), "Paddle should not move beyond the right edge of the panel");
    }
}
