import java.awt.*;
import java.awt.image.BufferedImage;

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
        model.setForceFieldAboutToExpire(false);

    }

    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setForceFieldAboutToExpire(true);
    }
}
