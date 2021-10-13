import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpritedBackgroundObject extends BackgroundObject{

	BufferedImage sprite; 
	int subSpriteX;
	int subSpriteY;
	int subSpriteWidth;
	int subSpriteHeight;
	
	int width;
	int height;
	
	public SpritedBackgroundObject(int x, int y, float xVel, float yVel, String spriteSheetString) {
		super(x, y, xVel, yVel, 0);
		getSpritesFromSheet(spriteSheetString);
	}
	
	public void getSpritesFromSheet(String sheetFileName){
		BufferedImage spriteSheet;
		try {
			spriteSheet = ImageIO.read(new File(sheetFileName));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		if(subSpriteWidth != 0 && subSpriteHeight != 0){
			sprite = spriteSheet.getSubimage(0, 0, subSpriteWidth, subSpriteHeight);
		}
		else sprite = spriteSheet;
	}
	
	public boolean hitTrainCar(float particleX, float particleY) {
		int intX = (int) (particleX-x)/5;
		int intY = (int) (particleY-y)/5;
		
		if(intX <= 0 || intX >= sprite.getWidth()) return false;
		if(intY <= 0 || intY >= sprite.getHeight()) return false;
		
		Color colorAt = new Color(sprite.getRGB(intX, intY), true);
		return (colorAt.getAlpha() != 0);
	}

}
