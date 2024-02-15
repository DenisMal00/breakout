package com.breakout.menu;

import com.breakout.mvc.GameController;
import com.breakout.mvc.GameModel;
import com.breakout.mvc.GamePanel;
import com.breakout.utils.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuManager {
    private final JFrame frame = new JFrame("Breakout Game");
    private GamePanel gamePanel;
    private GameController gameController;
    private final ResourceManager resourceManager = new ResourceManager();

    public MenuManager() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        showMainMenu();
    }

    private void showMainMenu() {
        Container cp = frame.getContentPane();
        cp.removeAll(); // Clear previous content
        BackgroundPanel backgroundPanel = new BackgroundPanel(resourceManager);
        addMenuButtons(backgroundPanel); // Add menu buttons to the panel
        cp.add(backgroundPanel); // Add the panel to the frame
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
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

    private void startGame(Container cp, int level) {
        cp.removeAll(); // Clear existing components
        cp.setLayout(new BorderLayout()); // Set layout for game panel

        GameModel gameModel = new GameModel(cp.getWidth(), cp.getHeight(), level);
        gameController = new GameController(gameModel, this::showEndGameScreen);
        gameController.setOnGamePause(paused ->setupPausePanel()); // Handle pause logic

        gamePanel = new GamePanel(gameController, resourceManager);
        gameController.setView(gamePanel); // Set the game panel view in controller

        frame.add(gamePanel, BorderLayout.CENTER); // Add the game panel to the frame
        frame.revalidate();
        frame.repaint();

        frame.setResizable(false); // Prevent resizing during the game
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow(); // Focus on game panel for keyboard and mouse input
    }

    private void setupPausePanel() {
        Container cp = frame.getContentPane();
        cp.removeAll(); // Clear current panel

        BackgroundPanel backgroundPanel = new BackgroundPanel(resourceManager);
        addPauseButtons(backgroundPanel); // Add pause menu buttons
        cp.add(backgroundPanel); // Add the panel with buttons to the frame

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
        gameController.resumeGame(); // Resume game logic
        setupGamePanel(); // Reinitialize game panel
    }

    private void setupGamePanel() {
        Container cp = frame.getContentPane();
        cp.removeAll(); // Clear existing content
        gamePanel = new GamePanel(gameController, resourceManager); // Recreate game panel
        gameController.setView(gamePanel); // Reassign the view in controller
        cp.add(gamePanel); // Add game panel back to the frame

        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow(); // Focus on game panel for keyboard inputs
    }

    private void showEndGameScreen(String message) {
        gamePanel = null; // Clear previous game panel
        frame.setResizable(true); // Allow resizing for end game screen
        Container cp = frame.getContentPane();
        cp.removeAll(); // Clear existing content

        BackgroundPanel backgroundPanel = new BackgroundPanel(resourceManager);
        addEndGameButtons(message, backgroundPanel); // Add end game buttons
        cp.add(backgroundPanel); // Add the panel to the frame

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

    private void styleButton(JButton button, int width, int height) {
        // Styles the button with a consistent look and feel
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 22)); // Enlarge on hover
                button.setForeground(Color.WHITE); // Change text color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 20)); // Reset size
                button.setForeground(Color.BLACK); // Reset text color
            }
        });
    }
}