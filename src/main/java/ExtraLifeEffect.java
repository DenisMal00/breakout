import java.awt.*;
import java.awt.image.BufferedImage;

public class ExtraLifeEffect extends GameEffect {
    // Imposta una durata molto breve, poiché l'effetto si attiva immediatamente
    private static final int DURATION = 1;

    public ExtraLifeEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
        setImage("/extra_life.png");
    }

    @Override
    public void activate(GameModel model) {
        // Incrementa il conteggio delle vite nel modello di gioco
        model.incrementLives();
    }

    @Override
    public void deactivate(GameModel model) {
        // Nessuna azione specifica necessaria alla disattivazione, l'effetto è immediato
    }

    @Override
    public void refreshEffectState(GameModel model) {}

    @Override
    public BufferedImage getPlaceholderImage() {
        BufferedImage placeholderImage = new BufferedImage((int) super.getSize(), (int) super.getSize(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        // Imposta un colore di default (es. grigio)
        g2d.setColor(Color.orange);
        g2d.fillOval(0, 0, (int) super.getSize(), (int) super.getSize());

        // Pulizia e restituzione dell'immagine
        g2d.dispose();
        return placeholderImage;
    }
}
