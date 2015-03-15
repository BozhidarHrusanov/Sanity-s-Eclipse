package controllers;

import entities.AlienShip;
import game.Action;
import game.Game;

public class SeekNLaser implements Controller {

	  Action action = new Action();
	  public Action action(Game game) {
	    action.shoot = true; 
	    action.turn = 1; 
	    return action; 
	}
	@Override
	public Action action() {
		action.shoot = true; 
	    action.turn = 1; 
		return this.action;
	} 
	@Override
	public void update(AlienShip alienShip) {
		// TODO Auto-generated method stub

	}

}
