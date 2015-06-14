package com.grizbenzis.bgj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.grizbenzis.bgj9.ResourceManager;
import com.grizbenzis.bgj9.bgj9;

/**
 * Created by sponaas on 6/12/15.
 */
public class SplashScreen implements Screen {

    private Stage _stage;
    private SpriteBatch _spriteBatch;
//    private Sprite _titleSprite;

    private ImageButton _startButton;

    public SplashScreen() {}

    @Override
    public void show() {
        _spriteBatch = new SpriteBatch();

//        _titleSprite = new Sprite(ResourceManager.getTexture("splashscreen"));
//        _titleSprite.setPosition(0f, 0f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.act(delta);
        _spriteBatch.begin();
//        _titleSprite.draw(_spriteBatch);
        _stage.draw();
        _spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        if(null == _stage)
            _stage = new Stage();
        _stage.clear();
        Gdx.input.setInputProcessor(_stage);

        Texture startButtonTex = ResourceManager.getTexture("splashscreen");
        _startButton = new ImageButton(new SpriteDrawable(new Sprite(startButtonTex)));
        _startButton.setX((Gdx.graphics.getWidth() / 2) - (_startButton.getWidth() / 2));
        _startButton.setY((Gdx.graphics.getHeight() / 2) - (_startButton.getHeight() / 2));

        _startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bgj9.game.setScreen(new GameScreen());
            }
        });

        _stage.addActor(_startButton);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        _stage.dispose();
        _spriteBatch.dispose();
    }

}
