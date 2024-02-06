import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {
    private final GameController controller;
    private ResourceManager images;
    public GamePanel(GameController controller,ResourceManager resourceManager) {
        this.controller = controller;
        images=resourceManager;
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color backgroundColor = new Color(200, 200, 200); // Grigio chiaro
        g.setColor(backgroundColor);
        // Riempie il pannello con il colore di sfondo
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

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
            if (ball.isAboutToExpire()) {
                // Lampeggia la pallina
                g2d.setColor((System.currentTimeMillis() / 400) % 2 == 0 ?  new Color(255,165,0,64):new Color(255,165,0,255) );
            } else {
                g2d.setColor(new Color(255,165,0,255)); // Colore normale della pallina
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
                    case 0 -> images.getImage("brick_0");
                    case 1 -> images.getImage("brick_1");
                    case 2 -> images.getImage("brick_2");
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
        // Impostazioni per l'ombra
        int shadowOffset = 1; // Distanza dell'ombra dal paddle
        Color shadowColor = new Color(0, 0, 0, 255); // Colore dell'ombra con trasparenza
        RoundRectangle2D.Float shadow = new RoundRectangle2D.Float(paddle.getX() + shadowOffset, paddle.getY() + shadowOffset, paddle.getWidth(), paddle.getHeight(), 15, 15);
        g2d.setColor(shadowColor);
        g2d.fill(shadow);
        if(!paddle.isControlInverted())
            g2d.setColor(Color.BLUE);
        else{
            if (paddle.isAboutToExpire())
                g2d.setColor((System.currentTimeMillis() / 400) % 2 == 0 ? Color.RED : Color.BLUE);
            else
                g2d.setColor(Color.RED);
        }
        RoundRectangle2D.Float roundPaddle = new RoundRectangle2D.Float(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 15, 15);
        g2d.fill(roundPaddle);
    }

    private void drawPowerUp(Graphics2D g2d) {
        for (GameEffect effect : controller.getDroppingEffects()) {
            if (effect.isVisible())
                g2d.drawImage(images.getImage(effect.getEffectType().toString()), (int)effect.getX(), (int)effect.getY(),(int)effect.getSize(), (int)effect.getSize(), null);
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
