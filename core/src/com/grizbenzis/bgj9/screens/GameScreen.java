package com.grizbenzis.bgj9.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.grizbenzis.bgj9.*;
import com.grizbenzis.bgj9.actors.Player;
import com.grizbenzis.bgj9.components.ParallaxBackgroundComponent;
import com.grizbenzis.bgj9.components.PlayerDataComponent;
import com.grizbenzis.bgj9.systems.*;

/**
 * Created by sponaas on 6/12/15.
 */
public class GameScreen implements Screen {

    private Engine _engine;
    private OrthographicCamera _camera;
    private World _world;
    private ContactManager _contactManager;

    private int _screenWidth, _screenHeight;
    private SpriteBatch _spriteBatch;
    private SpriteBatch _hudBatch;

    private InputManager _inputManager;

    private Box2DDebugRenderer _debugRenderer;

    public GameScreen() {}

    @Override
    public void show() {
        _spriteBatch = new SpriteBatch();
        _hudBatch = new SpriteBatch();
        _debugRenderer = new Box2DDebugRenderer();

        _engine = initializeEngine();

        _world = new World(new Vector2(0f, Constants.GRAVITY), false);
        BodyFactory.initialize(_world);
        _contactManager = new ContactManager(_engine, _world);

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
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

        GameBoardInfo.getInstance().update();

        _spriteBatch.begin();
        _engine.update((float) Time.time);
        _spriteBatch.setProjectionMatrix(_camera.combined);
        renderActivePowerups();
        _spriteBatch.end();

        EntityManager.getInstance().update();
//        _debugRenderer.render(_world, debugMatrix);

        renderHud();
    }

    @Override
    public void resize(int width, int height) {
        _screenWidth = width;
        _screenHeight = height;
        GameBoardInfo.initialize(width, height);

        Entity playerEntity = Player.makePlayerEntity();
        EntityManager.getInstance().addEntity(playerEntity);
        Player player = new Player(playerEntity);
        EntityManager.getInstance().addActor(player);
        GameBoardInfo.getInstance().setPlayer(player);

        addLevelBounds(0f, 0f, 0f, height * Constants.PIXELS_TO_METERS);
        addLevelBounds(width * Constants.PIXELS_TO_METERS, 0f, width * Constants.PIXELS_TO_METERS, height * Constants.PIXELS_TO_METERS);

        addWaterSurface(0f,
                (height - Constants.SKY_HEIGHT_IN_PIXELS - 5f) * Constants.PIXELS_TO_METERS, // TODO: CAREFUL! HACK!
                (width) * Constants.PIXELS_TO_METERS,
                (height - Constants.SKY_HEIGHT_IN_PIXELS - 5f) * Constants.PIXELS_TO_METERS); // TODO: CAREFUL! HACK!

        makeBackgroundHack();

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
        int level = GameBoardInfo.getInstance().getLevel();
        int score = GameBoardInfo.getInstance().getScore();
        int lives = GameBoardInfo.getInstance().getLives();
        hudFont.draw(_hudBatch, "LIVES: " + lives + "   LEVEL: " + level + "   SCORE: " + score, scoreIconXPos, (float) Gdx.graphics.getHeight() - 4f); // TODO: actually show the score
        _hudBatch.end();
    }

    private final float POWERUP_BUFFER_HACK = 25f;
    private void renderActivePowerups() {
        float drawPosX = GameBoardInfo.getInstance().getWidth() - POWERUP_BUFFER_HACK;
        float drawPosY = GameBoardInfo.getInstance().getHeight() - POWERUP_BUFFER_HACK + 3f;

        PlayerDataComponent playerData = GameBoardInfo.getInstance().getPlayerData();
        if(playerData.powerupTimeExplosionUp > 0f) {
            _spriteBatch.draw(ResourceManager.getTexture("powerupexplsmall"), drawPosX, drawPosY);
            drawPosX -= POWERUP_BUFFER_HACK;
        }
        if(playerData.powerupTimeSpeedUp > 0f) {
            _spriteBatch.draw(ResourceManager.getTexture("powerupspdsmall"), drawPosX, drawPosY);
            drawPosX -= POWERUP_BUFFER_HACK;
        }
        if(playerData.powerupTimePoints2x > 0f) {
            _spriteBatch.draw(ResourceManager.getTexture("poweruppointssmall"), drawPosX, drawPosY);
            drawPosX -= POWERUP_BUFFER_HACK;
        }
    }

