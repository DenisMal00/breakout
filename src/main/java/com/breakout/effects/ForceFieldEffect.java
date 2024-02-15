package com.breakout.effects;

import com.breakout.mvc.GameModel;
import com.breakout.utils.GameConstants;

public class ForceFieldEffect extends GameEffect {
    // Constructor to initialize a force field effect.
    public ForceFieldEffect(GameEffectType type, int x, int y) {
        super(type, x, y, GameConstants.EFFECT_DURATION);
    }

    // Activates the force field in the game model.
    @Override
    public void doActivate(GameModel model) {
        model.setForceFieldActive(true);
    }

    // Deactivates the force field when the effect expires.
    @Override
    public void doDeactivate(GameModel model) {
        model.setForceFieldActive(false);
        model.setForceFieldAboutToExpire(false);
    }

    // Checks if the force field effect is about to expire.
    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setForceFieldAboutToExpire(true);
    }
}
