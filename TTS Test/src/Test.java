import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Test {
	
	Random rand = new Random();
	
	String speaker = "kevin";
	Voice voice;
	
	String[] dict;
	String[] nato;
	
	public static void main(String[] args) {
		new Test();
	}
	
	public Test() {
		dict = arrayFromTxt("dict");
		nato = arrayFromTxt("nato");
		
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice(speaker);
		voice.allocate();
		
		while(true) {
			randomWords(rand.nextInt(10));
			rest(rand.nextInt(5));
			randomNato(rand.nextInt(10));
			rest(rand.nextInt(5));
			numberStation(rand.nextInt(10));
			rest(rand.nextInt(5));
			gibberish(rand.nextInt(10));
			rest(rand.nextInt(5));
			tillZero();
			rest(rand.nextInt(5));
		}
		
	}
	
	private void randomNato(int times) {
		String w = "";
		for(int i=0; i<times; i++) {
			w = nato[rand.nextInt(26)];
			w += ", "+rand.nextInt(100);
			w += ", "+rand.nextInt(100);
			voice.speak(w);
		}
	}
	
	private void randomWords(int times) {
		for(int i=0; i<times; i++) {
			voice.speak(randWord());
		}
	}
	
	private String[] arrayFromTxt(String fileName) {
		List<String> lines = null;
		File file = new File(fileName+".txt");
		try {
			lines = Files.readAllLines(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines.toArray(new String[1000]);
	}

	private String randWord() {
		return dict[rand.nextInt(dict.length)];
	}
	
	private void numberStation(int times) {
		for(int i=0; i<times; i++) numberStation();
	}
	
	private void numberStation() {
		for(int i=0; i<4; i++) {
			voice.speak(rand.nextInt(10)+" ");
			rest(.5);
		}
		voice.speak(randWord());
		rest(2);
	}

	private void gibberish(int times) {
		for(int i=0; i<times; i++) gibberish();
		
	}

	private void gibberish() {
		String s = "";
		for(int i=0; i<5; i++)
			s += randChar();
		
		voice.speak(s);
	}
	
	private char randChar() {
		return (char) (rand.nextInt(26) + 65);
	}

	private void tillZero() {
		int i = 1;
		while(i != 0) {
			i = rand.nextInt(10);
			voice.speak(Integer.toString(i));
			System.out.println(i);
		}
		
		
		
	}
	
	public void rest(double seconds) {
		try {
			Thread.sleep((long) (seconds*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
