import java.awt.Color;
import java.awt.Graphics;

public class Barrier extends BottledObject{

	int xOrigin;
	int yOrigin;
	
	public Barrier(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
		//super(handler, x, y, width, height, 0, -5);
	
		xOrigin = x;
		yOrigin = y;
		falling = false;
	}
	
	public void run(){
		super.move();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.red);
		g.drawRect(intX, intY, width, height);
	}

	
	
}
