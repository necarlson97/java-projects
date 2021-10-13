import java.awt.image.BufferedImage;

public class Pidgeon extends Bird{

	public Pidgeon(int x, int y) {
		super(x, y, "Pidgeon.png");
		wingPower = 5;
		glideAt = 2;
		setSprites();
		currentSprite = restSprite;
	}

	private void setSprites() {
		restSprite = sprites[0];
		
		flapSprites = new BufferedImage[3];
		flapSprites[0] = sprites[1];
		flapSprites[1] = sprites[2];
		flapSprites[2] = sprites[3];
		
		idleSprites = new BufferedImage[2];
		idleSprites[0] = sprites[4];
		idleSprites[1] = sprites[5];
		
		eatSprites = new BufferedImage[4];
		eatSprites[0] = sprites[6];
		eatSprites[1] = sprites[7];
		eatSprites[2] = sprites[8];
		eatSprites[3] = sprites[9];
	}
	
	

}
