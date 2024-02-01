import java.awt.*;

public abstract class GameEffect {
    private final GameEffectType effectType;
    protected int duration;
    private float x;
    private float y;
    private static final float size=20;
    private static final float DOWNWARD_SPEED = 0.7f; // Velocità di movimento verso il basso comune
    protected GameEffect(GameEffectType effectType, float x, float y) {
        this.effectType = effectType;
        this.x = x;
        this.y = y;
    }

    // Metodo astratto per attivare l'effetto
    public abstract void activate(GameModel model);

    // Metodo astratto per disattivare l'effetto
    public abstract void deactivate(GameModel model);

    // Metodo per decrementare la durata dell'effetto
    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }

    // Metodo per spostare l'effetto verso il basso (se in movimento sullo schermo)
    public void moveDown() {
        y+=DOWNWARD_SPEED; // Velocità di movimento verso il basso comune
    }

    // Getter e Setter
    public GameEffectType getEffectType() {
        return effectType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isEffectExpired() {
        decrementDuration();
        return duration <= 0;
    }
    public abstract void render(Graphics2D g2d);

    public float getSize() {
        return size;
    }
    protected boolean isAboutToExpire() {
        // Logica generale per determinare se l'effetto sta per scadere
        // Ad esempio, se restano meno di 2 secondi
        return this.duration <= 2 * GameConstants.UPDATES_PER_SECOND;
    }

    public abstract void refreshEffectState(GameModel model);

}