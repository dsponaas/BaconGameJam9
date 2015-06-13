package com.grizbenzis.bgj9;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by sponaas on 6/12/15.
 */
public class InputManager implements InputProcessor {

    public static boolean moveLeftActive = false;
    public static boolean moveRightActive = false;
    public static boolean fireLeftActive = false;
    public static boolean fireRightActive = false;

    private static final int MOVE_LEFT_KEY = Input.Keys.LEFT;
    private static final int MOVE_RIGHT_KEY = Input.Keys.RIGHT;
    private static final int FIRE_LEFT_KEY = Input.Keys.Z;
    private static final int FIRE_RIGHT_KEY = Input.Keys.X;

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case MOVE_LEFT_KEY:
                moveLeftActive = true;
                break;
            case MOVE_RIGHT_KEY:
                moveRightActive = true;
                break;
            case FIRE_LEFT_KEY:
                fireLeftActive = true;
                break;
            case FIRE_RIGHT_KEY:
                fireRightActive = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case MOVE_LEFT_KEY:
                moveLeftActive = false;
                break;
            case MOVE_RIGHT_KEY:
                moveRightActive = false;
                break;
            case FIRE_LEFT_KEY:
                fireLeftActive = false;
                break;
            case FIRE_RIGHT_KEY:
                fireRightActive = false;
                break;
        }
        return true;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
