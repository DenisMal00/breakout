package com.breakout.objects;

import com.breakout.utils.GameConstants;
import lombok.Data;

@Data
public class ForceField {
    private boolean isActive = false; // State of the force field (active or not).
    private final int yPosition; // Vertical position of the force field in the game panel.
    private final int height = GameConstants.FORCE_FIELD_HEIGHT; // Height of the force field.
    private boolean isAboutToExpire = false; // Indicator for when the force field is about to expire.

    // Constructor sets the position of the force field at the bottom of the game panel.
    public ForceField(int panelHeight) {
        this.yPosition = panelHeight - height;
    }
}
