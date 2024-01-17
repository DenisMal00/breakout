import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private final int DELAY = 1; // Milliseconds between timer ticks - controls game speed
    // Game objects
    private Paddle paddle;
    private Ball ball;
    private BrickMap bricks;
    private int lifes=0;

    public GamePanel() {
        initGamePanel();
    }

    private void initGamePanel() {
        bricks = new BrickMap(3, 7); // Example: 3 rows, 7 columns of bricks
        ball=new Ball();
        paddle= new Paddle();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow(); // Chiedi esplicitamente il focus qui
        setDoubleBuffered(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Imposta la posizione del paddle ogni volta che il pannello cambia dimensione
                paddle.setPosition(getWidth(), getHeight());
                createBricks();
                ball.setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
            }
        });

        // Set up a timer to call actionPerformed regularly
        timer = new Timer(DELAY, this);
        timer.start();
        repaint();
    }
    private void createBricks() {
        bricks.createBricks(getWidth(), getHeight());
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for better control over graphics
        Graphics2D g2d = (Graphics2D) g;

        // Draw game objects
        paddle.draw(g2d);
        ball.draw(g2d);
        bricks.draw(g2d);

        Toolkit.getDefaultToolkit().sync(); // Uncomment if experiencing display issues
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Game loop logic
        if(ball.move(getWidth(),getHeight())){
            lifes++;
            System.out.println(lifes);
            if(lifes==3){
                lifes=0;
                resetGamePanel();
                return;
            }
            paddle.setPosition(getWidth(), getHeight());
            ball.setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
        }
        Collision.checkCollisionWithPaddle(ball, paddle);
        Collision.checkCollisionWithBricks(ball, bricks);

        repaint(); // Redraw the panel
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed in this game, but required by the interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.moveRight(getWidth());
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //prova
    }

    public void resetGamePanel() {
        // Resetta tutti i componenti del gioco
        bricks = new BrickMap(3, 7); // Example: 3 rows, 7 columns of bricks
        ball=new Ball();
        paddle= new Paddle();
        repaint(); // Ridisegna il pannello
    }
    // Additional methods as needed...
}

