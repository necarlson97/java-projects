import java.awt.Color;
import java.awt.image.BufferedImage;

public class Emotion {
	
	
	int statusCount;
	
	BufferedImage sprites[][] = new BufferedImage[1][];
	
	String name;
	
	int spriteSize = 16;
	
	Color color = Color.white;
	
	private int frame;
	private int renderFPS = 4;
	
	int stage = 0;
	
	Emotion(String name) {
		this.name = name;
		for(int i=0; i<1; i++) {
			sprites[i] = SpriteLoader.loadSprites("img/"+name, name+i, spriteSize);
		}
		if(name == "Happy") color = Color.yellow;
		else if(name == "Angry") color = Color.red;
		else if(name == "Sad") color = Color.blue;
		else if(name == "Afraid") color = Color.green;
	}
	
	BufferedImage getFrame() {
		int prevFrame = frame;
		if(Engine2D.frameCount % (Engine2D.FPS/renderFPS) == 0) frame = (frame+1)%sprites[stage].length;
		return sprites[stage][prevFrame];
	}
	
}
