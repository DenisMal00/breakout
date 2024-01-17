import java.awt.*;
import java.util.ArrayList;

public class BrickMap {
    private ArrayList<Brick> bricks;
    private int rows;
    private int cols;
    private int brickHeight = 30; // Altezza fissa, ad esempio

    public BrickMap(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
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

    public void draw(Graphics2D g) {
        for (Brick brick : bricks) {
            brick.draw(g);
        }
    }

    public ArrayList<Brick> getBricks() {
            return bricks;
    }
}
