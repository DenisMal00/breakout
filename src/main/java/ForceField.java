import lombok.Data;

@Data
public class ForceField {
    private boolean isActive=false;
    private final int yPosition;
    private final int height=40;

    private boolean isAboutToExpire=false;
    public ForceField(int panelHeight) {
        this.yPosition = panelHeight - height; // Posizione Y del campo di forza
    }
}
