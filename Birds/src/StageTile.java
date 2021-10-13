import java.awt.Graphics;

public class StageTile extends SpritedObject{
	
	String spriteFileName;
	StageTile next;
	
	boolean flipped;
	float xVelMultiplier;
	
	int shiftingY;
	
	
	public StageTile(float xVelMultiplier, int y, String fileName, int width, int height) {
		super(0, y, fileName, width, height);
		this.xVelMultiplier = xVelMultiplier;
		this.spriteFileName = fileName;
		makeNext();
	}

	public StageTile(StageTile prev) {
		super((int)prev.x+prev.drawWidth, (int)prev.y, prev.spriteFileName, 256, 64);
		this.spriteFileName = prev.spriteFileName;
		this.flipped = !prev.flipped;
		this.xVelMultiplier = prev.xVelMultiplier;
	}
	
	public void run(){
		shiftingY = (int) (y-Birds.player.y/10*xVelMultiplier);
		x-= Birds.player.xVel * xVelMultiplier;
		if(x<-drawWidth) becomeNext();
		if(next != null) next.run();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = shiftingY; 
		
//		g.drawRect(intX, intY, drawWidth, drawHeight);
//		g.drawRect(intX, intY, 10, 10);
		
		if(flipped) g.drawImage(sprites[0], intX+drawWidth, intY, drawWidth*-1, drawHeight, null);
		else g.drawImage(sprites[0], intX, intY, drawWidth, drawHeight, null);
		if(next != null) next.render(g);
	}
	
	public void makeNext(){
		next = new StageTile(this);
	}
	
	public void becomeNext(){
		if(next == null) return;
		this.flipped = next.flipped;
		this.x = next.x;
		next.makeNext();
	}
	
	public String toString(){
		return spriteFileName+" ("+x+","+y+") flipped: "+flipped+" xVelMultiplier: "+xVelMultiplier;
	}
}
