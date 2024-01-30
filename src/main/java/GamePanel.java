import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

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
        drawForceField(g2d);
    }
    private void drawForceField(Graphics2D g2d) {
        ForceField forceField = controller.getModel().getForceField();
        if (forceField.isActive()) {
            g2d.setColor(new Color(0, 255, 255, 64)); // Colore azzurro semitrasparente
            g2d.fillRect(0, forceField.getYPosition(), getWidth(), forceField.getHeight());
        }
    }
    private void drawBall(Graphics2D g2d) {
        // Disegna la pallina
        List<Ball> balls = controller.getModel().getBalls();
        for(Ball ball : balls){
            g2d.setColor(Color.RED);
            float diameter = balls.get(0).getRadius() * 2;
            g2d.fillOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
            g2d.setColor(Color.BLACK);
            g2d.drawOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
        }
    }

    private void drawBricks(Graphics2D g2d) {
        BrickMap bricks = controller.getModel().getBricks();
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                g2d.setColor(brick.getColor()); // Set the color of the brick
                g2d.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight()); // Draw the brick as a filled rectangle
                g2d.setColor(Color.BLACK);
                g2d.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }

    private void drawPaddle(Graphics2D g2d) {
        Paddle paddle = controller.getModel().getPaddle();
        if(!paddle.isControlInverted()){
            g2d.setColor(Color.BLUE);
            g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        }
        else{
            g2d.setColor(Color.RED);
            g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        }
    }

    private void drawPowerUp(Graphics2D g2d) {
        for (GameEffect effect : controller.getModel().getEffectsOnScreen()) {
            effect.render(g2d);
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
