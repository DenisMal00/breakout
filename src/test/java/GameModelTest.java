import com.breakout.effects.*;
import com.breakout.mvc.GameModel;
import com.breakout.objects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    private GameModel gameModel;
    private Ball ball;
    private Paddle paddle;
    private BrickMaps brickMap;
    private Brick singleBrick;

    @BeforeEach
    public void setUp() {
        gameModel = new GameModel(800, 600, 1); // Assume sample panel sizes
        gameModel.getArrayBricks().clear(); //delete the bricks created with costructor so in the test we can put the bricks needed to dothe tests
        ball = new Ball();
        paddle = new Paddle();
        brickMap=new BrickMaps(1,800,600);
        gameModel.getBalls().clear();

    }

    @Test
    public void testInitializeGame() {
        assertNotNull(gameModel.getBricks(), "BrickMaps should be initialized");
        assertNotNull(gameModel.getPowerUpManager(), "GameEffectManager should be initialized");
        assertEquals(1, gameModel.getBalls().size(), "One ball should be added after resetting game positions");
        assertNotNull(gameModel.getPaddle(), "Paddle should be initialized");
        assertEquals(gameModel.getPaddle().getX(), 800 / 2 - gameModel.getPaddle().getWidth() / 2,
                "Paddle should be centered horizontally");

        // Ensure that the initial position of the first ball is relative to the paddle
        Ball firstBall = gameModel.getBalls().get(0);
        assertEquals(firstBall.getX(), gameModel.getPaddle().getX() + gameModel.getPaddle().getWidth() / 2f,
                "First ball should be initially positioned above the paddle");
        assertEquals(firstBall.getY(), gameModel.getPaddle().getY() - firstBall.getRadius() - 30,
                "First ball should be initially positioned above the paddle");
    }

    @Test
    public void testAddBall() {
        int initialNumberOfBalls = gameModel.getBalls().size();
        gameModel.addBall(new Ball());
        assertEquals(initialNumberOfBalls + 1, gameModel.getBalls().size(), "Number of balls should increase by 1");
    }

    @Test
    public void testBallPaddleCollision() {
        // Assume the initial vertical speed of the ball is downward
        paddle.setY(400);// Assume paddle is at the bottom of the screen
        paddle.setX(550);
        ball.setX(550);
        ball.setY(paddle.getY() - ball.getRadius());// Place the ball right above the paddle
        ball.setDy(1);
        gameModel.addBall(ball);
        gameModel.setPaddle(paddle);
        // Simulate the collision by calling the checkCollision method
        gameModel.update(); // This is the method to check for collisions
        // Verify the effects of the collision
        // Assuming the collision with the paddle should change the vertical direction of the ball
        assertTrue(gameModel.getFirstBall().getDy() < 0, "The ball's vertical velocity should be upward after the collision");
    }



    @Test
    public void testBallBrickCollisionFromBelow() {

        singleBrick = new Brick(200, 200, 50, 30,true,0); // Adjust position and size as needed
        brickMap.getBricks().add(singleBrick); // Assuming a method to add a brick to the map, adjust if necessary
        gameModel.setBricks(brickMap);
        // Position the ball to collide with the brick
        ball.setX(singleBrick.getX() + (float) singleBrick.getWidth() / 2 - (ball.getRadius()*2) / 2);
        ball.setY( singleBrick.getY() + singleBrick.getHeight());

        // Assuming your game model can set ball and bricks for testing
        gameModel.addBall(ball);
        // Call the collision detection method
        gameModel.update(); // Adjust this method call based on your actual game logic


        // After the collision, check the expected outcomes
        // This might include verifying the ball's new position or velocity,
        // and whether the brick has been marked as hit or removed from the brick map.
        assertTrue(gameModel.getFirstBall().getDy() > 0, "The ball's vertical velocity should be downward after the collision"); // Assuming the ball bounces back
    }

    @Test
    public void testBallBrickCollisionFromSide() {
        // Create a brick and clear existing bricks for a clean test environment
        singleBrick = new Brick(200, 200, 50, 30, true, 0); // Assume these are the dimensions and position of the brick
        brickMap.getBricks().add(singleBrick);
        gameModel.setBricks(brickMap);
        // Position the ball so that it will collide with the right side of the brick
        // The ball's left edge should be just to the right of the brick's right edge
        ball.setX(singleBrick.getX() + singleBrick.getWidth() + 1); // Positioning ball just right of the brick
        ball.setY(singleBrick.getY() + (float) singleBrick.getHeight() / 2 - ball.getRadius()); // Aligning ball vertically with the brick's center
        ball.setDx(-1.5F); // Assuming negative dx means the ball is moving left

        // Set up the game model for the test
        gameModel.addBall(ball);

        // Initial conditions before the update (collision detection)
        // Update the game model, which should process the collision
        gameModel.update();
        // Check the expected outcomes after the collision
        assertTrue(gameModel.getFirstBall().getDx() > 0, "The ball's horizontal velocity (dx) should be positive (rightward) after the collision");
    }

    @Test
    public void testLoseLifeWhenBallFallsOffScreen() {
        // Set the initial position of the ball to be below the bottom edge of the screen
        ball.setY(gameModel.getPanelHeight()+10); // Below the bottom edge of the screen
        gameModel.addBall(ball);
        // Record the initial number of lives before the update
        int initialLives = gameModel.getLives();
        // Update the game model, which should detect the ball is out of bounds and reduce the number of lives
        gameModel.update();
        // Check that one life has been subtracted
        assertEquals(initialLives - 1, gameModel.getLives(), "The player should lose one life when the ball falls off the screen");
    }

    @Test
    public void testBallBounceOffTopWall() {
        ball.setX(400); // Center horizontally
        ball.setY(1); // Set to top edge
        gameModel.addBall(ball);
        gameModel.update(); // Your game model should detect collision and update ball's direction
        assertTrue(gameModel.getFirstBall().getDy() > 0, "The ball should be moving downwards after bouncing off the top wall");
    }

    @Test
    public void testBounceOffRightWall() {
        // Position the ball moving towards the right wall
        ball.setX(gameModel.getPanelWidth()); // Position near the right edge of the screen
        ball.setY(300); // Midway down the screen
        ball.setDy(0); // No vertical movement
        gameModel.addBall(ball);
        // Update the game model, which should process the collision with the wall and bounce the ball
        gameModel.update();
        // Check that the ball's horizontal velocity has been reversed
        assertTrue(gameModel.getFirstBall().getDx() < 0, "The ball's horizontal velocity should be negative (leftward) after bouncing off the right wall");
    }

    @Test
    public void testBounceOffLeftWall() {
        // Position the ball moving towards the left wall
        ball.setX(0); // Position at the left edge of the screen
        ball.setY(300); // Midway down the screen
        ball.setDx(-1); // Moving left
        ball.setDy(0); // No vertical movement
        gameModel.addBall(ball);
        // Update the game model, which should process the collision with the wall and bounce the ball
        gameModel.update();
        // Check that the ball's horizontal velocity has been reversed
        assertTrue(gameModel.getFirstBall().getDx() > 0, "The ball's horizontal velocity should be positive (rightward) after bouncing off the left wall");
    }

    @Test
    public void testEffectPaddleCollision() {
        GameEffect droppingEffect;
        paddle.setX(350);// Assuming paddle size and position
        paddle.setY(550);
        droppingEffect = new ForceFieldEffect(GameEffectType.FORCE_FIELD,375, 540); // Position the effect directly above the paddle

        gameModel.setPaddle(paddle);
        gameModel.addEffect(droppingEffect);
        // Initially, the effect should not be activated
        // Update the game model, which should process the collision with the paddle
        gameModel.update();
        assertFalse(gameModel.getDroppingEffects().contains(droppingEffect), "Effect should be removed from dropping effects activation");
        assertTrue(gameModel.getActiveEffects().contains(droppingEffect), "Effect should be added to active effects");
        // After the update, check if the effect has been activated by the collision with the paddle
    }

    @Test
    public void testEffectRemovedAfterExpiration() {
        GameEffect activeEffect;
        // Assume the effect expires after a certain number of updates
        activeEffect = new ForceFieldEffect(GameEffectType.FORCE_FIELD,375, 540); // Position the effect directly above the paddle
        activeEffect.setDuration(5);

        gameModel.getActiveEffects().add(activeEffect);
        activeEffect.activate(gameModel);
        assertTrue(gameModel.getActiveEffects().contains(activeEffect), "Effect should be in active effects before expiration");

        // Simulate the game updates
        for (int i = 0; i < 5; i++) {
            gameModel.update(); // You need to implement this method to update and check effect durations
        }
        // After the updates, the effect should have expired and been removed from active effects
        assertFalse(gameModel.getActiveEffects().contains(activeEffect), "Effect should be removed from active effects after expiration");
    }


    @Test
    public void testGameOverCondition() {
        // Imposta il numero di vite a 0 per simulare la condizione di game over
        gameModel.setLives(0);

        // Verifica che il metodo isGameOver ritorni true
        assertTrue(gameModel.isGameOver(), "The game should be over when lives are 0");
    }

    @Test
    public void testVictoryCondition() {
        gameModel=new GameModel(800,600,1);
        for (Brick brick : gameModel.getArrayBricks()) {
            brick.setHitPoints(-1);
        }

        assertTrue(gameModel.isVictory(), "The game should be won when all bricks are destroyed");
    }
}
