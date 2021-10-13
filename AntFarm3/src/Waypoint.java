import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Waypoint extends Point{

	
	public String s;
	
	public Waypoint(int x, int y, String s) {
		super(x,y);
		this.s = s;
	}
	
//	public boolean equals(Object o) {
////		if(!(o instanceof Waypoint)) return false;
////		Waypoint wpo = (Waypoint) o;
//		return x == wpo.x && y == wpo.y && s.equals(wpo.s);
//	}
	
	public String toString() {
		return s+" "+ super.toString();
	}
	
	@Override
	public void render(Graphics g) {
		if(this == Farm.waypoints.get(0)) g.setColor(Color.red);
		else if(this == Farm.waypoints.get(1)) g.setColor(Color.yellow);
		else if(this == Farm.waypoints.get(2)) g.setColor(Color.green);
		else if(this == Farm.waypoints.get(3)) g.setColor(Color.magenta);
		g.fillRect((int) x, (int) y, 8, 8);
	}
	
}
