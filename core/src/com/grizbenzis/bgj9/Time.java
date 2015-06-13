package com.grizbenzis.bgj9;

import com.badlogic.gdx.Gdx;

/**
 * Created by sponaas on 6/12/15.
 */
public class Time {
    public static double time = 1.0d;
    public static void update() {
        int actualFPS = Gdx.graphics.getFramesPerSecond();
        actualFPS = ( 0 == actualFPS ) ? 3000 : actualFPS;
        time = ( double )Constants.TARGET_FPS / actualFPS;
    }
}
