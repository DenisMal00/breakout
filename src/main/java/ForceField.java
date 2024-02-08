import lombok.Data;

@Data
public class ForceField {
    private boolean isActive=false;
    private final int yPosition;
    private final int height=GameConstants.FORCE_FIELD_HEIGHT;
    private boolean isAboutToExpire=false;
    public ForceField(int panelHeight) {
        this.yPosition = panelHeight - height;
    }
}
