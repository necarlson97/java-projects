import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Blood extends Particle{

	public Blood(int x, int y) {
		super(x, y, 200, 5);
		yVel = (r.nextFloat()*5)-2;
		xVel = (r.nextFloat()*5)-1;
	}
	
	public void run() {
		color = new Color((float)life/lifeSpan, 0, 0);
		super.run();
	}

	@Override
	public void render(Graphics g) {
		
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillOval(intX-size/2, intY-size/2, size, size);
	}

}
