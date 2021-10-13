import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpritedObject {

	BufferedImage sprite; 
	BufferedImage[] sprites; 
	
	int spriteWidth;
	int spriteHeight;
	
	static int scale = 5;
	
	float x;
	float y;
	
	int drawWidth;
	int drawHeight;
	
	public SpritedObject(int x, int y, float xVel, float yVel, String spriteSheetString) {
		getSpritesFromSheet(spriteSheetString);
		spriteWidth =  sprite.getWidth();
		spriteHeight = sprite.getHeight();
		setKnownVars(x, y);
	}

	public SpritedObject(int x, int y, String spriteSheetString, int size) {
		spriteWidth = size;
		spriteHeight = size;
		getSpritesFromSheet(spriteSheetString);
		spriteWidth --;
		spriteHeight --;
		setKnownVars(x, y);
	}
	
	private void setKnownVars(int x, int y){
		this.x = x;
		this.y = y;
		
		drawWidth = spriteWidth * scale;
		drawHeight = spriteHeight * scale;
	}
	
	public void getSpritesFromSheet(String sheetFileName){
		BufferedImage spriteSheet;
		try {
			spriteSheet = ImageIO.read(new File(sheetFileName));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		if(spriteWidth != 0 || spriteHeight != 0){
			int spriteRows = (spriteSheet.getWidth()/spriteWidth);
			int spriteCols = (spriteSheet.getHeight()/spriteHeight);
			sprites = new BufferedImage[spriteRows * spriteCols];
			
			int spriteIndex = 0;
			for(int row = 0; row < spriteRows; row++){
				for(int col = 0; col < spriteCols; col++){
					int spriteStartX = row*spriteWidth;
					int spriteStartY = col*spriteHeight;

					sprites[spriteIndex] = 
							spriteSheet.getSubimage(spriteStartX, spriteStartY, spriteWidth-1, spriteHeight-1);
					spriteIndex++;
				}
			}
			
			sprite = sprites[0];
		}
		else sprite = spriteSheet;
	}
	
	public boolean inHitbox(float givenX, float givenY) {
		int intX = (int) (givenX-x)/scale;
		int intY = (int) (givenY-y)/scale;
		
		if(intX <= 0 || intX >= sprite.getWidth()) return false;
		if(intY <= 0 || intY >= sprite.getHeight()) return false;
		
		Color colorAt = new Color(sprite.getRGB(intX, intY), true);
		return (colorAt.getAlpha() != 0);
	}
	
}
