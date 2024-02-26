package com.breakout.effects;

import com.breakout.mvc.GameModel;

public class ExtraLifeEffect extends GameEffect {
    // Constructor for com.breakout.gamEffects.ExtraLifeEffect.
    public ExtraLifeEffect(GameEffectType type, int x, int y) {
        super(type, x, y, 1); // Duration is set to 1 because is an instant effect.
    }

    // Increases the player's lives by one when activated.
    @Override
    public void doActivate(GameModel model) {
        model.incrementLives();
    }

    // No deactivation behavior needed for this effect.
    @Override
    public void doDeactivate(GameModel model) {}

    // No state refresh needed for this instant effect.
    @Override
    public void refreshEffectState(GameModel model) {}
}