import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GamePanel extends JPanel {
    private final GameController controller;

    public GamePanel(GameController controller) {
        this.controller = controller;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.processKey(e.getKeyCode());
            }
        });
        /*addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                model.moveBallTo(e.getX(), e.getY());
                repaint();
            }
        });
        */
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                controller.movePaddleTo(e.getX());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawPaddle(g2d);
        drawBricks(g2d);
        drawBall(g2d);
        drawLives(g2d);
        drawPowerUp(g2d);
    }

    private void drawBall(Graphics2D g2d) {
        // Disegna la pallina
        Ball ball = controller.getModel().getBall();
        g2d.setColor(Color.RED);
        float diameter = ball.getRadius() * 2;
        g2d.fillOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);

    }

    private void drawBricks(Graphics2D g2d) {
        BrickMap bricks = controller.getModel().getBricks();
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                g2d.setColor(Color.GREEN); // Set the color of the brick
                g2d.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight()); // Draw the brick as a filled rectangle
                g2d.setColor(Color.BLACK);
                g2d.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }

    private void drawPaddle(Graphics2D g2d) {
        Paddle paddle = controller.getModel().getPaddle();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
    }

    private void drawPowerUp(Graphics2D g2d) {
        for (PowerUp powerUp : controller.getModel().getPowerUpsOnScreen()) {
            switch (powerUp.getType()) {
                case DOUBLE_BALL:
                    g2d.setColor(Color.YELLOW); // Scegli un colore per questo tipo di power-up
                    break;
                case REVERSE_CONTROLS:
                    g2d.setColor(Color.MAGENTA); // Un altro colore per un tipo diverso di power-up
                    break;
            }
            float x = powerUp.getX();
            float y = powerUp.getY();
            float size = powerUp.getSize(); // La dimensione del power-up
            g2d.fillOval((int) x, (int) y, (int) size, (int) size); // Qui stiamo disegnando un cerchio
        }
    }

    private void drawLives(Graphics2D g2d) {
        // Disegna le informazioni del gioco, come il punteggio e le vite
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        int x = 10; // Margine dal bordo sinistro
        int y = getHeight() - 10; // Margine dal bordo inferiore

        g2d.drawString("Vite: " + controller.getModel().getLives(), x,y);
    }
}
