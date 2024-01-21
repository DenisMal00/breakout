import javax.swing.*;
import java.awt.*;

public class Main {
    static JFrame frame = new JFrame("Breakout Game");
    static GamePanel gamePanel = null; // Variabile di istanza statica per GamePanel

    public static void main(String[] args) {
        // Ensure that all UI updates are done on the Event Dispatch Thread
        EventQueue.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = frame.getContentPane();
        cp.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            cp.removeAll();
            frame.setLayout(new BorderLayout());
            gamePanel = new GamePanel();
            gamePanel.setOnGameOver(() -> showEndGameScreen("Hai perso!"));
            gamePanel.setOnVictory(() -> showEndGameScreen("Hai vinto!"));
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            frame.setResizable(false);
            gamePanel.setFocusable(true);
            gamePanel.requestFocusInWindow();
        });

        cp.add(startButton, new GridBagConstraints(0,0,2,2,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.NONE,new Insets(0, 0, 0, 0),0,0 ));

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showEndGameScreen(String message) {
        // Imposta gamePanel a null per assicurare che la vecchia istanza sia pronta per la Garbage Collection
        gamePanel = null;
        frame.setResizable(true);
        frame.getContentPane().removeAll();

        JPanel endGamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Label con il messaggio passato come parametro
        JLabel endGameLabel = new JLabel(message);
        endGameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        endGamePanel.add(endGameLabel, gbc);

        // Pulsante "Torna al Menu"
        JButton menuButton = new JButton("Torna al Menu");
        menuButton.addActionListener(e -> {
            createAndShowGUI();
            endGamePanel.setVisible(false);
        });
        endGamePanel.add(menuButton, gbc);

        // Pulsante "Esci"
        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(e -> System.exit(0));
        endGamePanel.add(exitButton, gbc);

        frame.add(endGamePanel);
        frame.revalidate();
        frame.repaint();
    }
}
