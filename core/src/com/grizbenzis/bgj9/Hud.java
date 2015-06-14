package com.grizbenzis.bgj9;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by kyle on 6/13/15.
 */
public class Hud {

    private BitmapFont _hudFont;
    private BitmapFont _gameOverFont;

    private static Hud _instance = null;


    private Hud() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        _hudFont = generator.generateFont(parameter);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 64;
        _gameOverFont = generator.generateFont(parameter2);

        generator.dispose();
    }

    public static Hud getInstance() {
        if( _instance == null )
            _instance = new Hud();
        return _instance;
    }

    public BitmapFont getGameOverFont() {
        return _gameOverFont;
    }
    public BitmapFont getFont() {
        return _hudFont;
    }
}
