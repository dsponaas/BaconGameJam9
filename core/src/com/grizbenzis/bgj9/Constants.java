package com.grizbenzis.bgj9;

/**
 * Created by sponaas on 6/12/15.
 */
public class Constants {

    public static final int TARGET_FPS = 60;
    public static final String LOG_TAG = "bgj9";
    public static final float GRAVITY = -8f;

    public static float WATER_LEVEL = 4f; // TODO: this isnt going to work long-term. lets get something going then come back to this

    //****************************************************************************
    //*********** World Scale ****************************************************
    //****************************************************************************
    public static final float PIXELS_TO_METERS = 0.01f;
    public static final float METERS_TO_PIXELS = 100f;

    //****************************************************************************
    //*********** Player Movement ************************************************
    //****************************************************************************
    public static final float BASE_PLAYER_ACCEL = 1.0f;
    public static final float BASE_PLAYER_MAXSPEED = 5.0f;
    public static final float SURFACE_FRICTION = 0.5f;

    //****************************************************************************
    //*********** Box2D Bitmasks *************************************************
    //****************************************************************************
    public static final short   BITMASK_PLAYER = 0x0001;
    public static final short   BITMASK_ENEMY = 0x0002;
    public static final short   BITMASK_LEVEL_BOUNDS = 0x0004;
    public static final short   BITMASK_LOOT = 0x0008;
    public static final short   BITMASK_PLAYER_BULLET = 0x0010;
    public static final short   BITMASK_ENEMY_BULLET = 0x0020;
    public static final short   BITMASK_ENVIRONMENT = 0x0040;

    //****************************************************************************
    //*********** Player Actions *************************************************
    //****************************************************************************
    public static final float SHOOTING_COOLDOWN_TIMER = 60f;
    public static final float SHOOTING_CHARGE_UP_TIME = 60f;
    public static final float SHOOTING_OVERCHARGE_TIME = 60f;

}
