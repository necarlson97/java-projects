import java.awt.image.BufferedImage;

public class Attack extends Action{
	
	float damedge;
	int hitFrame;
	int range;
	int knockback;
	int hitPause;
	int xOffset;
	int yOffset;
	
	public Attack(String name, float damedge, int hitFrame, int range, int xOffset, int yOffset, Fighter fighter){
		super(name, fighter);
		this.damedge = damedge;
		this.hitFrame = hitFrame;
		this.range = range;
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
		this.knockback = (int)(damedge*10);
		this.hitPause = (int)(damedge*5);
		
		for(int i=0; i<frameStates.length; i++){
			if(i == hitFrame) frameStates[i].actionName = name + i + "hit frame";
		}
	}
	
}
