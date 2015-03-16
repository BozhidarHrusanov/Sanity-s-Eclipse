package game;

import game.StateManager.States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MainMenu {
	
	private final String[] verticalOptions = { "Play endless", "Toggle music", "Exit" };
	
	private int currentOption = 0;
	
	public void update(){
		
	}

	public void goUp() {
		if (currentOption == 0){
			currentOption = verticalOptions.length-1;
		}else{
			currentOption--;
		}
	}
	
	public void goDown() {
		if (currentOption == verticalOptions.length-1){
			currentOption = 0;
		}else{
			currentOption++;
		}
	}

	public void select() {
		String choise = verticalOptions[currentOption];
		switch(choise){
		case "Play endless":
			StateManager.getStateManager().setState(States.ENDLESS);
			break;
		case "Toggle music":
			if (Game.musicManager.isPlayingBackgroundMusic()){
				Game.musicManager.stopBackgroundMusic();
			}else{
				Game.musicManager.loopBackgroundMusic();
			}
			break;
		case "Exit":
			System.exit(0);
			break;
		}
		
	}

	public void drawMenu(Graphics2D g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		for (int i = 0; i < verticalOptions.length; i++) {
			if (i == currentOption) {
				g.setColor(new Color(0, 255, 0));
			} else {
				g.setColor(new Color(255, 0, 0));
			}
			g.drawString(verticalOptions[i], (Constants.FRAME_WIDTH
					- g.getFontMetrics().stringWidth(verticalOptions[i])) / 2,
					Constants.FRAME_HEIGHT / 2 - 3*50 + i*100);
		}
	}
	

}
