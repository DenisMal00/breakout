package com.breakout.objects;

import com.breakout.utils.Collidable;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Brick implements Collidable {
    private final int x, y; // Position of the brick.
    private final int width, height; // Dimensions of the brick.
    private final boolean isDestructible; // Whether the brick can be destroyed.
    private int hitPoints; // Health of the brick.
    private UUID id; // Unique identifier for the brick.

    // Constructor for creating a brick instance.
    public Brick(int x, int y, int width, int height, boolean isDestructible, int hitpoints) {
        this.id = UUID.randomUUID(); // Assign a unique ID.
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isDestructible = isDestructible;
        if(!this.isDestructible)
            this.hitPoints = -1;
        else
            this.hitPoints=hitpoints;
    }

    // Returns whether the brick should be visible.
    public boolean isVisible() {
        return !isDestructible || hitPoints >= 0;
    }

    // Handles the brick getting hit and decreases its hit points.
    public boolean hit() {
        if (isDestructible && hitPoints >= 0) {
            hitPoints--;
            return hitPoints == -1;
        }
        return false;
    }
}
