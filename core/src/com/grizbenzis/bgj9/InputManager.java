package com.grizbenzis.bgj9;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by sponaas on 6/12/15.
 */
public class InputManager implements InputProcessor {

    public static boolean moveLeftActive = false;
    public static boolean moveRightActive = false;

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.LEFT:
                moveLeftActive = true;
                break;
            case Input.Keys.RIGHT:
                moveRightActive = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.LEFT:
                moveLeftActive = false;
                break;
            case Input.Keys.RIGHT:
                moveRightActive = false;
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
