import java.awt.Color;
import java.awt.Graphics;

public class Foam extends Particle{

	public Foam(Module m, int size, int i) {
		super(m.x+r.nextInt(m.width), m.y-r.nextInt(m.height), size, size, i, m);
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		float trans = (float)life/lifeSpan; 
		g.setColor(new Color(1, 1, 1, trans));
		g.fillOval(intX-width/2, intY-width/2, width, width);
		
		g.setColor(new Color(0, 0, 0, trans));
		g.drawOval(intX-width/2, intY-width/2, width, width);
		
	}

}
