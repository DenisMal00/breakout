package com.breakout.effects;

import com.breakout.objects.Brick;
import com.breakout.objects.BrickMaps;
import com.breakout.utils.GameConstants;

import java.util.*;

public class GameEffectManager {
    private final Map<UUID, GameEffect> brickPowerUpMap = new HashMap<>(); // Maps bricks to their respective power-ups.
    private static final Random random = new Random(); // Random instance for power-up generation.

    // Associates random power-ups with destructible bricks.
    public GameEffectManager(BrickMaps bricks) {
        generateAndAssociatePowerUps(bricks);
    }


    // Generates and associates power-ups to bricks based on the game level.
    private void generateAndAssociatePowerUps(BrickMaps bricks) {
        // Iterate through bricks and assign random power-ups if they are destructible.
        for (Brick brick : bricks.getBricks()) {
            if (brick.isDestructible()) {
                GameEffect powerUp = createRandomPowerUp(brick.getX() + brick.getWidth() / 2, brick.getY() + brick.getHeight() / 2);
                brickPowerUpMap.put(brick.getId(), powerUp);
            }
        }
    }

    // Retrieves the power-up associated with a specific brick.
    public GameEffect getPowerUpForBrick(Brick brick) {
        return brickPowerUpMap.get(brick.getId());

    }
    // Creates a specific com.breakout.gameEffects.GameEffect based on its type.
    private GameEffect createEffect(GameEffectType type, int x, int y) {
        return switch (type) {
            case DOUBLE_BALL -> new DoubleBallEffect(type, x, y);
            case REVERSE_CONTROLS -> new ReverseControlsEffect(type, x, y);
            case EXTRA_LIFE -> new ExtraLifeEffect(type, x, y);
            case SPEED_BOOST -> new SpeedBoostEffect(type, x, y);
            case FORCE_FIELD -> new ForceFieldEffect(type, x, y);
        };
    }

    // Randomly creates a power-up at the specified location.
    private GameEffect createRandomPowerUp(int x, int y) {
        if (random.nextInt(100) < GameConstants.POWER_UP_SPAWN_CHANCE) {
            GameEffectType[] types = GameEffectType.values();
            GameEffectType randomType = types[random.nextInt(types.length)];
            return createEffect(randomType, x, y);
        }
        return null;
    }
}

