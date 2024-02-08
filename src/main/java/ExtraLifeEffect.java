public class ExtraLifeEffect extends GameEffect {
    public ExtraLifeEffect(GameEffectType type,int x, int y) {
        super(type, x, y,1);
    }

    @Override
    public void doActivate(GameModel model) {
        model.incrementLives();
    }

    @Override
    public void doDeactivate(GameModel model) {
    }

    @Override
    public void refreshEffectState(GameModel model) {}
}
