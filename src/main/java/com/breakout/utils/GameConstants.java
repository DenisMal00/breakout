package com.breakout.utils;

// Constants defining various parameters of the game like dimensions, speeds, durations, etc.
public class GameConstants {
    public static final int TIMER_INTERVAL = 5;
    public static final int UPDATES_PER_SECOND = 1000 / TIMER_INTERVAL;
    public static final float BALL_SPEEDBOOST =2;
    public static final int EFFECT_DURATION=1000; //5 seconds based on time interval of the timer = 5
    public static final float BALL_RADIUS = 10;
    public static final float INITIAL_BALL_DX = 1.5f;
    public static final float INITIAL_BALL_DY = -1.5f;
    public static final float INITIAL_BALL_MAX_SPEED_CHANGE = 1.2f;
    public static final int BRICK_HEIGHT =30;
    public static final int FORCE_FIELD_HEIGHT = 40;
    public static final int MOUSE_INACTIVITY_THRESHOLD = 300; //0.3 seconds
    public static final float EFFECT_SIZE = 25;
    public static final float EFFECT_DOWNWARD_SPEED = 0.7f;
    public static final int POWER_UP_SPAWN_CHANCE = 80;
    public static final int INITIAL_LIVES = 3;
    public static final int PADDLE_WIDTH = 130;
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_MARGIN_FROM_BOTTOM = 40;
    public static final int PADDLE_SPEED_MOUSE = 8;
    public static final int PADDLE_SPEED_KEYBOARD = 50;
}
