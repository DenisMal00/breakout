package com.breakout.effects;

import com.breakout.mvc.GameModel;
import com.breakout.objects.Ball;

public class DoubleBallEffect extends GameEffect {
    // Constructor for com.breakout.gamEffects.DoubleBallEffect.
    public DoubleBallEffect(GameEffectType effectType, float x, float y) {
        super(effectType, x, y, 1); // Duration is set to 1, but typically not used for instant effects.
    }

    // Creates a new ball opposite to the existing one when activated.
    @Override
    public void doActivate(GameModel model) {
        Ball existingBall = model.getFirstBall();
        Ball createdBall = new Ball(existingBall.getX(), existingBall.getY(),
                -existingBall.getDx(), existingBall.getDy(),
                existingBall.getMaxSpeedChange(), false);
        model.addBall(createdBall);
    }

    // No deactivation behavior needed for this effect.
    @Override
    public void doDeactivate(GameModel model) {}

    // No state refresh needed for this instant effect.
    @Override
    public void refreshEffectState(GameModel model) {}
}
