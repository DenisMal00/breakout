import java.awt.*;

public class ReverseControlsEffect extends GameEffect {
    public ReverseControlsEffect(int x, int y) {
        super(x, y);
        super.duration=600;
    }

    @Override
    public void activate(GameModel model) {
        // Logica per invertire i controlli
    }

    @Override
    public void deactivate(GameModel model) {
        // Logica per ripristinare i controlli normali
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval((int)super.getX(), (int)super.getY(), (int)super.getSize(), (int)super.getSize());
    }
}