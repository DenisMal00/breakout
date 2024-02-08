public class ForceFieldEffect extends GameEffect {
    public ForceFieldEffect(GameEffectType type,int x, int y) {
        super(type, x, y,GameConstants.EFFECT_DURATION);
    }
    @Override
    public void doActivate(GameModel model) {
        model.setForceFieldActive(true);
    }
    @Override
    public void doDeactivate(GameModel model) {
        model.setForceFieldActive(false);
        model.setForceFieldAboutToExpire(false);
    }
    @Override
    public void refreshEffectState(GameModel model) {
        if (this.isAboutToExpire())
            model.setForceFieldAboutToExpire(true);
    }
}
