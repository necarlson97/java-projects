import java.awt.Graphics;

public abstract class Pilot {
	
	boolean left;
	boolean right;
	boolean up;
	boolean down;
	
	Plane plane;
	Setting setting;
	
	Pilot() {
		plane = new Mustang();
	}
	
	void update() {
		if(plane != null) {
			if(left) plane.throttleDown();
			else if(right) plane.throttleUp();
			if(up) plane.angleUp();
			else if(down) plane.angleDown();
			plane.update();
		}
	}
	
	void render(Graphics g) {
		if(plane != null) plane.render(g);
	}
	
	

}
