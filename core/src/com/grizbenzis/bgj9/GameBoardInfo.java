package com.grizbenzis.bgj9;

import com.badlogic.ashley.core.Entity;
import com.grizbenzis.bgj9.actors.Actor;
import com.grizbenzis.bgj9.actors.EnemySub;
import com.grizbenzis.bgj9.actors.Player;

import java.util.Random;

/**
 * Created by sponaas on 6/13/15.
 */
public class GameBoardInfo {

    private final float ENEMY_SPAWN_TIMER_HACK = 300f;

    private static GameBoardInfo _instance;
    private Random _rand;
    private float _enemySpawnTimer;

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

    private int _lives;
    public int getLives()                   { return _lives; }
    public void decrementLives()            { _lives -= 1; }
    public void incrementLives()            { _lives += 1; }

    private Player _player;
    public Player getPlayer()               { return _player; }
    public void setPlayer(Player player)    { _player = player; }

    private GameBoardInfo(float width, float height) {
        _gameBoardWidth = width;
        _gameBoardHeight = height;
        _waterLevel = _gameBoardHeight - Constants.SKY_HEIGHT_IN_PIXELS;
        _enemySpawnTimer = ENEMY_SPAWN_TIMER_HACK;
        _rand = new Random();
        _score = 0;
        _level = 1;
        _lives = 2;
    }

    public static void initialize(float width, float height) {
        if(null != _instance) {
            _instance.dispose();
        }
        _instance = new GameBoardInfo(width, height);
    }

    public void update() {
        _enemySpawnTimer -= (float)Time.time;
        if(_enemySpawnTimer < 0f) {
            float speed = _rand.nextBoolean() ? 1f : -1f;
            float yPos = getRandomFloat(Constants.FLOOR_DETONATION_BUFFER_IN_PIXELS,
                    _gameBoardHeight - Constants.SKY_HEIGHT_IN_PIXELS - Constants.MIN_DETONATION_DEPTH_IN_PIXELS);
            float xPos = speed > 0f ?
                    0f :
                    _gameBoardWidth;

            Entity enemyEntity = EnemySub.makeEnemyEntity(xPos, yPos, speed);
            EntityManager.getInstance().addEntity(enemyEntity);
            EntityManager.getInstance().addActor(new EnemySub(enemyEntity));

            _enemySpawnTimer = ENEMY_SPAWN_TIMER_HACK;
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
        _score += val;
    }

}
