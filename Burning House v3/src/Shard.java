import java.awt.Color;
import java.awt.Graphics;

public class Shard extends Particle{
	
	Color color;

	public Shard(Window w, int i) {
		super(w.x, w.y-r.nextInt(w.height), r.nextInt(w.height), w.width, i, w);
		xVel = (float) (r.nextFloat() - .05);
		yVel = margin/2;
		color = w.color;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
	}

}
