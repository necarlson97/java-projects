import java.awt.image.BufferedImage;

public class SpritedObject {
	
	float x;
	float y;
	
	BufferedImage currSprite ;
	
	int spriteWidth;
	int spriteHeight;
	
	public SpritedObject(int x, int y, int spriteWidth, int spriteHeight, String name){
		this.x = x;
		this.y = y;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

}
