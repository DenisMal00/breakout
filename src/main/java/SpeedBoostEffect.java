public class SpeedBoostEffect extends GameEffect {
    public SpeedBoostEffect(GameEffectType type,int x, int y) {
        super(type, x, y,GameConstants.EFFECT_DURATION);
    }

    @Override
    public void doActivate(GameModel model) {
        model.getBalls().forEach(ball -> ball.increaseSpeed(GameConstants.BALL_SPEEDBOOST));
    }

    @Override
    public void doDeactivate(GameModel model) {
        model.getBalls().forEach(ball -> {
            ball.decreaseSpeed(GameConstants.BALL_SPEEDBOOST);
            ball.setAboutToExpire(false);
        });
    }
    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setBallAboutToExpire(true);
    }
}
