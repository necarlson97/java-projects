import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Fire {
	
	Random r = new Random();
	
	float x;
	float y;
	
	int intensity;
	int size;
	
	int maxFlames = 10;
	Flame[] flames;
	
	int maxSmoke = 10;
	Smoke[] smoke;
	
	public boolean growing = true;
	
	public Fire(int x, int y, int size, int intensity) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.intensity = intensity;
		flames = new Flame[intensity];
		smoke = new Smoke[size];
	}
	
	public void run() {
		if(Game.count%20 == 0) {
			if(growing) size ++;
			else size --;
		}
		
		runFlames();
		runSmoke();
		
		cleanUpParticles();
	}
	
	private void cleanUpParticles() {
		for(int i=0; i<flames.length; i++)
			if(flames[i] != null && flames[i].life < 0) flames[i] = null;
		for(int i=0; i<smoke.length; i++)
			if(smoke[i] != null && smoke[i].life < 0) smoke[i] = null;
		
	}

	private void runFlames() {
		for(int i=0; i<flames.length; i++) {
			if(flames[i] == null && size > 10) 
				flames[i] = new Flame((int)x, (int)y, r.nextInt(size), r.nextInt(size));
			if(flames[i] != null) flames[i].run();
		}
	}

	private void runSmoke() {
		for(int i=0; i<smoke.length; i++) {
			if(smoke[i] == null && size > 0) 
				smoke[i] = new Smoke((int)x, (int)y, r.nextInt(size));
			if(smoke[i] != null) smoke[i].run();
		}
	}

	public void render(Graphics g) {
		
		int intX = (int)x;
		int intY = (int)y;
		
		float grn = (float) oscillateNumber(Game.count, 80, .5);
		g.setColor(new Color(1, grn, 0));
		g.fillRect(intX, intY-size, size, size);
		
		if(size < 5) return;
		
		renderSmoke(g);
		renderFlames(g);
		
//		if(Game.debug) {
//			g.setColor(Color.white);
//			g.drawString("s: "+size+" i: "+intensity, (int)x, (int)y+20);
//		}
		
	}
	
	private void renderSmoke(Graphics g) {
		for(int i=0; i<smoke.length; i++)
			if(smoke[i] != null) smoke[i].render(g);
	}

	private void renderFlames(Graphics g) {
		for(int i=0; i<flames.length; i++)
			if(flames[i] != null) flames[i].render(g);	
	}

	public static double oscillateNumber(int numb, int period, double scale) {
	    return Math.sin(numb*2*Math.PI/period)*(scale/2) + (scale/2);
	}

}
