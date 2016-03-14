package com.bensach.saul;

import com.badlogic.gdx.Game;
import com.bensach.saul.menu.*;

public class GameStart extends Game {

	private LoginMenu loginMenu;
	private MainMenu mainMenu;
	private OptionsMenu optionsMenu;
	private RegisterMenu registerMenu;
	private SplashScreen splashScreen;

	@Override
	public void create() {
		splashScreen = new SplashScreen(this);
		loginMenu = new LoginMenu(this);
		mainMenu = new MainMenu(this);
		optionsMenu = new OptionsMenu(this);
		registerMenu = new RegisterMenu(this);

		setScreen(loginMenu);
	}

}