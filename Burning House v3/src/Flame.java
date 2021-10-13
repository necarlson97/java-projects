import java.awt.Color;
import java.awt.Graphics;

public class Flame extends Particle {

	public Flame(int x, int y, int size, int i, GameObject parent) {
		super(x, y, 40, size, i, parent);
		int speed = margin/5;
		xVel = (r.nextFloat()*speed) - (float)speed/2;
		yVel = -r.nextFloat()*speed;
	}
	
	public void run() {
		super.run();
		float grn = (float)life/lifeSpan;
		if(grn > 0 && grn < 1) color = new Color(1, 1-grn, 0);
	}

	@Override
	public void render(Graphics g) {
		int drawSize = (int) (width * ((float)life/lifeSpan) * 1.5);
		
		g.setColor(color);
		g.fillRect((int)x-drawSize/2, (int)y-drawSize/2, drawSize, drawSize);
	}

}
