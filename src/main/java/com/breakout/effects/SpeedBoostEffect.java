package com.breakout.effects;

import com.breakout.mvc.GameModel;
import com.breakout.utils.GameConstants;

public class SpeedBoostEffect extends GameEffect {
    // Constructor to initialize a speed boost effect.
    public SpeedBoostEffect(GameEffectType type, int x, int y) {
        super(type, x, y, GameConstants.EFFECT_DURATION);
    }

    // Increases the speed of all balls in the game model.
    @Override
    public void doActivate(GameModel model) {
        model.getBalls().forEach(ball -> ball.increaseSpeed(GameConstants.BALL_SPEEDBOOST));
    }

    // Decreases the speed of all balls back to normal when the effect expires.
    @Override
    public void doDeactivate(GameModel model) {
        model.getBalls().forEach(ball -> {
            ball.decreaseSpeed(GameConstants.BALL_SPEEDBOOST);
            ball.setAboutToExpire(false);
        });
    }

    // Checks if the speed boost effect is about to expire.
    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setBallAboutToExpire(true);
    }
}