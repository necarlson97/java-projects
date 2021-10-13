import java.awt.Color;
import java.awt.Graphics;

public class Window extends GameObject {
	
	boolean broken = false;
	
	Color color = new Color(200, 200, 255);
	
	int maxShards = 5;
	Shard[] shards;
	
	public Window(int x, int y) {
		super(x, y, 2, 40);
		shards = new Shard[maxShards];
	}

	@Override
	public void run() {
		for(Shard s : shards)
			if(s != null) s.run();
		cleanParticles();
	}
	
	private void cleanParticles() {
		for(int i=0; i<shards.length; i++) {
			if(shards[i] != null && shards[i].life < 0) shards[i] = null;
		}
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX-width/2, intY-height-5, width, height);
		for(Shard s : shards)
			if(s != null) s.render(g);
	}

	@Override
	public void use() {
		if(broken) return;
		broken = true;
		color = Color.WHITE;
		for(int i=0; i<shards.length; i++) {
			shards[i] = new Shard((int)x, (int)y-height+(i*2));
		}
	}
}
