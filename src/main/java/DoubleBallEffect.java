import java.awt.*;

public class DoubleBallEffect extends GameEffect {
    private static final int DURATION = 1;

    public DoubleBallEffect(GameEffectType effectType,float x, float y) {
        super(effectType,x, y);
        super.duration=DURATION;
    }

    @Override
    public void activate(GameModel model) {
        Ball createdBall;
        Ball existingBall = model.getBalls().get(0); // Prendi la palla esistente
        // Crea una nuova palla con proprietà simili
        createdBall = new Ball(existingBall.getX(),existingBall.getY(),-existingBall.getDx(),existingBall.getDy());
        model.addBall(createdBall);    }

    @Override
    public void deactivate(GameModel model) {
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval((int)super.getX(), (int)super.getY(), (int)super.getSize(), (int)super.getSize());
    }
    @Override
    public boolean isEffectExpired() {
        return false;
    }

}
