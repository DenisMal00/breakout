import java.awt.*;

public class ForceFieldEffect extends GameEffect {
    private static final int DURATION = 2000; // Durata dell'effetto in cicli di aggiornamento

    public ForceFieldEffect(GameEffectType type,int x, int y) {
        super(type, x, y);
        super.duration=DURATION;
    }

    @Override
    public void activate(GameModel model) {
        model.setForceFieldActive(true);
    }

    @Override
    public void deactivate(GameModel model) {
        model.setForceFieldActive(false);
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(Color.gray);
        g2d.fillOval((int) super.getX(), (int) super.getY(), (int) super.getSize(), (int) super.getSize());
    }

    // ... altri metodi se necessari ...
}