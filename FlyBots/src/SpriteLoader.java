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
			e.printStackTrace();
			return null;
		}
		return sprite;
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
