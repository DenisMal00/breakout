public class DoubleBallEffect extends GameEffect {
    private static final int DURATION = 1;


    public DoubleBallEffect(GameEffectType effectType,float x, float y) {
        super(effectType,x, y,DURATION);
    }

    @Override
    public void activate(GameModel model) {
        Ball createdBall;
        Ball existingBall = model.getFirstBall(); // Prendi la palla esistente
        // Crea una nuova palla con proprietà simili
        createdBall = new Ball(existingBall.getX(),existingBall.getY(),-existingBall.getDx(),existingBall.getDy(), existingBall.getMaxSpeedChange(),false);
        model.addBall(createdBall);
    }

    @Override
    public void deactivate(GameModel model) {
    }
    @Override
    public void refreshEffectState(GameModel model) {}
}
