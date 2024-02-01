import java.util.ArrayList;

public class BrickMap {
    private final ArrayList<Brick> bricks;
    private static final int rows=3;
    private static final int cols=7;
    private static final int brickHeight = 30; // Altezza fissa, ad esempio

    public BrickMap() {
        bricks = new ArrayList<>();
    }

    public void createBricks(int panelWidth) {
        bricks.clear();
        int brickWidth = panelWidth / cols;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * brickWidth;
                int y = row * brickHeight;
                int adjustedBrickWidth = (col == cols - 1) ? panelWidth - x : brickWidth;
                int hitPoints = determineHitPoints(row, rows);

                bricks.add(new Brick(x, y, adjustedBrickWidth, brickHeight, true, hitPoints));
            }
        }
    }

    private int determineHitPoints(int row, int totalRows) {
        double upperThreshold = totalRows * 0.33; // I primi 33% delle righe
        double middleThreshold = totalRows * 0.66; // Fino al 66% delle righe

        if (row < upperThreshold) {
            return 2; // Righe superiori: più resistenti (rossa)
        } else if (row < middleThreshold) {
            return 1; // Righe centrali: resistenza media (arancione)
        } else {
            return 0; // Righe inferiori: meno resistenti (verde)
        }
    }



    public ArrayList<Brick> getBricks() {
            return bricks;
    }

    public boolean areAllBricksGone() {
        for(Brick brick : bricks) {
            if(brick.isVisible()) {
                return false; // Almeno un mattone è ancora visibile
            }
        }
        return true; // Tutti i mattoncini sono distrutti
    }
}
