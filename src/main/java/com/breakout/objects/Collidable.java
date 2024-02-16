package com.breakout.objects;

// Interface for objects that can collide with the ball.
public interface Collidable {
    int getX(); // Get X position.
    int getY(); // Get Y position.
    int getWidth(); // Get width.
    int getHeight(); // Get height.
}