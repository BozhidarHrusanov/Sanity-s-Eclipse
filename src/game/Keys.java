package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controllers.Controller;
import entities.AlienShip;
import entities.BaseShip;
import entities.Ship;

public class Keys extends KeyAdapter implements Controller {
	public Action action;

	public Keys() {
		this.action = new Action();
	}

	public Action action() {
		// this is defined to comply with the standard interface
		return this.action;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			this.action.thrust = -1;
			Ship.setThrusting(true);
			break;
		case KeyEvent.VK_DOWN:
			this.action.thrust = 1;
			Ship.setThrusting(true);
			break;
		case KeyEvent.VK_LEFT:
			this.action.turn = -1;
			break;
		case KeyEvent.VK_RIGHT:
			this.action.turn = 1;
			break;
		case KeyEvent.VK_SPACE:
			this.action.shoot = true;
			break;
		case KeyEvent.VK_ESCAPE:
			Game.gameRunning = false;
			break;
		case KeyEvent.VK_Z:
			Ship.shieldOn = true;
			if(Ship.currentShield < 1.0){
				  Ship.shieldOn=false;
				  return;
			  }
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			Ship.setThrusting(false);
			this.action.thrust = 0;
			break;
		case KeyEvent.VK_DOWN:
			Ship.setThrusting(false);
			this.action.thrust = 0;
			break;
		case KeyEvent.VK_LEFT:
			this.action.turn = 0;
			break;
		case KeyEvent.VK_RIGHT:
			this.action.turn = 0;
			break;
		case KeyEvent.VK_SPACE:
			this.action.shoot = false;
			break;
		case KeyEvent.VK_ESCAPE:
			Game.gameRunning = false;
			break;
		case KeyEvent.VK_Z:
			Ship.shieldOn = false;
			break;
		}
	}

	@Override
	public void update(AlienShip alienShip) {
		// not used for the player controller
	}
}