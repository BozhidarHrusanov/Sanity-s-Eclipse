package game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controllers.BomberController;
import controllers.ChargeController;
import controllers.RotateNShoot;
import controllers.SeekNShoot;
import entities.AlienBomber;
import entities.AlienScatter;
import entities.AlienShip;
import entities.Asteroid;
import entities.GameObject;
import entities.Powerup;
import entities.Ship;
import game.StateManager.States;
import utilities.AnimationManager;
import utilities.JEasyFrameFull;
import utilities.MusicManager;
import utilities.SoundManager;
import utilities.Vector2D;

/*  ********************** TO DO *********************** 

- highscore text file, gotta encrypt it to prevent h4x0rz
- powerup pickup sfx
- levelManager: finished lvl -> next lvl; save the lvlManager state
- game ends (endless mode), when u start it again the final score screen is still present !
    ****************************************************
    */

public class Game {
	
	public List<Asteroid> asteroids = new ArrayList<Asteroid>();
	public static CopyOnWriteArrayList<GameObject> objects = new CopyOnWriteArrayList<GameObject>();
	public Keys ctrl;
	public MenuKeys menuKeys;
	public static MainMenu mainMenu = new MainMenu();
	public static Ship ship;
	public static int score, prevScore;
	public static int lives = 3;
	private int currentWave = 1;
	public static boolean gameRunning = true;
	public static MusicManager musicManager = new MusicManager();

	public Game() {

		this.ctrl = new Keys();
		this.menuKeys = new MenuKeys(mainMenu);
		musicManager.loopBackgroundMusic();
	}

	public static void main(String[] args) throws Exception {
		Game game = new Game();
		View view = new View(game);
		JEasyFrameFull jFrame = new JEasyFrameFull(view);
		SoundManager.initSoundManager();

		while (gameRunning) {			
			jFrame.addKeyListener(game.menuKeys);
			while (StateManager.getStateManager().getState() == States.MENU && gameRunning) {
				mainMenu.update();
				view.repaint();
				Thread.sleep(Constants.DELAY);
			}
			jFrame.removeKeyListener(game.menuKeys);
			
			jFrame.addKeyListener(game.ctrl);
			game.spawnShip();
			game.generateEnemies();
			lives=3;
			score=0;
			while (StateManager.getStateManager().getState() == States.PLAY && gameRunning) {
				game.checkListContents();
				game.update();
				view.repaint();
				Thread.sleep(Constants.DELAY);
			}
			
			jFrame.removeKeyListener(game.ctrl);
		}
		System.exit(0);
	}
	
	private void spawnShip() {
		// dispose of old entities
		objects = new CopyOnWriteArrayList<>();
		asteroids = new ArrayList<Asteroid>();
		ship = null;
		
		ship = new Ship(this.ctrl);
		objects.add(ship);
	}
	
	private void checkListContents(){
		boolean running=false;
		for (GameObject obj : objects) {
			if((obj instanceof AlienShip) || (obj instanceof Asteroid)){
				running = true;
			}
		}
		if(!running){
			generateEnemies();
			ship.giveInvulnerability();
			running=false;
		}
	}
	
	private void generateEnemies() {
		
		// initialize new entities
		for (int i = 0; i < Constants.N_INITIAL_ASTEROIDS; i++) {
			this.asteroids.add(Asteroid.makeRandomAsteroid());
		}
		objects.addAll(this.asteroids);

		double difficulty = 0;
		double maxDifficulty = 1 + currentWave;
		currentWave++;

		while (difficulty <= maxDifficulty) {

			if (Math.random() < (double) 25 / 100) {
				createAlienShip("alienShip1");
				difficulty += 3;
			} else if (Math.random() < (double) 50 / 100) {
				createAlienShip("alienShip2");
				difficulty += 1;
			} else if (Math.random() < (double) 60 / 100) {
				createAlienShip("alienShip3");
				difficulty += 2;
			} else if (Math.random() < (double) 80 / 100) {
				createAlienShip("alienShip4");
				difficulty += 5;
			} else {
				createAlienShip("alienShip5");
				difficulty += 2;
			}
		}
	}

