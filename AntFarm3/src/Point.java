import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Point extends WorldObject{
	public int x;
	public int y;
	
	public Point(int x, int y){
		super(x,y,8,8);
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Point)) return false;
		Point po = (Point) o;
		return x == po.x && y == po.y;
	}
	
	public String toString(){
		return "("+x+","+y+")";
	}

	public void tick() {
		
	}


	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int) x, (int) y, 8, 8);
	}
}
