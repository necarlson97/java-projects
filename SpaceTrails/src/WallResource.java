import java.awt.Graphics;

public class WallResource extends WallModule{
	
	public int resourceMax;
	public int resourceLevel;
	public int resourceIncrement = 100;
	
	public WallResource(int x, int y, String spriteSheetString, int resourceMax) {
		super(x, y, spriteSheetString, 8);
		this.resourceMax = resourceMax;
	}
	
	public WallResource(LabModule lab, int row, int col, String spriteSheetString, int resourceMax) {
		super( lab, row, col, spriteSheetString, 8 );
		this.resourceMax = resourceMax;
	}
	
	public void run(int count){
		if(resourceLevel < resourceMax &&
				count % resourceIncrement == 0) resourceLevel+=1;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		if(resourceLevel > 3*(resourceMax/4)) 
			g.drawImage(sprites[3], intX, intY, drawWidth, drawHeight, null);
		else if(resourceLevel > 2*(resourceMax/4)) 
			g.drawImage(sprites[2], intX, intY, drawWidth, drawHeight, null);
		else if(resourceLevel > (resourceMax/4)) 
			g.drawImage(sprites[1], intX, intY, drawWidth, drawHeight, null);
		else
			g.drawImage(sprites[0], intX, intY, drawWidth, drawHeight, null);
	}
	
}
