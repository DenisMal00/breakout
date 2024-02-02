import java.util.*;

public class EffectManager {
    private Map<Brick, GameEffect> brickPowerUpMap = new HashMap<>();
    private static final Random random = new Random();

    public EffectManager(BrickMap bricks) {
        generateAndAssociatePowerUps(bricks);
    }

    private void generateAndAssociatePowerUps(BrickMap bricks) {
        for (Brick brick : bricks.getBricks()) {
            if (brick.isDestructable()) {
                GameEffect powerUp = createRandomPowerUp(brick.getX() + brick.getWidth() / 2, brick.getY() + brick.getHeight() / 2);
                brickPowerUpMap.put(brick, powerUp);
            }
        }
    }

    public GameEffect getPowerUpForBrick(Brick brick) {
        return brickPowerUpMap.get(brick);
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
        if (random.nextInt(10) < 8) { // Circa 33% di probabilità
            GameEffectType[] types = GameEffectType.values();
            GameEffectType randomType = types[random.nextInt(types.length)];
            return createEffect(GameEffectType.SPEED_BOOST, x, y);
        }
        return null;
    }

    public List<GameEffect> getAllPowerUps() {
        return new ArrayList<>(brickPowerUpMap.values());
    }
}

