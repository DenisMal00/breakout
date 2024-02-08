public class ReverseControlsEffect extends GameEffect {
    public ReverseControlsEffect(GameEffectType type,int x, int y) {
        super(type, x, y,GameConstants.EFFECT_DURATION);
    }

    @Override
    protected void doActivate(GameModel model) {
        model.setControlInverted(true);
    }

    @Override
    protected void doDeactivate(GameModel model) {
        model.setControlInverted(false);
        model.setPaddleAboutToExpire(false);
    }

    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setPaddleAboutToExpire(true);
    }
}
