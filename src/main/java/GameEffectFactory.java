import java.util.Random;

public class GameEffectFactory {
    private static final Random random = new Random();
    public static GameEffect createEffect(GameEffectType type, int x, int y) {
        switch (type) {
            case DOUBLE_BALL:
                return new DoubleBallEffect(x, y);
            case REVERSE_CONTROLS:
                return new ReverseControlsEffect(x, y);
        }
        return null;
    }

    public static GameEffect createRandomPowerUp(int x, int y) {
        if (random.nextInt(10) < 5) { // Circa 33% di probabilità
            GameEffectType[] types = GameEffectType.values();
            GameEffectType randomType = types[random.nextInt(types.length)];
            return createEffect(randomType, x, y);
        }
        return null;
    }
}

enum GameEffectType {
    DOUBLE_BALL,
    REVERSE_CONTROLS
    // Altri tipi...
}
