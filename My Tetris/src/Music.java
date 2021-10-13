import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	
	String theme = "Tetris_Theme.wav";
	String bonus = "Korobeiniki.wav";
	String click = "click.wav";
	
	Clip currClip;
	
	int themeCount = 0;
	
	public Music() { 
		playTheme();
	}
	
	public void mute() {
		currClip.stop();
	}
	
	public void playTheme() {
		currClip = play(theme);
		currClip.addLineListener(listener);
		themeCount ++;
	}
	
	public void playBonus() {
		currClip = play(bonus);
		currClip.addLineListener(listener);
		themeCount ++;
	}
	
	public void playClick() {
		play(click);
	}
	
	Clip play (String song) {
		Clip clip = null;
		try {
			AudioInputStream audioInputStream = 
					AudioSystem.getAudioInputStream(new File(song));
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		} catch(UnsupportedAudioFileException e) {
			System.out.println("Unsuported audio type: "+song);
		} catch (IOException e) {
			System.out.println("Could not find: "+song);
		} catch (LineUnavailableException e) {
			System.out.println("Audio line unavailable for: "+song);
		} 
		
		return clip;

	}
	
	LineListener listener = new LineListener() {
        public void update(LineEvent event) {
            if (event.getType() == Type.CLOSE) {
            		if(themeCount > 2 && themeCount < 4) playBonus();
            		else playTheme();
            }
        }
    };
    
}
