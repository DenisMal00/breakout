package com.breakout.effects;

import com.breakout.mvc.GameModel;
import com.breakout.objects.Collidable;
import com.breakout.utils.GameConstants;
import lombok.Data;

@Data
public abstract class GameEffect implements Collidable {
    protected boolean isVisible; // Indicates whether the effect is currently visible on the screen.
    protected final GameEffectType effectType; // The type of the game effect.
    protected int duration; // How long the effect lasts.
    protected float x, y; // The x and y coordinates of the effect.
    protected final float size = GameConstants.EFFECT_SIZE; // The size of the effect.
    protected final float DOWNWARD_SPEED = GameConstants.EFFECT_DOWNWARD_SPEED; // The downward speed of the effect.

    // Constructor for creating a game effect.
    protected GameEffect(GameEffectType effectType, float x, float y, int duration) {
        this.effectType = effectType;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.isVisible = false; // Initially, the effect is not visible.
    }

    // Activates the effect in the game model.
    public final void activate(GameModel model) {
        doActivate(model);
    }

    // Deactivates the effect in the game model.
    public final void deactivate(GameModel model) {
        doDeactivate(model);
    }

    // Reduces the duration of the effect each update.
    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }

    // Moves the effect downward at a constant speed.
    public void moveDown() {
        y += DOWNWARD_SPEED;
    }

    // Checks if the effect has expired based on its duration.
    public boolean isEffectExpired() {
        decrementDuration();
        return duration <= 0;
    }

    // Checks if the effect is about to expire (within two updates).
    protected boolean isAboutToExpire() {
        return this.duration <= 2 * GameConstants.UPDATES_PER_SECOND;
    }

    // Abstract methods to be implemented by subclasses for specific effect behaviors.
    public abstract void refreshEffectState(GameModel model);
    protected abstract void doActivate(GameModel model);
    protected abstract void doDeactivate(GameModel model);

    // Implementation of com.breakout.objects.Collidable interface methods for position and size.
    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public int getWidth() {
        return (int) size;
    }

    @Override
    public int getHeight() {
        return (int) size;
    }
}