import java.awt.*;

public abstract class GameEffect {
    protected float x, y;
    protected int duration; // Durata in termini di tick di aggiornamento
    protected static final float DOWNWARD_SPEED = 0.5f; // Velocità di movimento verso il basso comune
    protected boolean isCollected=false;
    private final static float size=20;


    public GameEffect(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveDown() {
        y += DOWNWARD_SPEED;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isCollected() {
        return isCollected;
    }
    public void setCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }
    // Metodi astratti per attivare e disattivare il power-up
    public abstract void activate(GameModel model);
    public abstract void deactivate(GameModel model);
    public abstract void render(Graphics2D g2d);

    public float getSize() {
        return size;
    }
}

/*public class PowerUp {
    public enum Type { DOUBLE_BALL, REVERSE_CONTROLS }
    private final Type type;
    private static final float DOWNWARD_SPEED = 0.7f ; // Velocità di movimento verso il basso
    private float x, y; // Posizione del power-up
    private int duration; // Durata in termini di tick di aggiornamento
    private boolean isCollected=false;



    public PowerUp(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.duration = determineDuration(type);
    }

    private int determineDuration(Type type) {
        switch (type) {
            case DOUBLE_BALL:
                return 0; // Per esempio, 0 per effetti immediati
            case REVERSE_CONTROLS:
                return 600; // Durata per REVERSE_CONTROLS
            // Aggiungi casi per altri tipi di power-up
            default:
                return 0;
        }
    }

    // Metodi getter e setter
    public Type getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    // Metodo per decrementare la durata
    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }
    public boolean isActive() {
        return duration > 0;
    }
    public static Type getRandomPowerUpType() {
        Type[] types = Type.values(); // Ottiene tutti i tipi di PowerUp
        int randomIndex = (int) (Math.random() * types.length);
        return types[randomIndex];
    }
    public void moveDown() {
        this.y += DOWNWARD_SPEED;
    }

    public float getSize(){
        return this.size;
    }
    public boolean isCollected() {
        return isCollected;
    }
    public void setCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }
}
*/