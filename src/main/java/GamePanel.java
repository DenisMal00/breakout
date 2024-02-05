import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GamePanel extends JPanel {
    private final GameController controller;
    private BufferedImage[] brickImages;




    public GamePanel(GameController controller) {
        this.controller = controller;
        loadImages();
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.processKey(e.getKeyCode());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controller.movePaddleTo(e.getX());
            }
        });
    }
    private void loadImages() {
        String[] imageFiles = {"/hitpoints_0.jpg", "/hitpoints_1.jpg", "/hitpoints_2.jpeg"};
        brickImages = new BufferedImage[imageFiles.length];

        for (int i = 0; i < imageFiles.length; i++) {
            URL imageUrl = getClass().getResource(imageFiles[i]);
            if (imageUrl != null) {
                try {
                    brickImages[i] = ImageIO.read(imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    brickImages[i] = createPlaceholderImage(i); // Replace with a placeholder if failed to load
                }
            } else {
                System.err.println("Resource not found: " + imageFiles[i]);
                brickImages[i] = createPlaceholderImage(i); // Replace with a placeholder if not found
            }
        }
    }

    private BufferedImage createPlaceholderImage(int hitpoints) {
        // Create a new image with transparency
        BufferedImage placeholderImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholderImage.createGraphics();
        // Decide the color based on the hitpoints
        switch (hitpoints) {
            case 0 -> g2d.setColor(new Color(255, 0, 0, 128)); // Light red for hitpoints 0
            case 1 -> g2d.setColor(Color.RED); // Normal red for hitpoints 1
            case 2 -> g2d.setColor(new Color(128, 0, 0)); // Dark red for hitpoints 2
            default -> g2d.setColor(Color.RED); // Default to normal red if an unexpected value is received
        }
        // Fill the entire image with the selected color
        g2d.fillRect(0, 0, placeholderImage.getWidth(), placeholderImage.getHeight());
        // Draw a black border around the image
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, placeholderImage.getWidth(),  placeholderImage.getHeight());
        // Dispose the graphics context and return the image
        g2d.dispose();
        return placeholderImage;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawPaddle(g2d);
        drawBall(g2d);
        drawPowerUp(g2d);
        drawForceField(g2d);
        drawLives(g2d);
        drawBricks(g2d);
    }
    private void drawForceField(Graphics2D g2d) {
        ForceField forceField = controller.getForceField();
        if (forceField.isActive()) {
            if (forceField.isAboutToExpire()) {
                // Lampeggia il Force Field
                g2d.setColor((System.currentTimeMillis() / 400) % 2 == 0 ? new Color(0, 255, 255, 64) : new Color(0, 255, 255, 255));
            } else {
                g2d.setColor(new Color(0, 255, 255, 255)); // Colore normale
            }
            g2d.fillRect(0, forceField.getYPosition(), getWidth(), forceField.getHeight());
        }
    }
    private void drawBall(Graphics2D g2d) {
        // Disegna la pallina
        List<Ball> balls = controller.getBalls();
        for(Ball ball : balls){
            Composite originalComposite = g2d.getComposite();
            if (ball.isAboutToExpire()) {
                // Lampeggia la pallina
                g2d.setColor((System.currentTimeMillis() / 400) % 2 == 0 ? new Color(255, 0, 0, 128) : Color.red);
            } else {
                g2d.setColor(Color.RED); // Colore normale della pallina
            }
            float diameter = balls.get(0).getRadius() * 2;
            g2d.fillOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
            g2d.setColor(Color.BLACK);
            g2d.drawOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
        }
    }

    private void drawBricks(Graphics2D g2d) {
        BrickMap bricks = controller.getBricks();
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                BufferedImage brickImage = switch (brick.getHitPoints()) {
                    case 0 -> brickImages[0];
                    case 1 -> brickImages[1];
                    case 2 -> brickImages[2];
                    default -> null;
                };
                if (brickImage != null) {
                    g2d.drawImage(brickImage, brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), null);
                }
            }
        }
    }
    private void drawPaddle(Graphics2D g2d) {
        Paddle paddle = controller.getPaddle();
        if(!paddle.isControlInverted()){
            g2d.setColor(Color.BLUE);
        }
        else{
            if (paddle.isAboutToExpire())
                g2d.setColor((System.currentTimeMillis() / 400) % 2 == 0 ? Color.RED : Color.BLUE);
            else
                g2d.setColor(Color.RED);
        }
        g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
    }

    private void drawPowerUp(Graphics2D g2d) {
        for (GameEffect effect : controller.getDroppingEffects()) {
            if (effect.isVisible())
                g2d.drawImage(effect.render(), (int)effect.getX(), (int)effect.getY(),(int)effect.getSize(), (int)effect.getSize(), null);
        }
    }

    private void drawLives(Graphics2D g2d) {
        // Disegna le informazioni del gioco, come il punteggio e le vite
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        int x = 10; // Margine dal bordo sinistro
        int y = getHeight() - 10; // Margine dal bordo inferiore
        g2d.drawString("Vite: " + controller.getLives(), x,y);
    }
}
