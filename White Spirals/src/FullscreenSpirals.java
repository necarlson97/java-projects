import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.Timer;

public class FullscreenSpirals extends Canvas implements Runnable{
	public static final int WIDTH = 1440, HEIGHT = 900;
	private Random r = new Random();
	
	public static void main(String[] args){
		System.out.println("main");
		new FullscreenSpirals();
	}
	
	public FullscreenSpirals() {
		System.out.println("Fullscreen");
		new Window(WIDTH, HEIGHT, "Spirals", this);
		Timer timer = new Timer(10, null);
        timer.start();
	}

	public void render() {
		System.out.println("Running");
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			this.run();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();

		bs.show();
		System.out.println("Shown");
		
		for(int j = 0; j<50; j++) {
			for(double i = 1; i<=200; i+=.01) {
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH, HEIGHT);
				
				drawSpiral(g, i);
				bs.show();
			}
			for(double i = 200; i>1; i-=.01) {
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH, HEIGHT);
				
				drawSpiral(g, i);
				bs.show();
			}
		}
		
		g.dispose();
		bs.show();
		
		
	}
	
	private void drawSpiral(Graphics g, double angleAdd) {

		Graphics2D g2 = (Graphics2D) g;
		//g.setColor(Color.white);
		int x;
		int y;
		//int size = r.nextInt(5)+10;
		int size = 10;
		for(double angle=0; angle<2880; angle+=angleAdd ) {
			double t = 0.1 * angle;
			int strokeSize = (int) (300-t)/10;
			g2.setStroke(new BasicStroke(strokeSize));
			double x1 = (1+t) * Math.cos(t);
			double y1 = (1+t) * Math.sin(t);
			x = (int) ((WIDTH/2) + x1);
			y = (int) ((HEIGHT/2) + y1);
			
			
			// Faint raibow
//			long redAngle = (long) Math.abs( ((x1+angleAdd)%400)-200 );
//			int red = map(redAngle, 0, 500, 1, 255);
//			
//			long grnAngle = (long) Math.abs( ((y1+angleAdd)%400)-200 );
//			int grn = map(grnAngle, 0, 00, 1, 255);
			
			
			// Snake plaid
//			long redAngle = (long) Math.abs( ((x+angleAdd)%20)-10 );
//			int red = map(redAngle, 0, 10, 1, 255);			
//			long grnAngle = (long) Math.abs( ((y+angleAdd)%20)-10 );
//			int grn = map(grnAngle, 0, 10, 1, 255);

			
			// Center blue
//			int red = map((long) Math.abs(x1), 0, 300, 1, 255);
//			int grn = map((long) Math.abs(y1), 0, 300, 1, 255);
			
			// Regular rainbow
//			int red = map((long) x1, -300, 300, 1, 255);
//			int grn = map((long) y1, -300, 300, 1, 255);
//			
			// Random snake
			int red = r.nextInt(254)+1;
			int grn = r.nextInt(254)+1;
			
			// Stable
//			int red = 200;
//			int grn = 200;
			
			
			long bluAngle = (long) Math.abs( (angleAdd%20)-10 );
			int blu =  map(bluAngle, 0, 10, 1, 255);
			
			
			//blu = 100;
			//blu = r.nextInt(254)+1;
			//int blu =  (int) angleAdd;
			
			g.setColor(new Color(red, grn, blu));
			g2.drawOval(x - size/2, y - size/2 , size, size);
		}
	}
	
	int map(long x, long in_min, long in_max, long out_min, long out_max) {
	  return (int) ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
	}
	
	public void run() {
		this.render();
	}

}
