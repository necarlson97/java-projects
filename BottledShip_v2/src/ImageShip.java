import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageShip extends Ship{

	String fileName;
	
	BufferedImage images[];
	AffineTransform identity = new AffineTransform();
	AffineTransform trans = new AffineTransform();
	
	int spriteWidth = 400;
	int spriteHeight = 250;
	
	int hullY;
	
	BufferedImage currImage;
	
	public ImageShip(int x, int y, float hullLoc, String fileName) {
		super(x, y);
		this.fileName = fileName;
		
		setUp();
		hullY = (int) (height * hullLoc);
	}
	
	public ImageShip(ImageShip prevShip) {
		super(BottledShip.windowWidth/2, 0);
		this.fileName = prevShip.fileName;
		
		setUp();
		hullY = prevShip.hullY;
	}
	
	public void setUp(){
		images = getSpritesFromSheet(fileName);
		width = images[0].getWidth()/2;
		height = images[0].getHeight()/2;
		
		frontDistance = width/2;
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
	
	public void run(){
		super.run();
		if (y>BottledShip.windowHeight) BottledShip.ship = new ImageShip(this);
		if(powerOn) currImage = images[1];
		else currImage = images[0];
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;

		Graphics2D g2d = (Graphics2D)g.create();
		
		trans.setTransform(identity);
		double angle = Math.asin(frontY/frontX);
		
		g2d.scale(.5, .5);
		g2d.translate(2*intX-(width), 2*intY-(hullY*2));
		trans.rotate(angle, width, hullY*2 );
		
		g2d.drawImage(currImage, trans, null);
		
		//g.drawImage(image, intX-(width/2), intY-hullY, width, height, null);
		super.render(g);
	}

}
