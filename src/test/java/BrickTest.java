import com.breakout.objects.Brick;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrickTest {

    @Test
    public void testVisibleForDestructibleBrickWithPositiveHitPoints() {
        Brick brick = new Brick(0, 0, 50, 30, true, 2);

        assertTrue(brick.isVisible(), "Destructible brick with positive hit points should be visible");
    }

    @Test
    public void testVisibleForDestructibleBrickWithZeroHitPoints() {
        Brick brick = new Brick(0, 0, 50, 30, true, -1);

        assertFalse(brick.isVisible(), "Destructible brick with -1 hit points should not be visible");
    }

    @Test
    public void testVisibleForUndestructibleBrick() {
        Brick brick = new Brick(0, 0, 50, 30, false, -1);

        assertTrue(brick.isVisible(), "Undestructible brick should always be visible");
    }

    @Test
    public void testHitDestructibleBrickWithRemainingHitPoints() {
        Brick brick = new Brick(0, 0, 50, 30, true, 2);

        assertFalse(brick.hit(), "Hit on destructible brick with remaining hit points should not remove the brick");
        assertEquals(1, brick.getHitPoints(), "Remaining hit points should be decreased");
    }

    @Test
    public void testHitDestructibleBrickWithZeroHitPoints() {
        Brick brick = new Brick(0, 0, 50, 30, true, 0);

        assertTrue(brick.hit(), "Hit on destructible brick with zero hit points should remove the brick");
        assertEquals(-1, brick.getHitPoints(), "Hit points should go to -1");
    }

    @Test
    public void testHitDestructibleBrickWithNegativeHitPoints() {
        Brick brick = new Brick(0, 0, 50, 30, true, -1);

        assertFalse(brick.hit(), "Hit on destructible brick with negative hit points does nothing because the brick is invisible");
        assertEquals(-1, brick.getHitPoints(), "Hit points should remain negative");
    }

    @Test
    public void testHitUndestructibleBrick() {
        Brick brick = new Brick(0, 0, 50, 30, false, -1);

        assertFalse(brick.hit(), "Hit on undestructible brick should not remove the brick");
        assertEquals(-1, brick.getHitPoints(), "Hit points should remain the same");
    }
}