	public static void addScore(int addition) {
		//System.out.println("score: " + score);
		if (!(ship.getInvulnerabilityTimer() > 0)) {
			score += addition;
		}
		//System.out.println("new score: " + score);
		//System.out.println("+++");
	}

	public static void addLives() {
		if ((score % Constants.GAIN_LIVES_THRESHOLD == 0) && (score != prevScore)) {
			prevScore = score;
			incrementLives();
		}
	}

	public static void removeLives() {
		lives--;
	}
	
	public static void incrementLives(){
		lives++;
		if (lives > Constants.MAX_LIVES){
			lives = Constants.MAX_LIVES;
		}
	}

	private void update() {
		
		AnimationManager.updateAnimations();
		for (int i = 0; i < Game.objects.size(); i++) {
			for (int j = i + 1; j < Game.objects.size(); j++) {
				Game.objects.get(i).collisionHandling(Game.objects.get(j));
			}
		}
		addLives();
		List<GameObject> alive = new ArrayList<GameObject>();
		for (GameObject object : Game.objects) {
			object.update(alive);
			if (!object.isDead())
				alive.add(object);
		}
		synchronized (Game.class) {
			Game.objects.clear();
			Game.objects.addAll(alive);
		}
	}
	
	public static void spawnPowerup(Vector2D s) {
		if (Math.random() < 0.33) {
			final String[] powerupNames = { "upgrWeapon", "bonusLife",
					"invulnerabilityShield", "rapidFire", "bonusVelocity",
					"scatter", "coin" };
			int powerupIndex = (int) Math.round(Math.random()
					* (powerupNames.length - 1));
			Vector2D randomXYVelocity = new Vector2D(modifyVelocity(-60
					+ Math.random() * 120), modifyVelocity(-40 + Math.random()
					* 80));
			Game.objects.add(new Powerup(new Vector2D(s), randomXYVelocity, 30.0,
					powerupNames[powerupIndex]));
		}
	}
	
	public void createAlienShip(String type) {
		Vector2D position = new Vector2D(Math.random() * Constants.FRAME_WIDTH,
				Math.random() * Constants.FRAME_HEIGHT * 3 / 4);
		
		switch (type) {
		case "alienShip1":
			objects.add(new AlienShip(position, new Vector2D(0.5, 0.5), 40, type,
					new SeekNShoot(), 3, "redBullet", 150));
			break;
		case "alienShip2":
			objects.add(new AlienShip(position, new Vector2D(0.65, 0.65), 35, type,
					new ChargeController(), 1, "redBullet", 100));
			break;
		case "alienShip3":
			objects.add(new AlienBomber(position, new Vector2D(0.5, 0.5), 40, type,
					new BomberController(), 5, "redBullet"));
			break;
		case "alienShip4":
			AlienShip alien = new AlienShip(position, new Vector2D(0.5, 0.5), 40, type,
					new SeekNShoot(), 12, "greenBullet", 300);
			alien.setWeaponGrade(3);
			objects.add(alien);
			break;
		case "alienShip5":
			objects.add(new AlienScatter(position, new Vector2D(0.5, 0.5), 50, type,
					new RotateNShoot(), 7, "purpleBullet"));
			break;
		default:
			System.out.println("createAlienShip input type is not valid!");
		}

	}
	
	public void createMultipleShips(int number, String type){
		for (;number > 0; number--){
			createAlienShip(type);
		}
	}
	
	public static double modifyVelocity(double vel) {
		if (vel < 0) {
			vel -= 50;
		} else {
			vel += 50;
		}
		return vel;
	}

	public static Ship getShip() {
		return ship;
	}
	
	public static MainMenu getMainMenu(){
		return mainMenu;
	}
	
	public static double calculateDistance(double relativeX, double relativeY){
		return Math.sqrt(Math.pow(relativeX,2) + Math.pow(relativeY, 2));
	}
}