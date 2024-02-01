import java.awt.*;

public class SpeedBoostEffect extends GameEffect {
    private static final int DURATION = 2000; // 10 secondi in termini di cicli di aggiornamento
    private static final float speedBoost=2;
    public SpeedBoostEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
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
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.gray);
        g2d.fillOval((int) super.getX(), (int) super.getY(), (int) super.getSize(), (int) super.getSize());
    }

    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setBallAboutToExpire(true);
    }
}
