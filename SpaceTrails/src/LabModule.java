import java.awt.Graphics;

public class LabModule extends SpritedObject {
	
	float x;
	float y;
	
	float xVel;
	float yVel;
	
	public LabModule(int x, int y){
		super(x, y, 0, 0, "LabModule1.png");
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.drawImage(sprite, intX, intY, drawWidth, drawHeight, null);
	}

	public void run() {
		
	}

}
