package utilities;


import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;

import java.io.File;

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
	public final static Clip beep = getClip("beep");
	public final static Clip pickup = getClip("pickup");

	public final static Clip[] clips = {bangLarge, bangMedium, bangSmall, explosion, beep, pickup};
	
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
			System.out.println(" ei vandal nema tolko indexi be: " + index);
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


	// Custom methods playing a particular sound
	// Please add your own methods below

	public static void asteroids() {
		play(bangMedium);
	}


}
