package game;

import game.StateManager.States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import utilities.ImageManager;

public class MainMenu {
	private double slideProgress;
	private double slideModifier = 0.02;

	private final String[] options = { "play",
			"music", "quit" };
	
	private int currentOption = 0;
	
	public void update(){
		
	}

	public void goUp() {
		if (currentOption == 0){
			currentOption = options.length-1;
		}else{
			currentOption--;
		}
	}
	
	public void goDown() {
		if (currentOption == options.length-1){
			currentOption = 0;
		}else{
			currentOption++;
		}
	}

	public void select() {
		String choice = options[currentOption];
		switch(choice){
		case "play":
			StateManager.getStateManager().setState(States.PLAY);
			break;
		case "music":
			if (Game.musicManager.isPlayingBackgroundMusic() && !(Game.musicManager.isPaused)){
				Game.musicManager.isPaused = true;
				Game.musicManager.stopBackgroundMusic();	
			}else{
				Game.musicManager.isPaused=false;
				Game.musicManager.playBackgroundMusic();
			}
			break;
		case "quit":
			System.exit(0);
			break;
		}
		
	}
	
	public void drawMenuBackground(Graphics2D g){
		double imgWidth = ImageManager.getImage("mainMenu").getWidth(null);
		double imgHeight = ImageManager.getImage("mainMenu").getHeight(null);
		
		double distance = imgWidth - Constants.FRAME_WIDTH;
		
		slideProgress += slideModifier;
		if(slideProgress >= 100 || slideProgress <= 0){
			slideModifier *= -1;
		}
		
		g.drawImage(ImageManager.getImage("mainMenu"),0 , 0,
				Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, (int)(slideProgress/100*distance),
				0, (int)(slideProgress/100*distance + Constants.FRAME_WIDTH),
				(int)imgHeight, null);
		
	}

	public void drawMenu(Graphics2D g) {
		g.drawImage(ImageManager.getImage("logo"),(int) (Constants.FRAME_WIDTH/2 - ImageManager.getImage("logo").getWidth(null)/2), Constants.FRAME_HEIGHT/9, null);
		int offset = Constants.FRAME_HEIGHT/9;
		for (int i = 0; i < options.length; i++) {
			int imgWidth = ImageManager.getImage(options[i]).getWidth(null);
			
			if (i == currentOption) {
				g.drawImage(ImageManager.getImage("selector"),
						(int) (Constants.FRAME_WIDTH/2 - imgWidth/2) - (ImageManager.getImage("selector").getWidth(null) + 30),
						Constants.FRAME_HEIGHT*2/5 + i*offset, null);
			}
			g.drawImage(ImageManager.getImage(options[i]),(int) (Constants.FRAME_WIDTH/2 - imgWidth/2), Constants.FRAME_HEIGHT*2/5 + i*offset, null);
		}
		
	}
	

}
