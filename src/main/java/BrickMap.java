import java.awt.*;
import java.util.ArrayList;

public class BrickMap {
    private ArrayList<Brick> bricks;
    private int rows=3;
    private int cols=7;
    private int brickHeight = 30; // Altezza fissa, ad esempio

    public BrickMap() {
        bricks = new ArrayList<>();
    }

    public void createBricks(int panelWidth, int panelHeight) {
        bricks.clear();
        int brickWidth = panelWidth / cols;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * brickWidth;
                int y = row * brickHeight;
                // Per l'ultimo mattone della riga, aggiusta la larghezza per riempire lo spazio rimanente
                int adjustedBrickWidth = brickWidth;
                if (col == cols - 1) {
                    adjustedBrickWidth = panelWidth - x;
                }
                bricks.add(new Brick(x, y, adjustedBrickWidth, brickHeight));
            }
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
