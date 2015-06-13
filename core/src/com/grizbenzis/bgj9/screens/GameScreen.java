package com.grizbenzis.bgj9.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.grizbenzis.bgj9.*;
import com.grizbenzis.bgj9.actors.Actor;
import com.grizbenzis.bgj9.actors.Player;
import com.grizbenzis.bgj9.components.BodyComponent;
import com.grizbenzis.bgj9.components.PositionComponent;
import com.grizbenzis.bgj9.components.RenderComponent;
import com.grizbenzis.bgj9.components.SpriteComponent;
import com.grizbenzis.bgj9.systems.DepthChargeSystem;
import com.grizbenzis.bgj9.systems.ExplosionSystem;
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

    private Actor playerActor;

    private int _screenWidth, _screenHeight;
    private SpriteBatch _spriteBatch;
    private SpriteBatch _hudBatch;

    private InputManager _inputManager;

    private Box2DDebugRenderer _debugRenderer;

    public GameScreen(bgj9 game) {
        _game = game;
    }

    @Override
    public void show() {
        _spriteBatch = new SpriteBatch();
        _hudBatch = new SpriteBatch();
        _debugRenderer = new Box2DDebugRenderer();

        _engine = initializeEngine();

        _world = new World(new Vector2(0f, Constants.GRAVITY), false);
        BodyFactory.initialize(_world);

        _screenWidth = Gdx.graphics.getWidth();
        _screenHeight = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, _screenWidth, _screenHeight);
        _camera.update();

        EntityManager.initialize(_engine, _world);

        _inputManager = new InputManager();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(_inputManager);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private Entity makePlayerHack() {
        Entity entity = new Entity();

        Sprite sprite = new Sprite(ResourceManager.getTexture("player"));
        Vector2 position = new Vector2(4f * Constants.METERS_TO_PIXELS, GameBoardInfo.getInstance().getWaterLevel());

        Body body = BodyFactory.getInstance().generate(entity, "player.json", position);

        PositionComponent playerPositionComponent = new PositionComponent(position.x, position.y);
        BodyComponent playerBodyComponent = new BodyComponent(playerPositionComponent, body);
        SpriteComponent playerRenderComponent = new SpriteComponent(sprite);
        RenderComponent renderComponent = new RenderComponent(0);

        entity.add(playerPositionComponent).add(playerBodyComponent).add(playerRenderComponent).add(renderComponent);
        return entity;
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
        _engine.update((float)Time.time);
        _spriteBatch.setProjectionMatrix(_camera.combined);
        _spriteBatch.end();

        playerActor.update(); // TODO: here for current convenince. roll into entitymanager or omsehitng?
        EntityManager.getInstance().update();
        _debugRenderer.render(_world, debugMatrix);

        renderHud();
    }

    @Override
    public void resize(int width, int height) {
        _screenWidth = width;
        _screenHeight = height;
        GameBoardInfo.initialize(width, height);

        Entity playerEntity = makePlayerHack();
        EntityManager.getInstance().addEntity(playerEntity);
        playerActor = new Player(playerEntity);

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
        _hudBatch.dispose();
    }

    private void renderHud() {
        Hud hud = Hud.getInstance();

        BitmapFont hudFont = hud.getFont();

        _hudBatch.begin();
        float scoreIconXPos = 4f; // lil bit of padding here...
        hudFont.draw( _hudBatch, "SCORE: " + 0, scoreIconXPos, (float)Gdx.graphics.getHeight() - 4f ); // TODO: actually show the score
        _hudBatch.end();
    }

    private Engine initializeEngine() {
        Engine engine = new Engine();

        PositionSystem positionSystem = new PositionSystem(0);
        RenderSystem renderSystem = new RenderSystem(_spriteBatch, 1);
        DepthChargeSystem depthChargeSystem = new DepthChargeSystem(2);
        ExplosionSystem explosionSystem = new ExplosionSystem(3);

        engine.addSystem(positionSystem);
        engine.addSystem(renderSystem);
        engine.addSystem(depthChargeSystem);
        engine.addSystem(explosionSystem);

        return engine;
    }
}
