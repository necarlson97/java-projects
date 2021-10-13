import java.awt.Color;
import java.awt.Graphics;

public class Ship extends BottledObject {

	public Ship(Handler handler, int x, int y) {
		super(handler, x, y, 100, 25);
	}
	
	public void run(){
		super.move();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.gray);
		g.fillRect(intX, intY, width, height);
	}
	

}
