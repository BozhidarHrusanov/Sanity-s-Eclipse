package controllers;

import entities.AlienShip;
import game.Action;
import game.Game;

public class RotateNShoot implements Controller {
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
	
	public void update(AlienShip alien) {
	//empty on purpose lel
	}
	

}