package com.breakout.mvc;

import com.breakout.effects.GameEffect;
import com.breakout.objects.*;
import com.breakout.utils.ResourceManager;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private final GameController controller; // Controller for game logic interaction.
    private final ResourceManager images; // Manages game images.

    // Constructor sets up the panel, key, and mouse listeners.
    public GamePanel(GameController controller, ResourceManager resourceManager) {
        this.controller = controller;
        this.images = resourceManager;
        setFocusable(true); // Makes the panel capable of receiving key input.
        requestFocusInWindow(); // Requests focus to enable key input.
        addKeyListener(new KeyAdapter() { // Key listener for game control.
            @Override
            public void keyPressed(KeyEvent e) {
                controller.processKey(e.getKeyCode());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() { // Mouse motion listener for paddle control.
            @Override
            public void mouseMoved(MouseEvent e) {
                controller.movePaddleTo(e.getX());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background of the panel.

        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d); // Fills the panel background.
        drawDrawableObjects(g2d); //Draws the objects without an image: ball,paddle, force Field
        drawPowerUp(g2d); // Draws active power-ups.
        gameInfo(g2d); // Displays game information (lives, pause instruction).
        drawBricks(g2d); // Draws the bricks.
    }

    private void drawDrawableObjects(Graphics2D g2d) {
        controller.getForceField().draw(g2d);
        controller.getBalls().forEach(ball -> ball.draw(g2d));
        controller.getPaddle().draw(g2d);
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(new Color(200, 200, 200)); // Set background color.
        g2d.fillRect(0, 0, getWidth(), getHeight()); // Fill the entire game area.
    }

    private void drawBricks(Graphics2D g2d) {
        BrickMaps bricks = controller.getBricks();
        // Draws each brick using the appropriate image based on its hit points.
        for (Brick brick : bricks.getBricks()) {
            if (brick.isVisible()) {
                BufferedImage brickImage = images.getImage("brick_" + brick.getHitPoints());
                if (brickImage != null) {
                    g2d.drawImage(brickImage, brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), null);
                }
            }
        }
    }
    private void drawPowerUp(Graphics2D g2d) {
        // Draws each active power-up.
        for (GameEffect effect : controller.getDroppingEffects()) {
            if (effect.isVisible()) {
                g2d.drawImage(images.getImage(effect.getEffectType().toString()), effect.getX(), effect.getY(), effect.getWidth(), effect.getHeight(), null);
            }
        }
    }

    private void gameInfo(Graphics2D g2d) {
        // Displays current lives and pause information.
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Lives: " + controller.getLives(), 10, getHeight() - 10);
        if (!controller.isPaused()) {
            g2d.drawString("Press P to pause", getWidth() - 150, getHeight() - 10);
        }
    }
}
