package com.grizbenzis.bgj9;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by sponaas on 6/12/15.
 */
public class ResourceManager {

    private static HashMap<String, Texture> _textures;

    public static void initialize() {
        _textures = new HashMap<String, Texture>();

        _textures.put("startButton", new Texture("badlogic.jpg"));
        _textures.put("player", new Texture("player.png"));
        _textures.put("enemy", new Texture("enemy.png"));
        _textures.put("bullet", new Texture("bullet.png"));
        _textures.put("explosion", new Texture("explosion.png"));
    }

    public static Texture getTexture(String name) {
        return _textures.get(name);
    }

}
