import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpritedObject {
	
	static String name;
	
	float x;
	float y;
	
	BufferedImage currSprite ;
	
	int spriteWidth;
	int spriteHeight;
	
	float scale = FriendFighter.scale;
	
	public SpritedObject(int x, int y, int spriteWidth, int spriteHeight, String name){
		this.name = name;
		this.x = x;
		this.y = y;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}
	
	public BufferedImage[] getSpritesFromSheet(String sheetFileName){
		BufferedImage spriteSheet;
		System.out.println(sheetFileName);
		try {
			spriteSheet = ImageIO.read(new File(sheetFileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		int spriteRows = (spriteSheet.getWidth()/spriteWidth);
		int spriteCols = (spriteSheet.getHeight()/spriteHeight);
		BufferedImage[] sprites = new BufferedImage[spriteRows * spriteCols];
		
		int spriteIndex = 0;
		for(int col = 0; col < spriteCols; col++){
			for(int row = 0; row < spriteRows; row++){
				int spriteStartX = row*spriteWidth;
				int spriteStartY = col*spriteHeight;

				sprites[spriteIndex] = 
						spriteSheet.getSubimage(spriteStartX, spriteStartY, spriteWidth-1, spriteHeight-1);
				spriteIndex++;
			}
		}
		
		return sprites;
	}

}
