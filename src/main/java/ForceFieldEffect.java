import java.awt.*;
import java.awt.image.BufferedImage;

public class ForceFieldEffect extends GameEffect {
    private static final int DURATION = 2000; // Durata dell'effetto in cicli di aggiornamento

    public ForceFieldEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
        setImage("/force_field.png");
    }

    @Override
    public void activate(GameModel model) {
        model.setForceFieldActive(true);

    }
    @Override
    public void deactivate(GameModel model) {
        model.setForceFieldActive(false);
    }

    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setForceFieldAboutToExpire(true);
    }

    @Override
    public BufferedImage getPlaceholderImage() {
        BufferedImage placeholderImage = new BufferedImage((int) super.getSize(), (int) super.getSize(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        // Imposta un colore di default (es. grigio)
        g2d.setColor(Color.green);
        g2d.fillOval(0, 0, (int) super.getSize(), (int) super.getSize());

        // Pulizia e restituzione dell'immagine
        g2d.dispose();
        return placeholderImage;
    }
    // ... altri metodi se necessari ...
}
