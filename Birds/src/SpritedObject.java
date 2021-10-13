import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpritedObject {

	BufferedImage[] sprites; 
	
	int spriteWidth;
	int spriteHeight;
	
	static int scale = 5;
	
	float x;
	float y;
	
	int drawWidth;
	int drawHeight;

	public SpritedObject(int x, int y, String spriteSheetString, int size) {
		spriteWidth = size;
		spriteHeight = size;
		setKnownVars(x, y, spriteSheetString);
	}
	
	public SpritedObject(int x, int y, String spriteSheetString, int width, int height) {
		spriteWidth = width;
		spriteHeight = height;
		setKnownVars(x, y, spriteSheetString);
	}
	
	public void setKnownVars(int x, int y, String spriteSheetString){
		this.x = x;
		this.y = y;
		
		getSpritesFromSheet(spriteSheetString);
		
		drawWidth = (spriteWidth-1) * scale;
		drawHeight = (spriteHeight-1) * scale;
	}
	
	public void getSpritesFromSheet(String sheetFileName){
		BufferedImage spriteSheet;
		try {
			spriteSheet = ImageIO.read(new File(sheetFileName));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		int spriteRows = (spriteSheet.getWidth()/spriteWidth);
		sprites = new BufferedImage[spriteRows];
		
		for(int i = 0; i < spriteRows; i++){
			sprites[i] = 
					spriteSheet.getSubimage(i*spriteWidth, 0, spriteWidth, spriteHeight);
		}
			
	}
	
}
