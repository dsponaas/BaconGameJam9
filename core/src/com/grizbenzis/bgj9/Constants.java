package com.grizbenzis.bgj9;

/**
 * Created by sponaas on 6/12/15.
 */
public class Constants {

    public static final int TARGET_FPS = 60;
    public static final String LOG_TAG = "bgj9";

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
    public final static short   BITMASK_PLAYER = 0x0001;
    public final static short   BITMASK_ENEMY = 0x0002;
    public final static short   BITMASK_LEVEL_BOUNDS = 0x0004;
    public final static short   BITMASK_LOOT = 0x0008;
    public final static short   BITMASK_PLAYER_BULLET = 0x0010;
    public final static short   BITMASK_ENEMY_BULLET = 0x0020;
    public final static short   BITMASK_ENVIRONMENT = 0x0040;

}