    private Engine initializeEngine() {
        Engine engine = new Engine();

        ParallaxBackgroundRenderSystem parallaxBackgroundRenderSystem = new ParallaxBackgroundRenderSystem(_spriteBatch, 0);
        PositionSystem positionSystem = new PositionSystem(0);
        RenderSpriteSystem renderSpriteSystem = new RenderSpriteSystem(_spriteBatch, 1);
        RenderAnimationSystem renderAnimationSystem = new RenderAnimationSystem(_spriteBatch, 3);
        DepthChargeSystem depthChargeSystem = new DepthChargeSystem(4);
        BulletSystem bulletSystem = new BulletSystem(5);
        ExplosionSystem explosionSystem = new ExplosionSystem(6);
        DeathTimerSystem deathTimerSystem = new DeathTimerSystem(7);
        PlayerDataSystem playerDataSystem = new PlayerDataSystem(8);
        PowerupSystem powerupSystem = new PowerupSystem(9);

        engine.addSystem(parallaxBackgroundRenderSystem);
        engine.addSystem(positionSystem);
        engine.addSystem(renderSpriteSystem);
        engine.addSystem(renderAnimationSystem);
        engine.addSystem(depthChargeSystem);
        engine.addSystem(bulletSystem);
        engine.addSystem(explosionSystem);
        engine.addSystem(deathTimerSystem);
        engine.addSystem(playerDataSystem);
        engine.addSystem(powerupSystem);

        return engine;
    }

    private void addLevelBounds(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0f, 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.BITMASK_LEVEL_BOUNDS;
        fixtureDef.filter.maskBits = Constants.BITMASK_PLAYER | Constants.BITMASK_PLAYER_BULLET | Constants.BITMASK_ENEMY_BULLET;

        EdgeShape shape = new EdgeShape();
        shape.set(x1, y1, x2, y2);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;

        Body body = _world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void addWaterSurface(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0f, 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.BITMASK_WATER_SURFACE;
        fixtureDef.filter.maskBits = Constants.BITMASK_ENEMY_BULLET | Constants.BITMASK_PLAYER;

        EdgeShape shape = new EdgeShape();
        shape.set(x1, y1, x2, y2);
        fixtureDef.shape = shape;
        fixtureDef.friction = Constants.SURFACE_FRICTION;

        Body body = _world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void makeBackgroundHack() {
        Sprite sprite = new Sprite(ResourceManager.getTexture("skybackground1"));
        Entity entity = new Entity();
        ParallaxBackgroundComponent backgroundComponent = new ParallaxBackgroundComponent(sprite, 1, 0.1f, GameBoardInfo.getInstance().getHeight() - Constants.SKY_HEIGHT_IN_PIXELS);
        entity.add(backgroundComponent);
        EntityManager.getInstance().addEntity(entity);

        Sprite sprite2 = new Sprite(ResourceManager.getTexture("skybackground2"));
        Entity entity2 = new Entity();
        ParallaxBackgroundComponent backgroundComponent2 = new ParallaxBackgroundComponent(sprite2, 2, 0.3f, GameBoardInfo.getInstance().getHeight() - Constants.SKY_HEIGHT_IN_PIXELS);
        entity2.add(backgroundComponent2);
        EntityManager.getInstance().addEntity(entity2);

        Sprite sprite3 = new Sprite(ResourceManager.getTexture("waterbackground1"));
        Entity entity3 = new Entity();
        ParallaxBackgroundComponent backgroundComponent3 = new ParallaxBackgroundComponent(sprite3, 3, 0.1f, 0f);
        entity3.add(backgroundComponent3);
        EntityManager.getInstance().addEntity(entity3);
    }

}
