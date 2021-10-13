import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Fire extends GameObject{
	
	int maxFlames = 5;
	int maxSmoke = 6;
	static int maxSize = 50;
	static int startingSize = 5;
	
	boolean flaming = true;
	
	int countAdd = Game.count;
	
	public Fire(int x, int y) {
		super(x, y, startingSize, 0, 0);
		setupParticles();
	}
	
	public void setupParticles() {
		particles = new Particle[maxFlames+maxSmoke];
		for(int i=0; i<maxFlames; i++) {
			particles[i] = new Flame((int)x, (int)y, width, i, this);
		}
		for(int i=maxFlames; i<maxSmoke; i++) {
			particles[i] = new Smoke((int)x, (int)y, width, i, this);
		}
	}
	
	public void run() {
		shrinkGrow();
		
		color = redYellow();
		
		float sizeRatio = (float)width/maxSize;
			
		int flameCount = (int) (sizeRatio * maxFlames);
		for(int i=0; i<=flameCount-1; i++) {
			if(particles[i] == null) particles[i] = new Flame((int)x, (int)y, width, i, this);
		}
		int smokeCount = maxFlames + (int) (sizeRatio * maxSmoke);
		//System.out.println("F: "+flameCount+" S: "+smokeCount);
		for(int i=maxFlames; i<=smokeCount-1; i++) {
			if(particles[i] == null) particles[i] = new Smoke((int)x, (int)y+height/2, width, i, this);
		}
		
		runParticles();
		
	}
	
	private void shrinkGrow() {
		if(flaming) {
			if((Game.count+countAdd) % Driver.hundredCount/4 == 0 && 
					width < maxSize) width ++;
		}
		else if((Game.count+countAdd) % Driver.hundredCount/10 == 0) width --;
		
	}
	
	private Color redYellow() {
		float grn = (float) Driver.oscillateNumber(Game.count+countAdd, 80, .5);
		return new Color(1, grn, 0);
	}
	
	public void render(Graphics g) {
		renderParticles(g);
		g.setColor(color);
		g.fillRect((int)x-width/2, (int)y-width/2, width, width);
	}
	
	public void use() {
		
	}

}
