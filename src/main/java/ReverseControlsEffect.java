import java.awt.*;
import java.awt.image.BufferedImage;

public class ReverseControlsEffect extends GameEffect {
    private static final int DURATION = 1000; // 10 secondi di durata

    public ReverseControlsEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
        setImage("/reverse_command.png");
    }

    @Override
    public void activate(GameModel model) {
        // Attiva l'effetto di inversione dei comandi
        model.setControlInverted(true);
    }

    @Override
    public void deactivate(GameModel model) {
        // Disattiva l'effetto di inversione dei comandi
        model.setControlInverted(false);
    }

    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setPaddleAboutToExpire(true);
    }

    @Override
    public BufferedImage getPlaceholderImage() {
        BufferedImage placeholderImage = new BufferedImage((int) super.getSize(), (int) super.getSize(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        // Imposta un colore di default (es. grigio)
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(0, 0, (int) super.getSize(), (int) super.getSize());

        // Pulizia e restituzione dell'immagine
        g2d.dispose();
        return placeholderImage;
    }

}
