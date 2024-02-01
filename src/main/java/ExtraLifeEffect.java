import java.awt.*;

public class ExtraLifeEffect extends GameEffect {
    // Imposta una durata molto breve, poiché l'effetto si attiva immediatamente
    private static final int DURATION = 1;

    public ExtraLifeEffect(GameEffectType type,int x, int y) {
        super(type, x, y,DURATION);
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
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        g2d.fillOval((int) super.getX(), (int) super.getY(), (int) super.getSize(), (int) super.getSize());
    }

    @Override
    public void refreshEffectState(GameModel model) {}
}
