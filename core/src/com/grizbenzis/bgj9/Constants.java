package com.grizbenzis.bgj9;

/**
 * Created by sponaas on 6/12/15.
 */
public class Constants {

    public static final int TARGET_FPS = 60;
    public static final String LOG_TAG = "bgj9";
    public static final float GRAVITY = -8f;

    public static final float DEATH_TIME = 120f;
    public static final float INVINCIBILITY_TIME = 180f;
    public static float SKY_HEIGHT_IN_PIXELS = 120;
    public static float MIN_DETONATION_DEPTH_IN_PIXELS = 60;
    public static float FLOOR_DETONATION_BUFFER_IN_PIXELS = 60;

    public static int NUM_LIVES = 2;
    public static final float LEVEL_TIME = 1200f;
    public static float BASE_ENEMY_SPAWN_TIMER = 300f;
    public static float LEVEL_ADVANCE_SPAWN_TIMER_MOD = 0.9f;

    //****************************************************************************
    //*********** World Scale ****************************************************
    //****************************************************************************
    public static final float PIXELS_TO_METERS = 0.01f;
    public static final float METERS_TO_PIXELS = 100f;

    //****************************************************************************
    //*********** Player Movement ************************************************
    //****************************************************************************
    public static final float BASE_PLAYER_ACCEL = 0.5f;
    public static final float BASE_PLAYER_MAXSPEED = 4.0f;
    public static final float SURFACE_FRICTION = 1f;

    //****************************************************************************
    //*********** Box2D Bitmasks *************************************************
    //****************************************************************************
    public static final short   BITMASK_PLAYER = 0x0001;
    public static final short   BITMASK_ENEMY = 0x0002;
    public static final short   BITMASK_LEVEL_BOUNDS = 0x0004;
    public static final short   BITMASK_LOOT = 0x0008;
    public static final short   BITMASK_PLAYER_BULLET = 0x0010;
    public static final short   BITMASK_ENEMY_BULLET = 0x0020;
    public static final short   BITMASK_EXPLOSION = 0x0040;
    public static final short   BITMASK_WATER_SURFACE = 0x0080;

    //****************************************************************************
    //*********** Player Actions *************************************************
    //****************************************************************************
    public static final float SHOOTING_COOLDOWN_TIMER = 60f;
    public static final float SHOOTING_CHARGE_UP_TIME = 60f;
    public static final float SHOOTING_OVERCHARGE_TIME = 60f;
    public static final float DEPTH_CHARGE_EXPLOSION_DURATION = 30f;

    public static final float POWERUP_TIMER = 600f;
    public static final float POWERUP_SPEED_ACCEL_FACTOR = 1.25f;
    public static final float POWERUP_SPEED_MAX_SPEED_FACTOR = 2f;
    public enum PowerupType {
        POINTS_2X,
        SPEED_UP,
        EXPLOSION_UP,
        EXTRA_LIFE;

        public static PowerupType fromInt(int x) {
            switch(x) {
                case 0:
                    return POINTS_2X;
                case 1:
                    return SPEED_UP;
                case 2:
                    return EXPLOSION_UP;
                case 3:
                    return EXTRA_LIFE;
            }
            return null;
        }
    }

}
