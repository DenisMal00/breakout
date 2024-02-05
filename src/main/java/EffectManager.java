import java.util.*;

public class EffectManager {
    private final Map<UUID, GameEffect> brickPowerUpMap = new HashMap<>();
    private static final Random random = new Random();

    public EffectManager(BrickMap bricks) {
        generateAndAssociatePowerUps(bricks);
    }

    private void generateAndAssociatePowerUps(BrickMap bricks) {
        for (Brick brick : bricks.getBricks()) {
            if (brick.isDestructible()) {
                GameEffect powerUp = createRandomPowerUp(brick.getX() + brick.getWidth() / 2, brick.getY() + brick.getHeight() / 2);
                brickPowerUpMap.put(brick.getId(), powerUp);
            }
        }
    }

    public GameEffect getPowerUpForBrick(Brick brick) {
        return brickPowerUpMap.get(brick.getId());

    }
    private GameEffect createEffect(GameEffectType type, int x, int y) {
        return switch (type) {
            case DOUBLE_BALL -> new DoubleBallEffect(type, x, y);
            case REVERSE_CONTROLS -> new ReverseControlsEffect(type, x, y);
            case EXTRA_LIFE -> new ExtraLifeEffect(type, x, y);
            case SPEED_BOOST -> new SpeedBoostEffect(type, x, y);
            case FORCE_FIELD -> new ForceFieldEffect(type, x, y);
        };
    }

    private GameEffect createRandomPowerUp(int x, int y) {
        if (random.nextInt(10) < 8) {
            GameEffectType[] types = GameEffectType.values();
            GameEffectType randomType = types[random.nextInt(types.length)];
            return createEffect(randomType, x, y);
        }
        return null;
    }
}

