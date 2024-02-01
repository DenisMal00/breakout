import java.util.Random;

public class GameEffectFactory {
    private static final Random random = new Random();
    public static GameEffect createEffect(GameEffectType type, int x, int y) {
        switch (type) {
            case DOUBLE_BALL:
                return new DoubleBallEffect(type,x, y);
            case REVERSE_CONTROLS:
                return new ReverseControlsEffect(type,x, y);
            case EXTRA_LIFE:
                return new ExtraLifeEffect(type,x,y);
            case SPEED_BOOST:
                return new SpeedBoostEffect(type,x,y);
            case FORCE_FIELD:
                return new ForceFieldEffect(type,x,y);
        }
        return null;
    }

    public static GameEffect createRandomPowerUp(int x, int y) {
        if (random.nextInt(10) < 8) { // Circa 33% di probabilità
            GameEffectType[] types = GameEffectType.values();
            GameEffectType randomType = types[random.nextInt(types.length)];
            return createEffect(GameEffectType.REVERSE_CONTROLS, x, y);
        }
        return null;
    }
}

