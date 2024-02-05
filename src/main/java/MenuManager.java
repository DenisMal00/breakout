import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MenuManager {
    private JFrame frame;
    private GamePanel gamePanel;
    private GameModel gameModel;
    private GameController gameController;

    public MenuManager() {
        frame = new JFrame("Breakout Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        showMainMenu();
    }

    private void showMainMenu() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        addMenuButtons(backgroundPanel);
        cp.add(backgroundPanel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
    private void addMenuButtons(Container container) {
        JButton lvl1Button = new JButton("Level 1");
        lvl1Button.addActionListener(e -> startGame(container));
        styleButton(lvl1Button,120, 45);
        container.add(lvl1Button, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton lvl2Button = new JButton("Level 2");
        lvl2Button.addActionListener(e -> startGame(container));
        styleButton(lvl2Button,120, 45);
        container.add(lvl2Button, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton lvl3Button = new JButton("Level 3");
        lvl3Button.addActionListener(e -> startGame(container));
        styleButton(lvl3Button,120, 45);
        container.add(lvl3Button, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton lvl4Button = new JButton("Level 4");
        lvl4Button.addActionListener(e -> startGame(container));
        styleButton(lvl4Button,120, 45);
        container.add(lvl4Button, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        styleButton(exitButton,100, 40);
        container.add(exitButton, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }
    public void styleButton(JButton button,int width, int height) {
        // Remove button focus border
        button.setFocusPainted(false);

        // Set text color and transparent background
        button.setPreferredSize(new Dimension(width,height));
        button.setForeground(Color.BLACK); // Colore del testo
        button.setBackground(new Color(0, 0, 0, 0)); // Sfondo trasparente
        button.setOpaque(false); // Rendi il pulsante non opaco
        button.setContentAreaFilled(false); // Non riempire l'area del contenuto
        button.setBorderPainted(false); // Non dipingere il bordo
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Imposta il font

        // Add mouse listener for hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 22)); // Cambia il font al passaggio del mouse
                button.setForeground(Color.WHITE); // Cambia il colore del testo in oro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 20)); // Reimposta il font quando il mouse esce
                button.setForeground(Color.BLACK); // Reimposta il colore del testo in bianco
            }
        });
    }

    private void startGame(Container cp) {
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        gameModel = new GameModel(cp.getWidth(), cp.getHeight());
        gameController = new GameController(gameModel, this::showEndGameScreen);
        gamePanel = new GamePanel(gameController);
        gameController.setView(gamePanel);
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        frame.setResizable(false);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    private void showEndGameScreen(String message) {
        gamePanel = null;
        frame.setResizable(true);
        Container cp = frame.getContentPane();
        cp.removeAll();

        BackgroundPanel backgroundPanel = new BackgroundPanel();

        JLabel statusLabel = new JLabel(message);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 30));
        statusLabel.setForeground(message.endsWith("You win!") ? Color.GREEN: Color.RED);
        backgroundPanel.add(statusLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(140, 0, 0, 0), 0, 0));

        JButton selectLevelButton = new JButton("Select Level");
        selectLevelButton.addActionListener(e -> showMainMenu());
        styleButton(selectLevelButton,190, 45);
        backgroundPanel.add(selectLevelButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 0, 0, 0), 0, 0));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        styleButton(exitButton,100, 40);
        // Riduzione dello spazio sopra il bottone Exit
        backgroundPanel.add(exitButton, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 100, 0), 0, 0));

        cp.add(backgroundPanel);

        frame.revalidate();
        frame.repaint();
    }


}


class BackgroundPanel extends JPanel {
    private BufferedImage background;

    public BackgroundPanel() {
        try {
            background = ImageIO.read(getClass().getResource("/sfondo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        // Configura il font e il colore per la scritta
        g.setFont(new Font("Serif", Font.BOLD, 48)); // Modifica il font e la dimensione come desiderato
        Graphics2D g2d = (Graphics2D) g.create();
        String text = "The Breakout Game";

        // Calcola la posizione della scritta per centrarla nel panel
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 6) + fm.getAscent();

        // Configura l'effetto bordo
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK); // Colore del bordo
        g2d.drawString(text, x + 2, y + 2); // Leggermente spostata per creare l'effetto bordo

        g2d.setColor(Color.ORANGE); // Colore dell'oro per la scritta
        g2d.drawString(text, x, y); // Disegna la scritta

        g2d.dispose(); // Rilascia la copia di Graphics2D
    }

}
