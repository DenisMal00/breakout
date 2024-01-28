import java.awt.*;

public class DoubleBallEffect extends GameEffect {
    private Ball createdBall;

    public DoubleBallEffect(int x, int y) {
        super(x, y);
        super.duration=600;
    }

    @Override
    public void activate(GameModel model) {
        Ball existingBall = model.getBalls().get(0); // Prendi la palla esistente

        // Crea una nuova palla con proprietà simili
        createdBall = new Ball(existingBall.getX(),existingBall.getY(),-existingBall.getDx(),existingBall.getDy());
        model.addBall(createdBall);    }

    @Override
    public void deactivate(GameModel model) {
        model.removeBall(createdBall);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval((int)super.getX(), (int)super.getY(), (int)super.getSize(), (int)super.getSize());
    }
}
