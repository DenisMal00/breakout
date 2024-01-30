import java.awt.*;

public class ReverseControlsEffect extends GameEffect {
    private static final int DURATION = 2000; // 10 secondi di durata

    public ReverseControlsEffect(GameEffectType type,int x, int y) {
        super(type, x, y);
        super.duration=DURATION;
    }

    @Override
    public void activate(GameModel model) {
        // Attiva l'effetto di inversione dei comandi
        model.getPaddle().setControlInverted(true);
    }

    @Override
    public void deactivate(GameModel model) {
        // Disattiva l'effetto di inversione dei comandi
        model.getPaddle().setControlInverted(false);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval((int) super.getX(), (int) super.getY(), (int) super.getSize(), (int) super.getSize());
    }
}
