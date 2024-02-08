import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data
public class BrickMaps {
    private final ArrayList<Brick> bricks;
    private final int brickHeight = GameConstants.BRICK_HEIGHT;
    private int panelWidth;
    private int panelHeight;

    Random random = new Random();
    public BrickMaps(int level, int panelWidth,int panelHeight) {
        this.panelHeight=panelHeight;
        this.panelWidth=panelWidth;
        bricks = new ArrayList<>();
        setupLevel(level);
    }

    public void setupLevel(int level) {
        switch (level) {
            case 1:
                setupLevel1(3,7);
                break;
            case 2:
                setupLevel2(7,11);
                break;
            case 3:
                setupLevel3();
                break;
            case 4:
                setupLevel4(50,20);
                break;
            default:
                throw new IllegalArgumentException("Livello non supportato: " + level);
        }
    }

    public void setupLevel4(int brickWidth, int brickHeight) {
        bricks.clear();
        int cols = panelWidth / brickWidth;
        int rows = panelHeight / brickHeight;
        int horizontalMargin = cols / 4;
        int verticalMargin = rows / 6;
        int horseshoeWidth = cols - (2 * horizontalMargin);
        int horseshoeHeight = (rows / 2) - (2 * verticalMargin)+3;
        for (int row = verticalMargin; row < verticalMargin + horseshoeHeight; row++) {
            bricks.add(new Brick(horizontalMargin * brickWidth, row * brickHeight, brickWidth, brickHeight, false, -1));
            bricks.add(new Brick((cols - horizontalMargin - 1) * brickWidth, row * brickHeight, brickWidth, brickHeight, false, -1));
        }
        for (int col = horizontalMargin; col < horizontalMargin + horseshoeWidth; col++) {
            bricks.add(new Brick(col * brickWidth, (verticalMargin + horseshoeHeight - 1) * brickHeight, brickWidth, brickHeight, false, -1));
        }
        for (int row = verticalMargin + 1; row < verticalMargin + horseshoeHeight - 1; row++) {
            for (int col = horizontalMargin + 1; col < cols - horizontalMargin - 1; col++) {
                bricks.add(new Brick(col * brickWidth, row * brickHeight, brickWidth, brickHeight, true, determineHitPoints()));
            }
        }
    }




    private void setupLevel3() {
        setupLevel1(4,15);
        int gapWidth=50;
        int brickWidth = (panelWidth - gapWidth*3) / 2;
        bricks.add(new Brick(gapWidth, 200, brickWidth, brickHeight, false, -1));
        bricks.add(new Brick(brickWidth + 2*gapWidth, 200, brickWidth, brickHeight, false, -1));
    }

    public void setupLevel2(int rows, int cols) {
        bricks.clear();
        int brickWidth = panelWidth / cols;

        int margin = 2;
        int effectiveCols = cols - 2 * margin;

        int centerX = margin + effectiveCols / 2;
        int centerY = rows / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = margin; col < cols - margin; col++) {
                int distanceFromCenter = Math.abs(centerX - col) + Math.abs(centerY - row);

                if (distanceFromCenter <= centerY) {
                    int x = col * brickWidth;
                    int y = row * brickHeight;
                    bricks.add(new Brick(x, y, brickWidth, brickHeight, true, determineHitPoints()));
                }
            }
        }
        bricks.add(new Brick(0,230, 200, 25, false, -1));
        bricks.add(new Brick(panelWidth-200,230, 200, 25, false, -1));
    }

    public void setupLevel1(int rows, int cols) {
        bricks.clear();
        int brickWidth = panelWidth / cols;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * brickWidth;
                int y = row * brickHeight;
                int adjustedBrickWidth = (col == cols - 1) ? panelWidth - x : brickWidth;
                int hitPoints = determineHitPoints();
                bricks.add(new Brick(x, y, adjustedBrickWidth, brickHeight,true, hitPoints));
            }
        }
    }
    private int determineHitPoints() {
        return random.nextInt(3);
    }
    public boolean areAllBricksGone() {
        for(Brick brick : bricks) {
            if(brick.isVisible() && brick.isDestructible()) {
                return false;
            }
        }
        return true;
    }
}