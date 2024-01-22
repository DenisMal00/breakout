import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private GameModel model;

    public GamePanel(GameModel model) {
        this.model = model;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                model.processKey(e.getKeyCode());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawGameObjects(g2d);
        drawGameInfo(g2d);
    }

    private void drawGameObjects(Graphics2D g2d) {
        // Esempi di disegno di oggetti di gioco
        // Dovrai implementare i metodi draw per ciascun oggetto nel tuo gioco

        // Disegna il paddle
        Paddle paddle = model.getPaddle();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        // Disegna la pallina
        Ball ball = model.getBall();
        g2d.setColor(Color.RED);
        float diameter = ball.getRadius() * 2;
        g2d.fillOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
        // Disegna i mattoni
        BrickMap bricks = model.getBricks();
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                if (brick.isVisible()) {
                    g2d.setColor(Color.GREEN); // Set the color of the brick
                    g2d.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight()); // Draw the brick as a filled rectangle

                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                }
            }
        }

        // Altri oggetti di gioco...
    }

    private void drawGameInfo(Graphics2D g2d) {
        // Disegna le informazioni del gioco, come il punteggio e le vite
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        int x = 10; // Margine dal bordo sinistro
        int y = getHeight() - 10; // Margine dal bordo inferiore

        g2d.drawString("Vite: " + model.getLives(), x,y);
        // Altre informazioni...
    }

    // Altri metodi utili per la gestione della vista...
}
