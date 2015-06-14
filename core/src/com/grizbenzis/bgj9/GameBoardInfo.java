package com.grizbenzis.bgj9;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.grizbenzis.bgj9.actors.Actor;
import com.grizbenzis.bgj9.actors.EnemySub;
import com.grizbenzis.bgj9.actors.EnemyType;
import com.grizbenzis.bgj9.actors.Player;
import com.grizbenzis.bgj9.components.*;

import java.util.Random;

/**
 * Created by sponaas on 6/13/15.
 */
public class GameBoardInfo {

    private static GameBoardInfo _instance;
    private Random _rand;
    private float _enemySpawnTimer;
    private EnemyType _nextEnemyType;

    private float _gameBoardWidth;
    public float getWidth()                 { return _gameBoardWidth; }

    private float _gameBoardHeight;
    public float getHeight()                { return _gameBoardHeight; }

    private float _waterLevel;
    public float getWaterLevel()            { return _waterLevel; }

    private int _score;
    public int getScore()                   { return _score; }

    private int _level;
    public int getLevel()                   { return _level; }
    public void incrementLevel()            { ++_level; }

    private float _levelTimer;

    private int _lives;
    public int getLives()                   { return _lives; }
    public void decrementLives()            { --_lives; }
    public void incrementLives()            { ++_lives; }

    private Player _player;
    public Player getPlayer()               { return _player; }
    public void setPlayer(Player player)    { _player = player; }
    public PlayerDataComponent getPlayerData()      { return _playerDataComponents.get(_player.getEntity()); }

    private float _powerupTimer;
    private static final float POWERUP_TIMER_HACK = 200f;

    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);

    private GameBoardInfo(float width, float height) {
        _gameBoardWidth = width;
        _gameBoardHeight = height;
        _waterLevel = _gameBoardHeight - Constants.SKY_HEIGHT_IN_PIXELS;
        _enemySpawnTimer = getSpawnTimer();
        _nextEnemyType = EnemyType.Type1;
        _rand = new Random();
        _score = 0;
        _level = 1;
        _lives = Constants.NUM_LIVES;
        _levelTimer = Constants.LEVEL_TIME;
        _powerupTimer = POWERUP_TIMER_HACK;
    }

    public static void initialize(float width, float height) {
        if(null != _instance) {
            _instance.dispose();
        }
        _instance = new GameBoardInfo(width, height);
    }

    public void update() {
        _enemySpawnTimer -= (float)Time.time;
        _levelTimer -= (float)Time.time;
        _powerupTimer -= (float)Time.time;
        if(_enemySpawnTimer < 0f) {
            float speed = _rand.nextBoolean() ? 1f : -1f;
            float yPos = getRandomFloat(Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS,
                    _gameBoardHeight - Constants.SKY_HEIGHT_IN_PIXELS - Constants.MIN_DETONATION_DEPTH_IN_PIXELS);
            float xPos = speed > 0f ?
                    0f :
                    _gameBoardWidth;

            Entity enemyEntity = EnemySub.makeEnemyEntity(xPos, yPos, speed, _nextEnemyType);
            EntityManager.getInstance().addEntity(enemyEntity);
            EntityManager.getInstance().addActor(new EnemySub(enemyEntity));

            _enemySpawnTimer = getSpawnTimer();
            _nextEnemyType = _nextEnemyType == EnemyType.Type1 ? EnemyType.Type2 : EnemyType.Type1;
        }
        if(_levelTimer < 0f) {
            _levelTimer = Constants.LEVEL_TIME;
            incrementLevel();
        }
        if(_powerupTimer < 0f) {
            _powerupTimer = POWERUP_TIMER_HACK;
            spawnPowerupEntity();
        }
    }

    public static GameBoardInfo getInstance()       { return _instance; }

    public void dispose() {
        // TODO: dont think we'll need this
    }

    private float getRandomFloat(float start, float end) {
        return start + ((end - start) * _rand.nextFloat());
    }

    public void incrementScore(int val) {
        int factor = 1;
        PlayerDataComponent playerDataComponent = _playerDataComponents.get(getPlayer().getEntity());
        if(playerDataComponent.powerupTimePoints2x > 0f)
            factor = 2;
        _score += (val * factor);
    }

    private float getSpawnTimer() {
        float spawnTimer = Constants.BASE_ENEMY_SPAWN_TIMER;
        for(int i = 1; i < _level; ++i)
            spawnTimer *= Constants.LEVEL_ADVANCE_SPAWN_TIMER_MOD; // TODO: this is kind of terrible
        return spawnTimer;
    }

    public void spawnPowerupEntity() {
        Entity entity = new Entity();

        int type = _rand.nextInt(Constants.PowerupType.values().length);
        Gdx.app.log( Constants.LOG_TAG, "type:" + type);

        float xPos = getRandomFloat(0f + Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS, _gameBoardWidth - Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS);
        float yPos = getRandomFloat(0f + Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS, _gameBoardHeight - Constants.SKY_HEIGHT_IN_PIXELS - Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS);
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);

        Sprite sprite = null;
        if(type == Constants.PowerupType.EXPLOSION_UP.ordinal())
            sprite = new Sprite(ResourceManager.getTexture("powerupexpl"));
        else if(type == Constants.PowerupType.POINTS_2X.ordinal())
            sprite = new Sprite(ResourceManager.getTexture("poweruppoints"));
        else if(type == Constants.PowerupType.SPEED_UP.ordinal())
            sprite = new Sprite(ResourceManager.getTexture("powerupspd"));
        else if(type == Constants.PowerupType.EXTRA_LIFE.ordinal())
            sprite = new Sprite(ResourceManager.getTexture("powerup1up"));

        SpriteComponent spriteComponent = new SpriteComponent(sprite);
        BodyComponent bodyComponent = new BodyComponent(positionComponent, BodyFactory.getInstance().generate(entity, "powerup.json", new Vector2(xPos, yPos)));
        PowerupComponent powerupComponent = new PowerupComponent(Constants.PowerupType.fromInt(type));
        RenderComponent renderComponent = new RenderComponent(0);

        entity.add(positionComponent).add(spriteComponent).add(bodyComponent).add(powerupComponent).add(renderComponent);

        EntityManager.getInstance().addEntity(entity);
    }

}
