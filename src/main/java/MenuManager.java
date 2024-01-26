import javax.swing.*;
import java.awt.*;

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
        cp.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startGame(cp));
        cp.add(startButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(e -> System.exit(0));
        cp.add(exitButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
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
        cp.setLayout(new GridBagLayout());

        JLabel endGameLabel = new JLabel(message);
        endGameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cp.add(endGameLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton menuButton = new JButton("Torna al Menu");
        menuButton.addActionListener(e -> showMainMenu());
        cp.add(menuButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton exitButton = new JButton("Esci");
        exitButton.addActionListener(e -> System.exit(0));
        cp.add(exitButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        frame.revalidate();
        frame.repaint();
    }
}
