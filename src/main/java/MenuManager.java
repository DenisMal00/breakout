import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuManager {
    private final JFrame frame;
    private GamePanel gamePanel;
    private GameController gameController;
    private final ResourceManager resourceManager;

    public MenuManager() {
        frame = new JFrame("Breakout Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        resourceManager=new ResourceManager();
        showMainMenu();
    }

    private void showMainMenu() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        BackgroundPanel backgroundPanel = new BackgroundPanel(resourceManager);
        addMenuButtons(backgroundPanel);
        cp.add(backgroundPanel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
        frame.setResizable(true);
    }
    private void addMenuButtons(Container container) {
        JButton lvl1Button = new JButton("Level 1");
        lvl1Button.addActionListener(e -> startGame(container,1));
        styleButton(lvl1Button,120, 45);
        container.add(lvl1Button, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton lvl2Button = new JButton("Level 2");
        lvl2Button.addActionListener(e -> startGame(container,2));
        styleButton(lvl2Button,120, 45);
        container.add(lvl2Button, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton lvl3Button = new JButton("Level 3");
        lvl3Button.addActionListener(e -> startGame(container,3));
        styleButton(lvl3Button,120, 45);
        container.add(lvl3Button, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton lvl4Button = new JButton("Level 4");
        lvl4Button.addActionListener(e -> startGame(container,4));
        styleButton(lvl4Button,120, 45);
        container.add(lvl4Button, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        styleButton(exitButton,100, 40);
        container.add(exitButton, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }
    private void startGame(Container cp,int level) {
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        GameModel gameModel = new GameModel(cp.getWidth(), cp.getHeight(), level);
        gameController = new GameController(gameModel, this::showEndGameScreen);
        gameController.setOnGamePause(paused ->setupPausePanel());
        gamePanel = new GamePanel(gameController,resourceManager);
        gameController.setView(gamePanel);
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        frame.setResizable(false);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }



    private void setupPausePanel() {
        Container cp = frame.getContentPane();
        cp.removeAll();

        BackgroundPanel backgroundPanel = new BackgroundPanel(resourceManager);
        addPauseButtons(backgroundPanel);
        cp.add(backgroundPanel);

        frame.revalidate();
        frame.repaint();
    }
    private void addPauseButtons(BackgroundPanel backgroundPanel) {
        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> resumeGame());
        styleButton(resumeButton,150,45);
        backgroundPanel.add(resumeButton,new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton mainMenuButton = new JButton("Menu");
        mainMenuButton.addActionListener(e -> showMainMenu());
        styleButton(mainMenuButton,150,45);
        backgroundPanel.add(mainMenuButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        styleButton(exitButton,150,40);
        backgroundPanel.add(exitButton, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    }


    private void resumeGame() {
        gameController.resumeGame();
        setupGamePanel();
    }

    private void setupGamePanel() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        gamePanel = new GamePanel(gameController, resourceManager);
        gameController.setView(gamePanel);
        cp.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
    }


    private void showEndGameScreen(String message) {
        gamePanel = null;
        frame.setResizable(true);
        Container cp = frame.getContentPane();
        cp.removeAll();

        BackgroundPanel backgroundPanel = new BackgroundPanel(resourceManager);
        addEndGameButtons(message, backgroundPanel);
        cp.add(backgroundPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void addEndGameButtons(String message, BackgroundPanel backgroundPanel) {
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
        backgroundPanel.add(exitButton, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 100, 0), 0, 0));
    }

    private void styleButton(JButton button,int width, int height) {
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width,height));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 22));
                button.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setForeground(Color.BLACK);
            }
        });
    }
}