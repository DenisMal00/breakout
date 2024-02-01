import java.awt.*;

public class ForceFieldEffect extends GameEffect {
    private static final int DURATION = 2000; // Durata dell'effetto in cicli di aggiornamento

    public ForceFieldEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
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
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.green);
        g2d.fillOval((int) super.getX(), (int) super.getY(), (int) super.getSize(), (int) super.getSize());
    }

    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setForceFieldAboutToExpire(true);
    }

    // ... altri metodi se necessari ...
}
