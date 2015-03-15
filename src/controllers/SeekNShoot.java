package controllers;

import utilities.Vector2D;
import entities.AlienShip;
import game.Action;
import game.Game;

public class SeekNShoot implements Controller {
	Action action = new Action();
	private int timer;

	@Override
	public Action action() {
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
		
		if (3.1 <= Math.abs(angleToTarget) && distance > 500){
			this.action.thrust = -1;
		}
		if (distance > 200 && distance < 500){
			this.action.thrust = 0;
		}
		if (distance < 200){
			this.action.thrust = 1;
		}
		
		if(3.1 <= Math.abs(angleToTarget) && distance < 700){
			if (this.timer > 0) {
				this.action.shoot = false;
				this.timer--;
			} else {
				this.action.shoot = true;
				this.timer = 20;
			}
		}
	}

	private void tickTimer(double angleToTarget) {
		this.action.turn = 0;
		if (angleToTarget > 0)
			this.action.turn = -1;
			// timer--;
		else if (angleToTarget < 0) {
			this.action.turn = 1;
			// timer++;
		}
	}
	
}