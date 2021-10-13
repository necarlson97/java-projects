import java.awt.Color;
import java.awt.Graphics;

public class Shard extends Particle{
	
	Color color = new Color(200, 200, 255);

	public Shard(int x, int y) {
		super(x, y, 20, 3);
		xVel = (float) (r.nextFloat()-.5);
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		y+=2;
		
		g.setColor(color);
		g.fillRect(intX, intY, size, size);
	}

}
