import java.awt.Color;
import java.awt.Graphics;

public class Window extends HousePart {
	
	public boolean broken = false; 

	int maxShards = 5;
	
	Color color = new Color(200, 200, 255);
	
	int floorOffset = margin/2;
	
	public Window(Story s, int index) {
		super(s.x+(s.width*index), s.y, margin/2, s.height-margin, index);
		particles = new Particle[maxShards];
	}

	@Override
	public void run() {
		runParticles();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x-width/2, y-height-floorOffset, width, height);
		
		renderParticles(g);
	}
	
	public boolean smash() {
		if(broken) return false;
		broken = true;
		for(int i=0; i<particles.length; i++) {
			particles[i] = new Shard(this, i);
		}
		color = Color.white;
		return true;
	}

}
