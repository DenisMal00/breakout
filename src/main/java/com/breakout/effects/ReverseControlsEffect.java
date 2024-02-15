package com.breakout.effects;

import com.breakout.mvc.GameModel;
import com.breakout.utils.GameConstants;

public class ReverseControlsEffect extends GameEffect {
    // Constructor to initialize a reverse controls effect.
    public ReverseControlsEffect(GameEffectType type, int x, int y) {
        super(type, x, y, GameConstants.EFFECT_DURATION);
    }

    // Reverses the control scheme in the game model.
    @Override
    protected void doActivate(GameModel model) {
        model.setControlInverted(true);
    }

    // Restores normal controls when the effect expires.
    @Override
    protected void doDeactivate(GameModel model) {
        model.setControlInverted(false);
        model.setPaddleAboutToExpire(false);
    }

    // Checks if the reverse control effect is about to expire.
    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setPaddleAboutToExpire(true);
    }
}