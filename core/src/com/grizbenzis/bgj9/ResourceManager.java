package com.grizbenzis.bgj9;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Created by sponaas on 6/12/15.
 */
public class ResourceManager {

    private static HashMap<String, Texture> _textures;

    private static TextureAtlas _subDestroyedAtlas;
    private static Animation _subDesroyedAnimation;

    public static void initialize() {
        _textures = new HashMap<String, Texture>();

        _textures.put("startButton", new Texture("badlogic.jpg"));
        _textures.put("player", new Texture("player.png"));
        _textures.put("enemy", new Texture("enemy.png"));
        _textures.put("bullet", new Texture("bullet.png"));
        _textures.put("explosion", new Texture("explosion.png"));
        _textures.put("enemybullet", new Texture("enemybullet.png"));

        createSubDestroyedAnimation();
    }

    public static Texture getTexture(String name) {
        return _textures.get(name);
    }

    public static void createSubDestroyedAnimation() {
        _subDestroyedAtlas = new TextureAtlas("Sub-Destroy.atlas");

        TextureRegion frame1 = _subDestroyedAtlas.findRegion("sub destroyed 01");
        TextureRegion frame2 = _subDestroyedAtlas.findRegion("sub destroyed 02");
        TextureRegion frame3 = _subDestroyedAtlas.findRegion("sub destroyed 03");
        TextureRegion frame4 = _subDestroyedAtlas.findRegion("sub destroyed 04");
        TextureRegion frame5 = _subDestroyedAtlas.findRegion("sub destroyed 05");
        TextureRegion frame6 = _subDestroyedAtlas.findRegion("sub destroyed 06");
        TextureRegion frame7 = _subDestroyedAtlas.findRegion("sub destroyed 07");
        TextureRegion frame8 = _subDestroyedAtlas.findRegion("sub destroyed 08");
        TextureRegion frame9 = _subDestroyedAtlas.findRegion("sub destroyed 09");
        TextureRegion frame10 = _subDestroyedAtlas.findRegion("sub destroyed 10");
        TextureRegion frame11 = _subDestroyedAtlas.findRegion("sub destroyed 11");

        TextureRegion[] frames = new TextureRegion[] {
                                                        frame1, frame2, frame3, frame4, frame5, frame6,
                                                        frame7, frame8, frame9, frame10, frame11
                                                    };

        _subDesroyedAnimation = new Animation(3f, frames);
    }

    public static Animation getSubDestroyedAnimation() { return _subDesroyedAnimation; }
}
