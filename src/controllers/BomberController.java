package controllers;

import entities.AlienShip;
import game.Action;
import game.Game;
import utilities.Vector2D;

public class BomberController implements Controller{
	Action action = new Action();
	private int timer;

	@Override
	public Action action() {
		action.shoot = true;
		return this.action;
	}

	public void update(AlienShip alien) {

		double playerShipRelativeX = Game.getShip().getX() - alien.getX();
		double playerShipRelativeY = Game.getShip().getY() - alien.getY();
		double distance = Game.calculateDistance(playerShipRelativeX, playerShipRelativeY);

		double angleAlienDirection = alien.getD().theta();

		Vector2D toShip = new Vector2D(playerShipRelativeX, playerShipRelativeY);
		toShip.rotate(-angleAlienDirection);
		
		double angleToTarget = toShip.theta();
		
		tickTimer(angleToTarget);
		
		if (3.11 <= Math.abs(angleToTarget)){
			this.action.thrust = -1;
		}
	}

	private void tickTimer(double angleToTarget) {
		this.action.turn = 0;
		if (angleToTarget > 0)
			this.action.turn = -1;
		else if (angleToTarget < 0) {
			this.action.turn = 1;
		}
	}

}
