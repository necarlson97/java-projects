import java.awt.Color;
import java.awt.Graphics;

public class Dust extends Particle {

	public Dust(int x, int y) {
		super(x, y, 10, 5);
		yVel = (r.nextFloat()*5)-5;
		xVel = (r.nextFloat()*4)-2;
	}
	
	public void run() {
		float gray = (float)life/lifeSpan;
		color = new Color(gray, gray, gray);
		super.run();
	}

	@Override
	public void render(Graphics g) {
		
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX-size/2, intY-size/2, size, size);
	}

}
