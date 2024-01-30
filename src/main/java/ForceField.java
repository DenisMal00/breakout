public class ForceField {
    private boolean isActive;
    private final int yPosition;
    private static final int height=40;

    public ForceField(int panelHeight) {
        this.yPosition = panelHeight - height; // Posizione Y del campo di forza
        this.isActive = false;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getHeight() {
        return height;
    }
}
