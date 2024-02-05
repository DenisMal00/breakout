import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedBoostEffect extends GameEffect {
    private static final int DURATION = 1000; // 10 secondi in termini di cicli di aggiornamento
    private static final float speedBoost=2;
    public SpeedBoostEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
        setImage("/speed_boost.png", Color.GRAY);
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
}
