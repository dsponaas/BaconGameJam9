package com.grizbenzis.bgj9.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.grizbenzis.bgj9.bgj9;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Depth Charge Game";
		config.width = 1024;
		config.height = 768;
		config.resizable = false;
		new LwjglApplication(new bgj9(), config);
	}
}
