import java.awt.Graphics;

public class WallModule extends SpritedObject{

	public String name;
	
	public WallModule(int x, int y, String spriteSheetString, int size) {
		super(x, y, spriteSheetString, size);
	}
	
	public WallModule(LabModule lab, int row, int col, String spriteSheetString, int size) {
		super( (int)lab.x+(13*scale)+(row*8*scale), (int)lab.y+(13*scale)+(col*8*scale), spriteSheetString, size );
	}
	
	public void run(int count) {

	}

	public void interact() {
		
	}

	public void render(Graphics g) {
		
	}

	
	
	
	
}
