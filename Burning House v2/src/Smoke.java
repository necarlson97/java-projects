import java.awt.Color;
import java.awt.Graphics;

public class Smoke extends Particle{
	
	public Smoke(int x, int y, int size) {
		super(x, y, size*3, size);
		xVel = (r.nextFloat()*2)-1;
	}
	
	public void run() {
		//float gray = 1- ((float)life/lifeSpan); // Black to white
		float gray = (float)life/lifeSpan; // White to black
		color = new Color(gray, gray, gray);
		
		yVel = -3;
		
		super.run();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillOval(intX-size/2, intY-size/2, size, size);
	}

}
