import java.awt.Color;
import java.awt.Graphics;

public class Blood extends Particle{

	public Blood(Person p, int i) {
		super((int)p.x, (int)p.y, r.nextInt(25)+25, margin, i, p);
		xVel = (float) (r.nextFloat()*margin-margin/2);
		yVel = -r.nextFloat()*margin;
	}
	
	@Override
	public void run() {
		super.run();
		yVel += margin/10;
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.red);
		g.fillOval(intX, intY, width, height);
	}

}
