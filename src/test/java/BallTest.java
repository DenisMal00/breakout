import com.breakout.objects.Ball;
import com.breakout.utils.GameConstants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    // Initial values for ball speed and maximum speed change
    float ball_dx = GameConstants.INITIAL_BALL_DX;
    float ball_dy = GameConstants.INITIAL_BALL_DY;
    float max_speed_change = GameConstants.INITIAL_BALL_MAX_SPEED_CHANGE;

    @Test
    public void testMoveBall() {
        // Create a Ball object with initial position (100, 100) and specified speeds
        Ball ball = new Ball(100, 100, ball_dx, ball_dy, max_speed_change, false);

        // Call the move method on the Ball object
        ball.move();

        // Assert that the X and Y positions of the ball have been updated correctly
        assertEquals(100 + ball_dx, ball.getX(), "Ball X position should be updated based on its horizontal speed");
        assertEquals(100 + ball_dy, ball.getY(), "Ball Y position should be updated based on its vertical speed");
    }

    @Test
    public void testReverseDirectionX() {
        // Create a Ball object with initial position (100, 100) and specified speeds
        Ball ball = new Ball(100, 100, ball_dx, ball_dy, max_speed_change, false);

        // Call the reverseDirectionX method on the Ball object
        ball.reverseDirectionX();

        // Assert that the horizontal speed (dx) of the ball has been reversed
        assertEquals(-ball_dx, ball.getDx(), "Ball's horizontal speed should be reversed after calling reverseDirectionX");
    }

    @Test
    public void testReverseDirectionY() {
        // Create a Ball object with initial position (100, 100) and specified speeds
        Ball ball = new Ball(100, 100, ball_dx, ball_dy, max_speed_change, false);

        // Call the reverseDirectionY method on the Ball object
        ball.reverseDirectionY();

        // Assert that the vertical speed (dy) of the ball has been reversed
        assertEquals(-ball_dy, ball.getDy(), "Ball's vertical speed should be reversed after calling reverseDirectionY");
    }
}
