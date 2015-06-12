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
    }

    public static Texture getTexture(String name) {
        return _textures.get(name);
    }

}
