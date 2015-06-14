package com.grizbenzis.bgj9;

import com.badlogic.gdx.Game;
import com.grizbenzis.bgj9.screens.SplashScreen;

public class bgj9 extends Game {
//	SpriteBatch batch;
//	Texture img;

	public static bgj9 game;

	@Override
	public void create() {
		game = this;
		ResourceManager.initialize();
		setScreen(new SplashScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

/*
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
*/
}
