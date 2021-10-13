import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tree extends SpritedObject{
	
	static Random r = new Random();
	BufferedImage treeSprite;
	boolean reversed;

	public Tree(int xOffset) {
		super(Birds.windowWidth+xOffset*(r.nextInt(100)+100), Birds.horizonY-64*5+r.nextInt(50)+30, "Trees.png", 32, 64);
		treeSprite = sprites[r.nextInt(sprites.length-1)];
		if(r.nextInt(2)==0) reversed = true;
	}
	
	public void run(){
		x-=Birds.player.xVel;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)(y-(Birds.player.y/10)); 
		
		int tempWidth = drawWidth;
		if(reversed){
			intX += tempWidth;
			tempWidth *= -1;
		}
		
		g.drawImage(treeSprite, intX, intY, tempWidth, drawHeight, null);
	}

}
