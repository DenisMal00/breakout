package com.breakout.menu;

import com.breakout.utils.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class BackgroundPanel extends JPanel {
    private final BufferedImage background;

    public BackgroundPanel(ResourceManager resourceManager) {
        // Set the background image from resource manager
        background = resourceManager.getImage("Background");
        setLayout(new GridBagLayout()); // Use GridBagLayout for flexibility
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image to fit the panel size
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        // Customize the game title appearance
        g.setFont(new Font("Serif", Font.BOLD, 48));
        Graphics2D g2d = (Graphics2D) g.create();
        String text = "The Breakout Game";

        // Center the title in the panel
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 6) + fm.getAscent();

        // Apply text effects for better visibility
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK); // Shadow color
        g2d.drawString(text, x + 2, y + 2); // Draw shadow

        g2d.setColor(Color.ORANGE); // Main text color
        g2d.drawString(text, x, y); // Draw main text

        g2d.dispose(); // Clean up the graphics object
    }
}
