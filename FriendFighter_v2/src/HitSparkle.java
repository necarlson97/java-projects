import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class HitSparkle extends SpritedObject{
	
	Stack<FrameState> frameStack = new Stack<FrameState>();
	BufferedImage[] sprites;
	
	String currActionName;
	BufferedImage currSprite;

	public HitSparkle(float x, float y, String typeName) {
		super((int)x, (int)y, 16, 16, typeName+"HitSparkle");
		sprites = getSpritesFromSheet(name+".png");
		
		frameStack.push(new FrameState("die", sprites[sprites.length-1]));
		for(int i= sprites.length-2; i >= 0; i--){
			frameStack.push(new FrameState("exist", sprites[i]));
		}
	}
	
	public void run(){
		if(!frameStack.isEmpty()){
			currSprite = frameStack.pop().sprite;
		}
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		int drawWidth = (int) (spriteWidth*scale);
		int drawHeight = (int) (spriteHeight*scale);
		
		 g.drawImage(currSprite, intX-drawWidth/2, intY-drawHeight/2,  drawWidth, drawHeight, null);
	}

}
