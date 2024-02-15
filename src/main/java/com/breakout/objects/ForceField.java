package com.breakout.objects;

import com.breakout.utils.GameConstants;
import lombok.Data;

import java.awt.*;

@Data
public class ForceField implements Drawable{
    private boolean isActive = false; // State of the force field (active or not).
    private final int yPosition; // Vertical position of the force field in the game panel.
    private final int height = GameConstants.FORCE_FIELD_HEIGHT; // Height of the force field.
    private boolean isAboutToExpire = false; // Indicator for when the force field is about to expire.
    private final int panelWidth;
    // Constructor sets the position of the force field at the bottom of the game panel.
    public ForceField(int panelHeight, int panelWidth) {
        this.yPosition = panelHeight - height;
        this.panelWidth=panelWidth;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.isActive) {
            Color color = this.isAboutToExpire ?
                    ((System.currentTimeMillis() / 400) % 2 == 0 ? new Color(0, 255, 255, 64) : new Color(0, 255, 255, 255)) :
                    new Color(0, 255, 255, 255);
            g2d.setColor(color);
            g2d.fillRect(0, this.yPosition, panelWidth, this.height);
        }
    }
}
