package utilities;

// change package name to fit your own package structure!

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;

import java.io.File;

// SoundManager for Asteroids

public class SoundManager {

	static int nBullet = 0;
	static boolean thrusting = false;

	// this may need modifying
	final static String path = "sounds/";

	// note: having too many clips open may cause
	// "LineUnavailableException: No Free Voices"

	public final static Clip bangLarge = getClip("bangLarge");
	public final static Clip bangMedium = getClip("bangMedium");
	public final static Clip bangSmall = getClip("bangSmall");
	public final static Clip explosion = getClip("explosion");
	public final static Clip beat2 = getClip("beat2");
	public final static Clip extraShip = getClip("extraShip");
	public final static Clip fire = getClip("fire");
	public final static Clip saucerBig = getClip("saucerBig");
	public final static Clip saucerSmall = getClip("saucerSmall");
	public final static Clip thrust = getClip("thrust");

	public final static Clip[] clips = {bangLarge, bangMedium, bangSmall, explosion, beat2, 
		extraShip, fire, saucerBig, saucerSmall, thrust };
	
	public static void initSoundManager(){
		for (int j = 0; j <= 3; j++) {
			FloatControl gainControl = (FloatControl) clips[j]
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f);
		}
		
	}

	// methods which do not modify any fields
 
	public static void playByIndex(int index) {
		if (index > clips.length) {
			System.out.println(" ei vandal nema tolko indexi be");
			return;
		}
		clips[index].setFramePosition(0);
		clips[index].start();
	}

	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}

	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	// methods which modify (static) fields

	public static void startThrust() {
		if (!thrusting) {
			thrust.loop(-1);
			thrusting = true;
		}
	}

	public static void stopThrust() {
		thrust.loop(0);
		thrusting = false;
	}

	// Custom methods playing a particular sound
	// Please add your own methods below

	public static void asteroids() {
		play(bangMedium);
	}
	public static void extraShip() {
		play(extraShip);
	}

}
