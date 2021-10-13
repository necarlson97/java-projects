import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WavOutput implements Runnable{
	
	private Thread wavOutputThread;
	private Random r = new Random();
	
	Clip clip;
	
	private Thought thought;
	
	public WavOutput(){
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		wavOutputThread = new Thread(this);
		wavOutputThread.start();
	}
	
	public void speak(Thought t){
		thought = t;
	}

	@Override
	public void run() {
		if(thought != null && !clip.isOpen()){
			File[] thoughtFolder = new File(thought.getName()).listFiles();
			int randomIndex = r.nextInt(thoughtFolder.length);
			File f = thoughtFolder[randomIndex];
			while(f.getName().equals(".DS_Store")){
				randomIndex = r.nextInt(thoughtFolder.length);
				f = thoughtFolder[randomIndex];
			}
			
			System.out.println(f + ", "+f.exists());
			
			try {
				AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
				System.out.println(aff+", "+AudioSystem.isFileTypeSupported(aff.getType()));
				clip.open(AudioSystem.getAudioInputStream(f));
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			
	        clip.start();
			while(clip.isRunning()) {
				try {
					wavOutputThread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			clip.close();
			thought = null;
			 
		} 
		else {
			try {
				wavOutputThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		run();
	}

}
