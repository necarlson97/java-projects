import java.awt.Graphics;
import java.util.Random;

public abstract class HousePart extends GameObject{
	
	int x;
	int y;
	
	public HousePart(int x, int y, int width, int height, int index) {
		super(x, y, width, height, index);
		this.x = x;
		this.y = y;
	}
	
	public abstract void run();
	
	public abstract void render(Graphics g);
	
	public void use() {
		
	}

}
