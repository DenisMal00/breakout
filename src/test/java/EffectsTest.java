import com.breakout.utils.GameConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import com.breakout.mvc.GameModel;
import com.breakout.effects.*;
public class EffectsTest {
    private GameModel gameModel;

    @BeforeEach
    public void setUp() {
        // Setup game model
        gameModel = new GameModel(800, 600, 1);
    }
    @Test
    public void testDoubleBallEffect() {
        DoubleBallEffect doubleBallEffect=new DoubleBallEffect(GameEffectType.DOUBLE_BALL,50,50);
        //Check the number of initial balls
        int initialNumberOfBalls = 1;

        // Activate the DoubleBallEffec
        doubleBallEffect.activate(gameModel);

        // Check if a new ball is added
        assertEquals(initialNumberOfBalls+1, gameModel.getBalls().size(), "The number of balls should be doubled after activating the Double Ball effect");
    }

    @Test
    public void testForceFieldActivation() {
        ForceFieldEffect forceFieldEffect=new ForceFieldEffect(GameEffectType.FORCE_FIELD,50,50);

        // Activate the ForceFieldEffect
        forceFieldEffect.activate(gameModel);

        // Check if the ForceField is now active
        assertTrue(gameModel.getForceField().isActive(), "The ForceField should be active after activating the ForceFieldEffect");
    }


    @Test
    public void testSpeedBoostActivation() {
        SpeedBoostEffect speedBoostEffect=new SpeedBoostEffect(GameEffectType.SPEED_BOOST,50,50);
        // Initially, the speed should be at the normal speed
        double initialSpeedDx = gameModel.getFirstBall().getDx();
        double initialSpeedDy = gameModel.getFirstBall().getDy();
        // Activate the SpeedBoostEffect
        speedBoostEffect.activate(gameModel);
        // Check if the speed is boosted
        assertTrue((gameModel.getFirstBall().getDx() == initialSpeedDx* GameConstants.BALL_SPEEDBOOST) &&(gameModel.getFirstBall().getDy() == initialSpeedDy* GameConstants.BALL_SPEEDBOOST), "The ball's speed should be boosted after activating the SpeedBoostEffect");
    }

    @Test
    public void testExtraLifeActivation() {
        ExtraLifeEffect extraLifeEffect=new ExtraLifeEffect(GameEffectType.EXTRA_LIFE,50,50);
        // Initially, the number of lives should be at the starting value
        int initialLives = gameModel.getLives();
        // Activate the ExtraLifeEffect
        extraLifeEffect.activate(gameModel);

        // Check if the number of lives has increased
        assertEquals(initialLives + 1, gameModel.getLives(), "The number of lives should increase after activating the ExtraLifeEffect");
    }

    @Test
    public void testReverseCommandsActivation() {
        ReverseControlsEffect reverseControlsEffect=new ReverseControlsEffect(GameEffectType.REVERSE_CONTROLS,50,50);
        // Initially, the controls should be in the normal state
        boolean initialControlState = gameModel.isControlInverted();

        // Activate the ReverseCommandsEffect
        reverseControlsEffect.activate(gameModel);

        // Check if the controls have been reversed
        assertNotEquals(initialControlState, gameModel.isControlInverted(), "The controls should be reversed after activating the ReverseCommandsEffect");
    }

    @Test
    public void testForceFieldDeactivation() {
        ForceFieldEffect forceFieldEffect=new ForceFieldEffect(GameEffectType.FORCE_FIELD,50,50);
        // Activate the ForceFieldEffect
        forceFieldEffect.activate(gameModel);

        // Deactivate the ForceFieldEffect
        forceFieldEffect.deactivate(gameModel);

        // Check if the ForceField is now inactive
        assertFalse(gameModel.getForceField().isActive(), "The ForceField should be inactive after deactivating the ForceFieldEffect");
    }

    @Test
    public void testSpeedBoostDeactivation() {
        SpeedBoostEffect speedBoostEffect=new SpeedBoostEffect(GameEffectType.SPEED_BOOST,50,50);
        // Activate the SpeedBoostEffect
        speedBoostEffect.activate(gameModel);

        // Deactivate the SpeedBoostEffect
        speedBoostEffect.deactivate(gameModel);

        // Check if the speed is back to the normal speed
        assertTrue((gameModel.getFirstBall().getDx() == GameConstants.INITIAL_BALL_DX) &&(gameModel.getFirstBall().getDy() == GameConstants.INITIAL_BALL_DY), "The ball's speed should be boosted after activating the SpeedBoostEffect");
    }

    @Test
    public void testReverseCommandsDeactivation() {

        ReverseControlsEffect reverseControlsEffect=new ReverseControlsEffect(GameEffectType.REVERSE_CONTROLS,50,50);
        // Activate the ReverseCommandsEffect
        reverseControlsEffect.activate(gameModel);

        // Deactivate the ReverseCommandsEffect
        reverseControlsEffect.deactivate(gameModel);

        // Check if the controls are back to the normal state
        assertFalse(gameModel.isControlInverted(), "The controls should be normal after deactivating the ReverseCommandsEffect");
    }
}
