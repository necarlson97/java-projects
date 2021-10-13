import java.awt.Color;
import java.awt.Graphics;

public class Flame extends Particle {

	int intensity;
	
	public Flame(int x, int y, int size, int intensity) {
		super(x, y, size, size);
		this.intensity = intensity;
		yVel = -r.nextFloat()*5;
		xVel = (r.nextFloat()*6)-3;
	}
	
	public void run() {
		float grn = 1- ((float)life/lifeSpan);
		color = new Color(1, grn, 0);
		super.run();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX-size/2, intY-size/2, size, size);
	}

}
