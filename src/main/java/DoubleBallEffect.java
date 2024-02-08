public class DoubleBallEffect extends GameEffect {
    public DoubleBallEffect(GameEffectType effectType,float x, float y) {
        super(effectType,x, y,1);
    }

    @Override
    public void doActivate(GameModel model) {
        Ball createdBall;
        Ball existingBall = model.getFirstBall();
        createdBall = new Ball(existingBall.getX(),existingBall.getY(),-existingBall.getDx(),existingBall.getDy(), existingBall.getMaxSpeedChange(),false);
        model.addBall(createdBall);
    }

    @Override
    public void doDeactivate(GameModel model) {
    }
    @Override
    public void refreshEffectState(GameModel model) {}
}
