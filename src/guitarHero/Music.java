package guitarHero;

import java.applet.Applet;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music extends Applet{
	
	Clip audioClip;
	
	public Music() {
	}
	
	public void playMusic() {
		try {
			audioClip = AudioSystem.getClip();
			URL soundURL = Music.class.getClassLoader().getResource("guitarHero/Supersonic.wav");
			audioClip.open(AudioSystem.getAudioInputStream(soundURL));
			if (!audioClip.isActive()) {
				audioClip.start();
			}
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			System.err.println("Error - playing music.");
			e.printStackTrace();
		}
	}
	
	public void stopMusic() {
		this.audioClip.stop();
	}

}
