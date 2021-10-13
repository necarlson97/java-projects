import java.awt.Color;
import java.awt.image.BufferedImage;

public class Emotion {
	
	String[] display;
	int[] flip = {0, 0, 50, 20, 10};
	String eyeRegex;
	String blinkStr;
	
	String name;
	String[] words;
	
	
	int spriteSize = 16;
	
	Color color = Color.white;
	
	private int blink1 = 120; 
	private int blink2 = 160; 
	private int blinkDuration = 5;
	private int stage = 0;
	
	Emotion(String name) {
		this.name = name;
		if(name == "Happy") {
			words = WordLoader.happyWords;
			color = Color.yellow;
			display = Emoticons.happy;
			eyeRegex = Emoticons.happyEye;
			blinkStr = Emoticons.happyBlink;
		}
		else if(name == "Angry") {
			words = WordLoader.angryWords;
			color = Color.red;
			display = Emoticons.angry;
			eyeRegex = Emoticons.angryEye;
			blinkStr = Emoticons.angryBlink;
		}
		else if(name == "Sad") {
			words = WordLoader.sadWords;
			color = Color.blue;
			display = Emoticons.sad;
			eyeRegex = Emoticons.sadEye;
			blinkStr = Emoticons.sadBlink;
		}
		else if(name == "Afraid") {
			words = WordLoader.afraidWords;
			color = Color.green;
			display = Emoticons.afraid;
			eyeRegex = Emoticons.afraidEye;
			blinkStr = Emoticons.afraidBlink;
		}
	}
	
	String getDisplay() {
		String ret = display[stage];
		if(Engine2D.frameCount % blink1 < blinkDuration ||
				Engine2D.frameCount % blink2 < blinkDuration) {
			ret = ret.replaceAll(eyeRegex, blinkStr);
		}
		if(flip[stage] > 0 && Engine2D.frameCount % flip[stage] == 0) 
			display[stage] = reverseEmoticon(display[stage]); 
		
		return ret;
	}
	
	private String reverseEmoticon(String emo) {
		String ret = new StringBuilder(emo).reverse().toString();
		ret = ret.replaceAll("\\(", "1");
		ret = ret.replaceAll("\\)", "(");
		ret = ret.replaceAll("1", ")");
		
		return ret;
	}

	void incrementStage() {
		if(stage < display.length-1) stage++;
	}
	void decrementStage() {
		if(stage > 0) stage--;
	}
	
	void setStage(float f) {
		stage = (int) (f * 5);
	}
	
}
