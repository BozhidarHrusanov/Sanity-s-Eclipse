package utilities;

import java.io.IOException;
import java.util.Random;

import org.newdawn.easyogg.OggClip;

public class MusicManager {
	private OggClip backgroundMusic;

	public MusicManager() {
			try {
				this.backgroundMusic = new OggClip("music/Spag Heddy - The Master.ogg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}

	public void loopBackgroundMusic() {
		this.backgroundMusic.loop();
		this.backgroundMusic.setGain(0.75f);
		this.backgroundMusic.setBalance(-1.0f);
	}

	public void stopBackgroundMusic() {
		this.backgroundMusic.stop();
	}

}
