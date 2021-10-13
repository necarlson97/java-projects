import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class Equations extends Canvas implements Runnable{
	
	public static final int WIDTH = 1000, HEIGHT = 800;
	private Random r = new Random();
	
	static Thread equationsThred;
	
	public static void main(String[] args){
		new Equations();
	}
	
	public Equations(){
		equationsThred = new Thread(this);
		equationsThred.start();
		new Window(WIDTH, HEIGHT, "Equation Visualizer", this);
	}

	@Override
	public void run() {
		render();
		run();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
//		for(double x=0; x<7; x+=.01){
//			drawEToTheX(g2, x);
//			bs.show();
//		}
		
		//drawSpiderWeb(g2);
		
		int q1Range = 10;
		int q2Range = 10;
		int rRange = 10;
		int sampleRate = (q1Range * q2Range * rRange) / 1000;
		
		int[] fs = new int[1000];
		int c=0;
		for(int q1=1; q1<q1Range; q1++) {
			for(int q2=1; q2<q2Range; q2++){
				for(int r=1; r<rRange; r++){
					if(c%sampleRate == 0) fs[c/sampleRate] = drawCoulombsLaw(g2, q1, q2, r);
					else drawCoulombsLaw(g2, q1, q2, r);
					bs.show();
					g.setColor(Color.black);
					g.fillRect(0, 0, WIDTH, HEIGHT);
					drawFs(g2, fs);
					c++;
				}	
			}	
		}
		
		
		try {
			equationsThred.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bs.show();
	}
	
	private void drawFs(Graphics2D g2, int[] fs) {
		g2.setColor(Color.green);
		int c=0;
		for(int i=1; i<fs.length; i++){
			if(fs[i] != 0) g2.drawLine(i, HEIGHT-fs[i]/100-200, i-1, HEIGHT-fs[i-1]/100-200);
		}
		
	}

	private int drawCoulombsLaw(Graphics2D g2, int q1, int q2, int r) {
		int k = 899;
		int f = (int) ((k*q1*q2)/Math.pow(r, 2));
		
		System.out.println(f);
		
		g2.setColor(Color.orange);
		g2.drawRect(20, HEIGHT-120, 20, q1*10);
		g2.setColor(Color.yellow);
		g2.drawRect(60, HEIGHT-120, 20, q2*10);
		g2.setColor(Color.red);
		g2.drawRect(100, HEIGHT-120, 20, r*10);
		g2.drawOval((WIDTH/2) - r, HEIGHT/2 - r, r*2, r*2);
		
		return f;
		
	}
	
	private void drawSpiderWeb(Graphics2D g2) {
		
		
		int prevI = 600;
		for(int i=prevI-r.nextInt(100); i>0; i-=r.nextInt(100)){
//			g2.setColor(Color.red);
			//g2.drawOval((WIDTH/2)-(i/2), (HEIGHT/2)-(i/2), i, i);
			for(int n=0; n<r.nextInt(10); n++) drawBetwenCircumferencePolyline(g2, (prevI/2), (i/2), r.nextInt(10), true);
			prevI = i;
		}
		//drawBetwenCircumferencePolyline(g2, (prevI/2), 1000, 6, true);
		
		
	}
	
	private void drawBetwenCircumferencePolyline(Graphics2D g2, int radiusOne, int radiusTwo, int nPoints, boolean t){
		int[][] seperatedPointsArray = getBetwenCircumferencePolylinePoints(radiusOne, radiusTwo, nPoints);
		int[] xPoints = seperatedPointsArray[0];
		int[] yPoints = seperatedPointsArray[1];
		
		g2.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
		g2.fillPolygon(xPoints, yPoints, nPoints);
	}
	
	private void drawBetwenCircumferencePolyline(Graphics2D g2, int radiusOne, int radiusTwo, int nPoints){
		int[][] seperatedPointsArray = getBetwenCircumferencePolylinePoints(radiusOne, radiusTwo, nPoints);
		int[] xPoints = seperatedPointsArray[0];
		int[] yPoints = seperatedPointsArray[1];
		
		g2.setColor(Color.white);
		g2.drawPolyline(xPoints, yPoints, nPoints);
	}
	
	private int[][] getBetwenCircumferencePolylinePoints(int radiusOne, int radiusTwo, int nPoints){
		
		Point[] points = new Point[nPoints];
		for(int p=0; p<nPoints; p++){
			if(p%2==0) points[p] = getRandomCircumferencePoint(radiusOne, (WIDTH/2), (HEIGHT/2));
			else points[p] = getRandomCircumferencePoint(radiusTwo, (WIDTH/2), (HEIGHT/2));
		}
		
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		for(int n=0; n<nPoints; n++){
			xPoints[n] = points[n].x;
			yPoints[n] = points[n].y;
		}
		
		int[][] returnArray = {xPoints, yPoints};
				
		return returnArray;
	}
	
	private Point getRandomCircumferencePoint(int radius, int originX, int originY){ 
		double angle = Math.random()*Math.PI*2;
		int x = (int) (Math.cos(angle)*radius);
		int y = (int) (Math.sin(angle)*radius);
		x+= originX;
		y+= originY;
		return new Point(x, y);
	}
	
	private Point getRandomCircumferencePoint(int radius){ 
		double angle = Math.random()*Math.PI*2;
		int x = (int) (Math.cos(angle)*radius);
		int y = (int) (Math.sin(angle)*radius);
		return new Point(x, y);
	}

	public void drawEToTheX(Graphics2D g2, double x){
		
		if(x > 6) g2.setColor(Color.white);
		else if(x > 5) g2.setColor(Color.magenta);
		else if(x > 4) g2.setColor(Color.red);
		else if(x > 3) g2.setColor(Color.orange);
		else if(x > 2) g2.setColor(Color.yellow);
		else if(x > 1) g2.setColor(Color.green);
		else if(x > 0) g2.setColor(Color.blue);
		
		g2.fillRect((int) (x*100), 0, 1, (int) Math.exp(x));
		
		g2.setColor(Color.gray);
		g2.fillOval((int) (x*100), (int) Math.exp(x), 7, 7);
	}

}
