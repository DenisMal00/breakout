import java.awt.*;
import java.awt.image.BufferedImage;

public class DoubleBallEffect extends GameEffect {
    private static final int DURATION = 1;


    public DoubleBallEffect(GameEffectType effectType,float x, float y) {
        super(effectType,x, y,DURATION);
        setImage("/double_ball.png");
    }

    @Override
    public void activate(GameModel model) {
        Ball createdBall;
        Ball existingBall = model.getFirstBall(); // Prendi la palla esistente
        // Crea una nuova palla con proprietà simili
        createdBall = new Ball(existingBall.getX(),existingBall.getY(),-existingBall.getDx(),existingBall.getDy(), existingBall.getMaxSpeedChange(),false);
        model.addBall(createdBall);    }

    @Override
    public void deactivate(GameModel model) {
    }
    @Override
    public void refreshEffectState(GameModel model) {}

    @Override
    public BufferedImage getPlaceholderImage() {
        BufferedImage placeholderImage = new BufferedImage((int) super.getSize(), (int) super.getSize(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();

        // Imposta un colore di default (es. grigio)
        g2d.setColor(Color.yellow);
        g2d.fillOval(0, 0, (int) super.getSize(), (int) super.getSize());

        // Pulizia e restituzione dell'immagine
        g2d.dispose();
        return placeholderImage;
    }
}
