import javax.swing.*;
import java.awt.*;

public class Main {
    static JFrame frame = new JFrame("Breakout Game");
    static GamePanel gamePanel = null; // Variabile di istanza statica per GamePanel

    public static void main(String[] args) {
        // Ensure that all UI updates are done on the Event Dispatch Thread
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        EventQueue.invokeLater(Main::showMainMenu);
    }

    private static void showMainMenu() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new GridBagLayout());
        cp.revalidate();
        cp.repaint();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            cp.removeAll();
            frame.setLayout(new BorderLayout());
            gamePanel = new GamePanel(cp.getWidth(), cp.getHeight());
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

        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(e -> System.exit(0));
        cp.add(exitButton, new GridBagConstraints(1, 1, 1, 1,0.0,0.0,GridBagConstraints.LAST_LINE_END,GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        frame.setVisible(true);
    }

    private static void showEndGameScreen(String message) {
        // Imposta gamePanel a null per assicurare che la vecchia istanza sia pronta per la Garbage Collection
        gamePanel = null;
        frame.setResizable(true);
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new GridBagLayout());

        // Label con il messaggio passato come parametro
        JLabel endGameLabel = new JLabel(message);
        endGameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cp.add(endGameLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // Pulsante "Torna al Menu"
        JButton menuButton = new JButton("Torna al Menu");
        menuButton.addActionListener(e -> showMainMenu());
        cp.add(menuButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // Pulsante "Esci"
        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(e -> System.exit(0));
        cp.add(exitButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        frame.revalidate();
        frame.repaint();
    }

}
