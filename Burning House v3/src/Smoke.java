import java.awt.Color;
import java.awt.Graphics;

public class Smoke extends Particle{

	public Smoke(int x, int y, int size, int i, Fire parent) {
		super(x, y, size, size, i, parent);
		int speed = size/20;
		xVel = (float) (r.nextFloat() - .5);
		yVel = -r.nextFloat()*speed;
		color = new Color(0, 0, 0);
	}
	
	public void run() {
		super.run();
		
		// Black to white, then fade
//		float gray = 1-(float)life/lifeSpan;
//		if(gray > 0 && gray < 1) {
//			if(life < 5) color = new Color(gray, gray, gray, (float)life/5);
//			else color = new Color(gray, gray, gray);
//		}
		
		// Black only fade
		if(life < 5) color = new Color(0, 0, 0, (float)life/5);
		
		
	}

	@Override
	public void render(Graphics g) {
		int drawSize = 2* (width - life);
		
		g.setColor(color);
		g.fillOval((int)x-drawSize/2, (int)y-drawSize/2, drawSize, drawSize);
	}

}
