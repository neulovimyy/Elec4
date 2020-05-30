package com.elec4.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tboi.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.setFromDisplayMode(displayMode);
		config.height = 600;
		config.width = 800;
		//config.addIcon("resources/maps/mapres/knight_m_hit_anim_f0.png", Files.FileType.Internal);
		new LwjglApplication(new MainGame(), config);
	}
}











