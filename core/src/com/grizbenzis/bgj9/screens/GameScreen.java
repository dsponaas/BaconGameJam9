package com.grizbenzis.bgj9.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.grizbenzis.bgj9.Constants;
import com.grizbenzis.bgj9.EntityManager;
import com.grizbenzis.bgj9.Time;
import com.grizbenzis.bgj9.bgj9;
import com.grizbenzis.bgj9.systems.PositionSystem;
import com.grizbenzis.bgj9.systems.RenderSystem;

/**
 * Created by sponaas on 6/12/15.
 */
public class GameScreen implements Screen {

    private bgj9 _game;
    private Engine _engine;
    private OrthographicCamera _camera;
    private World _world;

    private int _screenWidth, _screenHeight;
    private SpriteBatch _spriteBatch;

    private Box2DDebugRenderer _debugRenderer;

    public GameScreen(bgj9 game) {
        _game = game;
    }

    @Override
    public void show() {
        _spriteBatch = new SpriteBatch();
        _debugRenderer = new Box2DDebugRenderer();

        _engine = initializeEngine();

        _world = new World(new Vector2(0f, 0f), false);
//        BodyFactory.initialize(_world);

        _screenWidth = Gdx.graphics.getWidth();
        _screenHeight = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, _screenWidth, _screenHeight);
        _camera.update();

        EntityManager.initialize(_engine, _world);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Time.update();
        _world.step(1f / 60f, 6, 2);
        Matrix4 debugMatrix = _spriteBatch.getProjectionMatrix().cpy().scale(Constants.METERS_TO_PIXELS, Constants.METERS_TO_PIXELS, 0);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        _camera.position.x = _screenWidth / 2f;
        _camera.position.y = _screenHeight / 2f;
        _camera.update();

        _spriteBatch.begin();
        _engine.update( (float)Time.time );
        _spriteBatch.setProjectionMatrix(_camera.combined);
        _spriteBatch.end();

        EntityManager.getInstance().update();
    }

    @Override
    public void resize(int width, int height) {
        _screenWidth = width;
        _screenHeight = height;

        _camera.setToOrtho(false, _screenWidth, _screenHeight);
        _camera.update();
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
        _world.dispose();
        _spriteBatch.dispose();

    }

    private Engine initializeEngine() {
        Engine engine = new Engine();

        PositionSystem positionSystem = new PositionSystem(0);
        RenderSystem renderSystem = new RenderSystem(_spriteBatch, 2);

        engine.addSystem(positionSystem);
        engine.addSystem(renderSystem);

        return engine;
    }
}
