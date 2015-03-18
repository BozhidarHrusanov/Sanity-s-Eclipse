package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JComponent;

import entities.GameObject;
import game.StateManager.States;
import utilities.AnimationManager;
import utilities.ImageManager;

public class View extends JComponent {
	// background colour
	private Game game;
	AffineTransform bgTransf;
	Image background;

	public View(Game game) {
	    this.game = game;
	    
	    //background
	    try {
			ImageManager.initImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    this.background = ImageManager.getImage("backgr");
	    double imWidth = this.background.getWidth(null); 
	    double imHeight = this.background.getHeight(null); 
	    double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 : 
	                                Constants.FRAME_WIDTH/imWidth); 
	    double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 : 
	                                Constants.FRAME_HEIGHT/imHeight);
	    this.bgTransf = new AffineTransform(); 
	    this.bgTransf.scale(stretchx, stretchy);
	  }

	@Override
	public void paintComponent(Graphics g0) {
		Graphics2D g = (Graphics2D) g0;
		
		if (StateManager.getStateManager().getState() == States.MENU) {
			game.getMainMenu().drawMenuBackground(g);
			game.getMainMenu().drawMenu(g);
		}
		
		if (StateManager.getStateManager().getState() == States.PLAY) {
			g.drawImage(this.background, this.bgTransf,null); 
			synchronized (Game.class) {
				for (GameObject object : this.game.objects) {
					object.draw(g);
				}
				AnimationManager.drawAnimations(g);
			}

			drawHealthIndicator(g);
			drawShieldBar(g);
			
			if (Game.lives <= 0) {
				g.drawImage(
						ImageManager.getImage("gameover"),
						(Constants.FRAME_WIDTH / 2)
								- ImageManager.getImage("gameover").getWidth(
										null) / 2,
						Constants.FRAME_HEIGHT * 1 / 4, null);
				g.drawImage(
						ImageManager.getImage("finalscore"),
						(Constants.FRAME_WIDTH / 2)
								- ImageManager.getImage("finalscore").getWidth(
										null) / 2,
						Constants.FRAME_HEIGHT * 1 / 2, null);
				drawScoreDigits(g);
			} else {
				drawScoreInGame(g);
			}
		}
	}

	private void drawHealthIndicator(Graphics2D g) {
		for (int i = 0; i < game.lives; i++){
			g.drawImage(ImageManager.getImage("retroheart"), 20 + 55*i, 20,
					ImageManager.getImage("retroheart").getWidth(null)*3,
					ImageManager.getImage("retroheart").getHeight(null)*3, null);
		}
	}

	private void drawScoreDigits(Graphics2D g) {
		int digitWidth = ImageManager.getImage("numbers").getWidth(null)/10;
		String scoreText = Integer.toString(Game.score);
		int numbersX = Constants.FRAME_WIDTH/2 - (scoreText.length()*digitWidth/2);
		for (int i = 0; i < scoreText.length(); i++) {
			int currentDigit = Integer.parseInt( Character.toString( scoreText.charAt(i)));
			g.drawImage(ImageManager.getImage("numbers"),
					numbersX + i*digitWidth,
					Constants.FRAME_HEIGHT * 2/3,
					numbersX + i*digitWidth + digitWidth,
					Constants.FRAME_HEIGHT * 2/3 + digitWidth,
					currentDigit*digitWidth+5, 0,
					(currentDigit+1)*digitWidth-10, digitWidth, null);
		}
	}
	
	private void drawScoreInGame(Graphics2D g) {
		g.drawImage(ImageManager.getImage("ingameScore"), 10,Constants.FRAME_HEIGHT-50, null);
		int digitWidth = ImageManager.getImage("ingameDigits").getWidth(null)/10;
		String scoreText = Integer.toString(Game.score);
		for (int i = 0; i < scoreText.length(); i++) {
			int currentDigit = Integer.parseInt( Character.toString( scoreText.charAt(i)));
			g.drawImage(ImageManager.getImage("ingameDigits"),
					160 + i*digitWidth,
					Constants.FRAME_HEIGHT -50,
					160 + i*digitWidth + digitWidth,
					Constants.FRAME_HEIGHT -50 + digitWidth,
					currentDigit*digitWidth, 0,
					(currentDigit+1)*digitWidth, digitWidth, null);
		}
	}
	
	
	private void drawShieldBar(Graphics2D g){
		if(Game.lives > 0){
			int barWidth = ImageManager.getImage("barFrame").getWidth(null);
			int barHeight = ImageManager.getImage("barFrame").getHeight(null);	
			int barFillerWidth = ImageManager.getImage("barFiller").getWidth(null);
			int barFillerHeight = ImageManager.getImage("barFiller").getHeight(null);
			
			
			g.drawImage(ImageManager.getImage("barFrame"),
				Constants.FRAME_WIDTH/2 - barWidth/2, Constants.FRAME_HEIGHT - (barHeight+5),
				Constants.FRAME_WIDTH/2 + barWidth/2, Constants.FRAME_HEIGHT - 5,
				0, 0, barWidth, barHeight, null);
			
			double currentShield=0;
			double maxShield=0;
			if (!(Game.getShip() == null)){
				currentShield = barFillerWidth*Game.getShip().currentShield;
				maxShield = Game.getShip().maxShield;
			}
			
			g.drawImage(ImageManager.getImage("barFiller"),
				Constants.FRAME_WIDTH/2 - barFillerWidth/2, Constants.FRAME_HEIGHT - (barFillerHeight+16),
				Constants.FRAME_WIDTH/2 - barFillerWidth/2 + (int)(currentShield/maxShield),
				Constants.FRAME_HEIGHT - 16,
				0, 0,
				(int)(currentShield/maxShield), barFillerHeight, null);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return Constants.FRAME_SIZE;
	}
}
