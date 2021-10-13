import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteLoader {
	
	static BufferedImage planeSprite;
	static BufferedImage gearSprite;
	
	static BufferedImage loadSprite(String folder, String spriteName) {
		BufferedImage sprite;
		try {
			sprite = ImageIO.read(new File(folder+"/"+spriteName+".png"));
		} catch (IOException e) {
			System.out.println("Failed to read: "+folder+"/"+spriteName+".png");
			e.printStackTrace();
			return null;
		}
		return sprite;
	}
	
	static BufferedImage[] loadSprites(String folder, String spriteName, int spriteSize) {
		return loadSprites(folder, spriteName, spriteSize, spriteSize);
	}
	
	static BufferedImage[] loadSprites(String folder, String spriteName, int spriteWidth, int spriteHeight) {
		BufferedImage spriteSheet = loadSprite(folder, spriteName);
		int spriteRows = spriteSheet.getHeight() / spriteHeight;
		int spriteCols = spriteSheet.getWidth() / spriteWidth;
		BufferedImage[] sprites = new BufferedImage[spriteRows * spriteCols];
		
		int spriteIndex = 0;
		for(int row = 0; row < spriteRows; row++){
			for(int col = 0; col < spriteCols; col++){
				int spriteStartX = col*spriteWidth;
				int spriteStartY = row*spriteHeight;
				
				sprites[spriteIndex] = 
						spriteSheet.getSubimage(spriteStartX, spriteStartY, spriteWidth, spriteHeight);
				spriteIndex++;
			}
		} 
		
		return sprites;
	}
	
	static BufferedImage tintSprite(BufferedImage sprite, Color tint, float intensity) {
		BufferedImage tinted = new BufferedImage(sprite.getWidth(), 
				sprite.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics g = tinted.getGraphics();
		//tint = new Color(tint.getRed(), tint.getGreen(), tint.getBlue(), 0);
		g.setXORMode(tint);
		g.drawImage(sprite, 0, 0, null);
		g.dispose();
		return tinted;
	}

}
