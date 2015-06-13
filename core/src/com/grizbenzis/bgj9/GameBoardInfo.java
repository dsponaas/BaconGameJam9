package com.grizbenzis.bgj9;

/**
 * Created by sponaas on 6/13/15.
 */
public class GameBoardInfo {

    private static GameBoardInfo _instance;

    private float _gameBoardWidth;
    public float getWidth()         { return _gameBoardWidth; }

    private float _gameBoardHeight;
    public float getHeight()        { return _gameBoardHeight; }

    private float _waterLevel;
    public float getWaterLevel()    { return _waterLevel; }

    private GameBoardInfo(float width, float height) {
        _gameBoardWidth = width;
        _gameBoardHeight = height;
        _waterLevel = _gameBoardHeight - Constants.SKY_HEIGHT_IN_PIXELS;
    }

    public static void initialize(float width, float height) {
        if(null != _instance) {
            _instance.dispose();
        }
        _instance = new GameBoardInfo(width, height);
    }

    public static GameBoardInfo getInstance()       { return _instance; }

    public void dispose() {
        // TODO: dont think we'll need this
    }

}
