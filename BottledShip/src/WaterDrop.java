import java.awt.Color;
import java.awt.Graphics;

public class WaterDrop extends BottledObject{

	public WaterDrop(Handler handler, int x, int y) {
		super(handler, x, y, 5, 5);
	}
	
	public void run() {
		super.move();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.blue);
		g.fillOval(intX, intY, width, height);
	}
	
//	@Override
//	public boolean collidesWith(BottledObject other){
//		float distance = Math.abs((this.x - other.x)) + Math.abs((this.y - other.y));
//		return distance <= width;
//	}

}
