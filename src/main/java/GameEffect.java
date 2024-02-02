import lombok.Data;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Data
public abstract class GameEffect {
    // Getter e Setter
    protected boolean isVisible;
    protected final GameEffectType effectType;
    protected int duration;
    protected float x;
    protected float y;
    protected final float size=25;
    protected final float DOWNWARD_SPEED = 0.7f; // Velocità di movimento verso il basso comune
    protected BufferedImage powerUpImage;

    protected GameEffect(GameEffectType effectType, float x, float y, int duration) {
        this.effectType = effectType;
        this.x = x;
        this.y = y;
        this.duration=duration;
        this.isVisible = false;
    }

    public void setImage(String path){
        try {
            URL imageUrl = getClass().getResource(path);
            if (imageUrl != null) {
                powerUpImage = ImageIO.read(imageUrl);
            } else {
                powerUpImage = getPlaceholderImage(); // Utilizza l'immagine di segnaposto
                System.out.println("Il percorso dell'immagine non è stato trovato o è errato: " + path);
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());

            // Impostazione di un'immagine di default o di un segnaposto
            powerUpImage = getPlaceholderImage();
        }
    }
    public abstract BufferedImage getPlaceholderImage();
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

    public boolean isEffectExpired() {
        decrementDuration();
        return duration <= 0;
    }
    public void render(Graphics2D g2d){
        g2d.drawImage(powerUpImage, (int)x, (int)y,(int)size, (int)size, null);
    }

    protected boolean isAboutToExpire() {
        // Logica generale per determinare se l'effetto sta per scadere
        // Ad esempio, se restano meno di 2 secondi
        return this.duration <= 2 * GameConstants.UPDATES_PER_SECOND;
    }

    public abstract void refreshEffectState(GameModel model);

}