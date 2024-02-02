import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedBoostEffect extends GameEffect {
    private static final int DURATION = 1000; // 10 secondi in termini di cicli di aggiornamento
    private static final float speedBoost=2;
    public SpeedBoostEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
        setImage("/speed_boost.png");
    }

    @Override
    public void activate(GameModel model) {
        // Raddoppia la velocità di tutte le palline in gioco
        for (Ball ball : model.getBalls()) {
            ball.increaseSpeed(speedBoost);
        }
    }

    @Override
    public void deactivate(GameModel model) {
        // Ripristina la velocità normale delle palline
        for (Ball ball : model.getBalls()) {
            ball.decreaseSpeed(speedBoost);
            model.setBallAboutToExpire(false);
        }
    }
    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setBallAboutToExpire(true);
    }
   @Override
    public BufferedImage getPlaceholderImage() {
        BufferedImage placeholderImage = new BufferedImage((int) super.getSize(), (int) super.getSize(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        // Imposta un colore di default (es. grigio)
        g2d.setColor(Color.gray);
        g2d.fillOval(0, 0, (int) super.getSize(), (int) super.getSize());

        // Pulizia e restituzione dell'immagine
        g2d.dispose();
        return placeholderImage;
    }
}
