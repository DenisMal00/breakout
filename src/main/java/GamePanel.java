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
    private int lives =3;
    private Runnable onGameOver;
    private Runnable onVictory;
    private boolean gameWonDisplayed = false;



    public GamePanel() {
        initGamePanel();
    }

    private void initGamePanel() {
        bricks = new BrickMap(); // Example: 3 rows, 7 columns of bricks
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
                bricks.createBricks(getWidth(), getHeight());
                ball.setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
            }
        });
        /*addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Aggiorna la posizione della pallina in base alla posizione del mouse
                ball.setX(e.getX());
                ball.setY(e.getY());
                repaint(); // Ridisegna il pannello per riflettere la nuova posizione della pallina
            }
        });*/
        // Set up a timer to call actionPerformed regularly
        timer = new Timer(DELAY, this);
        timer.start();
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
        drawLives(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Game loop logic
        if(ball.move(getWidth(),getHeight())){
            lives--;
            if(lives ==0){
                lives =3;
                if (onGameOver != null) {
                    onGameOver.run(); // Notifica il listener che il gioco è finito
                }
            }
            paddle.setPosition(getWidth(), getHeight());
            ball.setInitialPosition(paddle.getX(), paddle.getY(), paddle.getWidth());
        }
        Collision.checkCollisionWithPaddle(ball, paddle);
        Collision.checkCollisionWithBricks(ball, bricks);

        if(!gameWonDisplayed && bricks.areAllBricksGone()){
            if (onVictory != null) {
                onVictory.run(); // Notifica il listener che il giocatore ha vinto
                gameWonDisplayed = true; // Imposta che la schermata è stata mostrata
            }
        }
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
    private void drawLives(Graphics2D g2d) {
        g2d.setColor(Color.BLACK); // Imposta il colore del testo
        g2d.setFont(new Font("Arial", Font.BOLD, 14)); // Imposta il font

        String lifeString = "Vite: " + lives;
        int x = 10; // Margine dal bordo sinistro
        int y = getHeight() - 10; // Margine dal bordo inferiore

        g2d.drawString(lifeString, x, y);
    }

    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;
    }

    public void setOnVictory(Runnable onVictory) { // Setter per onVictory
        this.onVictory = onVictory;
    }
}

