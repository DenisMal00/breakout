import com.breakout.objects.Ball;
import com.breakout.utils.GameConstants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    float ball_dx=GameConstants.INITIAL_BALL_DX;
    float ball_dy=GameConstants.INITIAL_BALL_DY;
    float max_speed_change= GameConstants.INITIAL_BALL_MAX_SPEED_CHANGE;
    @Test
    public void testMoveBall() {
        Ball ball = new Ball(100, 100, ball_dx, ball_dy,max_speed_change, false);
        ball.move();
        assertEquals(100+ball_dx, ball.getX(), "Ball X position should be updated based on its horizontal speed");
        assertEquals(100+ball_dy, ball.getY(), "Ball Y position should be updated based on its vertical speed");
    }

    @Test
    public void testReverseDirectionX() {
        Ball ball = new Ball(100, 100, ball_dx, ball_dy,max_speed_change, false);
        ball.reverseDirectionX();
        assertEquals(-ball_dx, ball.getDx(), "Ball's horizontal speed should be reversed after calling reverseDirectionX");
    }

    @Test
    public void testReverseDirectionY() {
        Ball ball = new Ball(100, 100, ball_dx, ball_dy, max_speed_change, false);
        ball.reverseDirectionY();
        assertEquals(-ball_dy, ball.getDy(), "Ball's vertical speed should be reversed after calling reverseDirectionY");
    }

}
