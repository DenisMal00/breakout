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
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

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
        drawPaddle(g2d); // Draws the paddle.
        drawBall(g2d); // Draws the ball(s).
        drawPowerUp(g2d); // Draws active power-ups.
        drawForceField(g2d); // Draws the force field, if active.
        gameInfo(g2d); // Displays game information (lives, pause instruction).
        drawBricks(g2d); // Draws the bricks.
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(new Color(200, 200, 200)); // Set background color.
        g2d.fillRect(0, 0, getWidth(), getHeight()); // Fill the entire game area.
    }

    private void drawForceField(Graphics2D g2d) {
        ForceField forceField = controller.getForceField();
        // Draws force field if active, with flickering effect when expiring.
        if (forceField.isActive()) {
            g2d.setColor(forceField.isAboutToExpire() ?
                    ((System.currentTimeMillis() / 400) % 2 == 0 ? new Color(0, 255, 255, 64) : new Color(0, 255, 255, 255)) :
                    new Color(0, 255, 255, 255));
            g2d.fillRect(0, forceField.getYPosition(), getWidth(), forceField.getHeight());
        }
    }

    private void drawBall(Graphics2D g2d) {
        List<Ball> balls = controller.getBalls();
        // Draws each ball, applying flickering effect when expiring.
        for (Ball ball : balls) {
            g2d.setColor(ball.isAboutToExpire() ?
                    ((System.currentTimeMillis() / 400) % 2 == 0 ? new Color(255,165,0,64) : new Color(255,165,0,255)) :
                    new Color(255,165,0,255));
            float diameter = ball.getRadius() * 2;
            g2d.fillOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()), (int)diameter, (int)diameter);
        }
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

    private void drawPaddle(Graphics2D g2d) {
        Paddle paddle = controller.getPaddle();
        // Draws paddle shadow for depth effect.
        g2d.setColor(new Color(0, 0, 0, 255));
        g2d.fill(new RoundRectangle2D.Float(paddle.getX() + 1, paddle.getY() + 1, paddle.getWidth(), paddle.getHeight(), 15, 15));
        // Changes paddle color based on control inversion and expiration.
        g2d.setColor(paddle.isControlInverted() ?
                (paddle.isAboutToExpire() ? ((System.currentTimeMillis() / 400) % 2 == 0 ? Color.RED : Color.BLUE) : Color.RED) :
                Color.BLUE);
        g2d.fill(new RoundRectangle2D.Float(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 15, 15));
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
