import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class PointSet {
	
	int[] xs;
	int[] ys;
	
	int number;
	WaveDisplay waveDisplay;
	
	Color color = Color.white;
	
	int yVel = 0;
	
	public PointSet(int number, WaveDisplay d) {
		this.number = number;
		xs = new int[number];
		ys = new int[number];
		waveDisplay = d;
		
		setupPoints();
	}
	
	public void setupPoints() {
		for(int i=0; i<number; i++) {
			xs[i] = (waveDisplay.windowWidth/(number-1)) * i;
		}
	}
	
	public void jostle(int power) {
		yVel += power;
	}

	public void run() {
		rippleOut();
		returnCenter();
	}

	private void rippleOut() {
		for(int i=1; i<number/2; i++) {
			int left = (number/2)-i;
			int right = (number/2)+i;
			ys[left] = (-ys[left+1] / 2);
			ys[right] = (-ys[right-1] / 2);
		}
	}
	
	private void returnCenter() {
		int center = number/2;
		ys[center] += yVel;
		yVel *= .99;
		if(ys[center] > 0) yVel-=2;
		else if(ys[center] < 0) yVel+=2;
		
		if(Math.abs(ys[center]) > waveDisplay.windowHeight/2) {
			ys[center] *= .8;
			yVel /= -2;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		int[] renderYs = Arrays.copyOf(ys, number);
		for(int i=0; i<number; i++) {
			renderYs[i] = ys[i] + waveDisplay.windowHeight/2;
		}
		g.drawPolyline(xs, renderYs, number);
	}

}
