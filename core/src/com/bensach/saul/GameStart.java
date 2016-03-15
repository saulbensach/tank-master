package com.bensach.saul;

import com.badlogic.gdx.Game;
import com.bensach.saul.menu.*;

public class GameStart extends Game {

	public LoginMenu loginMenu;
	public MainMenu mainMenu;
	public OptionsMenu optionsMenu;
	public RegisterMenu registerMenu;
	public SplashScreen splashScreen;

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