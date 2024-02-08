import lombok.Data;

@Data
public abstract class GameEffect {
    protected boolean isVisible;
    protected final GameEffectType effectType;
    protected int duration;
    protected float x;
    protected float y;
    protected final float size=GameConstants.EFFECT_SIZE;
    protected final float DOWNWARD_SPEED = GameConstants.EFFECT_DOWNWARD_SPEED;

    protected GameEffect(GameEffectType effectType, float x, float y, int duration) {
        this.effectType = effectType;
        this.x = x;
        this.y = y;
        this.duration=duration;
        this.isVisible = false;
    }

    public final void activate(GameModel model) {
        doActivate(model);
    }

    public final void deactivate(GameModel model) {
        doDeactivate(model);
    }

    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }

    public void moveDown() {
        y+=DOWNWARD_SPEED;
    }

    public boolean isEffectExpired() {
        decrementDuration();
        return duration <= 0;
    }
    protected boolean isAboutToExpire() {
        return this.duration <= 2 * GameConstants.UPDATES_PER_SECOND;
    }

    public abstract void refreshEffectState(GameModel model);
    protected abstract void doActivate(GameModel model);
    protected abstract void doDeactivate(GameModel model);
}