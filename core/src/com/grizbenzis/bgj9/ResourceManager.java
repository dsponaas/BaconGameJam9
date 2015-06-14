package com.grizbenzis.bgj9;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private static Animation _subDestroyedAnimation;
    private static Animation _subDestroyedFlippedAnimation;

    private static TextureAtlas _sub2DestroyedAtlas;
    private static Animation _sub2DestroyedAnimation;
    private static Animation _sub2DestroyedFlippedAnimation;

    private static Music _gameMusic;
    private static Sound _playerShootingSound;
    private static Sound _playerDeathSound;
    private static Sound _explosionSound;

    public static void initialize() {
        _textures = new HashMap<String, Texture>();

        _textures.put("startButton", new Texture("badlogic.jpg"));
        _textures.put("player", new Texture("player.png"));
        _textures.put("enemy", new Texture("enemy.png"));
        _textures.put("enemy2", new Texture("enemy2.png"));
        _textures.put("bullet", new Texture("bullet.png"));
        _textures.put("explosion", new Texture("explosion.png"));
        _textures.put("largeexplosion", new Texture("largeexplosion.png"));
        _textures.put("enemybullet", new Texture("enemybullet.png"));
        _textures.put("playAgainButton", new Texture("playagainbutton.png"));
        _textures.put("skybackground1", new Texture("skybackground1.png"));
        _textures.put("skybackground2", new Texture("skybackground2.png"));
        _textures.put("waterbackground1", new Texture("waterbackground1.png"));
        _textures.put("powerupspd", new Texture("powerupspeed.png"));
        _textures.put("powerup1up", new Texture("powerup1up.png"));
        _textures.put("poweruppoints", new Texture("poweruppoints.png"));
        _textures.put("powerupexpl", new Texture("powerupexpl.png"));
        _textures.put("powerupspdsmall", new Texture("powerupspeedsmall.png"));
        _textures.put("poweruppointssmall", new Texture("poweruppointssmall.png"));
        _textures.put("powerupexplsmall", new Texture("powerupexplsmall.png"));
        _textures.put("splashscreen", new Texture("splashscreen.png"));
        _textures.put("gameover", new Texture("gameover.png"));

        createSubDestroyedAnimation();
        createSub2DestroyedAnimation();
        createSubDestroyedFlippedAnimation();
        createSub2DestroyedFlippedAnimation();

        _gameMusic = Gdx.audio.newMusic(Gdx.files.internal("test4_looping.ogg"));
        _gameMusic.setLooping( true );

        _playerShootingSound = Gdx.audio.newSound(Gdx.files.internal("playershooting.ogg"));
        _explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        _playerDeathSound = Gdx.audio.newSound(Gdx.files.internal("death.ogg"));
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

        _subDestroyedAnimation = new Animation(3f, frames);
    }

    public static void createSub2DestroyedAnimation() {
        _sub2DestroyedAtlas = new TextureAtlas("Sub2-Destroy.atlas");

        TextureRegion frame1 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 01");
        TextureRegion frame2 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 02");
        TextureRegion frame3 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 03");
        TextureRegion frame4 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 04");
        TextureRegion frame5 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 05");
        TextureRegion frame6 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 06");
        TextureRegion frame7 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 07");
        TextureRegion frame8 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 08");
        TextureRegion frame9 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 09");
        TextureRegion frame10 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 10");
        TextureRegion frame11 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 11");

        TextureRegion[] frames = new TextureRegion[] {
                frame1, frame2, frame3, frame4, frame5, frame6,
                frame7, frame8, frame9, frame10, frame11
        };

        _sub2DestroyedAnimation = new Animation(3f, frames);
    }

    public static void createSubDestroyedFlippedAnimation() {
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

        frame1.flip(true, false);
        frame2.flip(true, false);
        frame3.flip(true, false);
        frame4.flip(true, false);
        frame5.flip(true, false);
        frame6.flip(true, false);
        frame7.flip(true, false);
        frame8.flip(true, false);
        frame9.flip(true, false);
        frame10.flip(true, false);
        frame11.flip(true, false);

        TextureRegion[] frames = new TextureRegion[] {
                frame1, frame2, frame3, frame4, frame5, frame6,
                frame7, frame8, frame9, frame10, frame11
        };

        _subDestroyedFlippedAnimation = new Animation(3f, frames);
    }

    public static void createSub2DestroyedFlippedAnimation() {
        _sub2DestroyedAtlas = new TextureAtlas("Sub2-Destroy.atlas");

        TextureRegion frame1 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 01");
        TextureRegion frame2 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 02");
        TextureRegion frame3 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 03");
        TextureRegion frame4 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 04");
        TextureRegion frame5 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 05");
        TextureRegion frame6 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 06");
        TextureRegion frame7 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 07");
        TextureRegion frame8 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 08");
        TextureRegion frame9 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 09");
        TextureRegion frame10 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 10");
        TextureRegion frame11 = _sub2DestroyedAtlas.findRegion("sub2 destroyed 11");

        frame1.flip(true, false);
        frame2.flip(true, false);
        frame3.flip(true, false);
        frame4.flip(true, false);
        frame5.flip(true, false);
        frame6.flip(true, false);
        frame7.flip(true, false);
        frame8.flip(true, false);
        frame9.flip(true, false);
        frame10.flip(true, false);
        frame11.flip(true, false);

        TextureRegion[] frames = new TextureRegion[] {
                frame1, frame2, frame3, frame4, frame5, frame6,
                frame7, frame8, frame9, frame10, frame11
        };

        _sub2DestroyedFlippedAnimation = new Animation(3f, frames);
    }

    public static Animation getSubDestroyedAnimation() { return _subDestroyedAnimation; }
    public static Animation getSub2DestroyedAnimation() { return _sub2DestroyedAnimation; }
    public static Animation getSubDestroyedFlippedAnimation() { return _subDestroyedFlippedAnimation; }
    public static Animation getSub2DestroyedFlippedAnimation() { return _sub2DestroyedFlippedAnimation; }

    public static Music getGameMusic() {
        return _gameMusic;
    }

    public static Sound getPlayerShootingSound() {
        return _playerShootingSound;
    }

    public static Sound getExplosionSound() {
        return _explosionSound;
    }

    public static Sound getPlayerDeathSound() {
        return _playerDeathSound;
    }
}
