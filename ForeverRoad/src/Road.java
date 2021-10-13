import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Road {
	
	Random rand = Engine2D.rand;
	
	ForeverRoad fr;
	
	int[] xs;
	int[] ys;
	
	Color color = Color.black;
	
	int width;
	int interval;
	
	int res;
	
	float inertia = 0;
	
	int delay = 1;
	
	int speed;
	
	int stripesOffset = 0;
	
	Road(int resolution, ForeverRoad fr) {
		this.fr = fr;
		
		res = resolution;
		xs = new int[res*2];
		ys = new int[res*2];
		
		
		// Set xs to left and right of road
		width = Engine2D.windowWidth/4;
		int middle = Engine2D.windowWidth/2;
		
		for(int i=0; i<xs.length; i++) {
			if(i < res) xs[i] = middle - width/2;
			else xs[i] = middle + width/2;
		}
		
		// Set ys at constant intervals from top to bottom
		interval = (Engine2D.windowHeight)/(res-1);
		
		int y = 0;
		for(int j=0; j<ys.length; j++) {
			ys[j] = y;
			if(j < res-1) y += interval;
			else if(j > res-1) y -= interval;
		}
		
		speed = interval / delay;
	}
	
	void update() {
		if(Engine2D.frameCount % delay == 0) {
			down();
			stripesOffset = 0;
		}
		else stripesOffset+=speed;
	}
	
	void render(Graphics g) {
		g.setColor(color);
		g.fillPolygon(xs, ys, xs.length);
		
		renderStripes(g);
		
		if(fr.debug) renderDebug(g);
		
	}
	
	private void renderStripes(Graphics g) {
		g.setColor(Color.yellow);
		for(int i=0; i<res; i+=1) {
			g.fillRect(xs[i]+width/2-width/20, ys[i]+stripesOffset, width/20, interval/2);
		}
	}

	private void renderDebug(Graphics g) {
		g.setColor(color.ORANGE);
		for(int i=0; i<xs.length; i++)
			g.fillOval(xs[i]-5, ys[i]-5, 10, 10);
	}

	void down() {
		int add = nextCurve();
		xs[0] += add;
		xs[xs.length-1] = xs[0] + width;
		
		for(int i=res; i>0; i--)
			xs[i] = xs[i-1];
		for(int i=res; i<xs.length-1; i++)
			xs[i] = xs[i+1];
	}
	
	int nextCurve() {
		inertia += (rand.nextFloat()*.005)-.0025;
		
		if(xs[0] <= 0) {
			inertia = 0;
			xs[0] = 1;
		}
			
		if (xs[0]+width >= Engine2D.windowWidth) { 
			inertia = 0;
			xs[0] = Engine2D.windowWidth-width-1;
		}
		
		return (int) (width * inertia);
	}

}
