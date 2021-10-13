import java.awt.Color;
import java.awt.Graphics;

public class Foam extends Particle{

	public Foam(int x, int y, boolean left) {
		super(x, y, 50, 1);
		yVel = r.nextFloat();
		if(left) xVel = -r.nextFloat()*5;
		else xVel = r.nextFloat()*5;
	}
	
	@Override
	public void run() {
		size = (lifeSpan - life)/2;
		super.run();
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(Color.WHITE);
		g.fillOval(intX-size/2, intY-size/2, size, size);
		g.setColor(Color.BLACK);
		g.drawOval(intX-size/2, intY-size/2, size, size);
	}

}
