package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import utilities.SoundManager;


public class MenuKeys extends KeyAdapter{
	
	public MainMenu mainMenu;

	public MenuKeys(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	@SuppressWarnings("incomplete-switch")
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			SoundManager.playByIndex(6);
			mainMenu.goUp();
			break;
		case KeyEvent.VK_DOWN:
			SoundManager.playByIndex(6);
			mainMenu.goDown();
			break;
		case KeyEvent.VK_SPACE:
			SoundManager.playByIndex(6);
			mainMenu.select();
			break;
		case KeyEvent.VK_ENTER:
			SoundManager.playByIndex(6);
			mainMenu.select();
			break;
		case KeyEvent.VK_ESCAPE:
			Game.gameRunning = false;
			break;
		}
	}

}
