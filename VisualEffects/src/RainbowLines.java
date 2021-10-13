
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;


public class RainbowLines extends Canvas implements Runnable{
	
	public static final int WIDTH = 1440, HEIGHT = 900;
	private static BufferedImage earth = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	private Random rand = new Random();
	
	public RainbowLines(){
		
		new Window(WIDTH, HEIGHT, "Rainbow Lines", this);
		this.render();
		
		
	}
	
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		
		bs.show();
		
//		drawDrillSpider(g);
//		for(double i = 1; i<=100; i+=.001) {
//			g.setColor(Color.black);
//			g.fillRect(300, 0, 800, 500);
//			
//			g.setColor(Color.white);
////			drawDrillSpider(g);
//			drawDrillDots(g, i);
//			bs.show();
//			
////			try {
////				TimeUnit.MILLISECONDS.sleep(3);
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
//			
//			
//		}
		
		
//		drawBullseye(g);
		
		//drawRainbowPixels(g);
		
//		for(int i=0; i< 100; i++) {
//			drawRainbowLine(g);
//		}
		
//		for(int i=1; i<WIDTH/2; i++) {
//			g.setColor(Color.black);
//			g.fillRect(0, 0, WIDTH, HEIGHT);
//			
//			drawGroupedPixels(g, i);
//			bs.show();
//		}
		
//		for(double i = 1; i<=100; i+=.001) {
//			g.setColor(Color.black);
//			g.fillRect(300, 0, 800, 500);
//			
//			g.setColor(Color.white);
//			drawDrillDots2(g, i, );
//			bs.show();
//		}
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 800);
		g.setColor(Color.black);
		drawDrillDots2(g, 1, 150, 150);
		drawDrillDots2(g, 8, 150, 400);
		drawDrillDots2(g, 16, 150, 650);
		
		drawDrillDots2(g, 24, 400, 150);
		drawDrillDots2(g, 32, 400, 400);
		drawDrillDots2(g, 40, 400, 650);
		
		drawDrillDots2(g, 48, 650, 150);
		drawDrillDots2(g, 56, 650, 400);
		drawDrillDots2(g, 64, 650, 650);
		bs.show();
		
//		drawStars(g);
		
		save();
		
		g.dispose();
		bs.show();
		
	}
	
	public void save()
	{
	    BufferedImage bImg = new BufferedImage(	WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    Graphics2D cg = bImg.createGraphics();
	    this.paintAll(cg);
	    try {
	            if (ImageIO.write(bImg, "png", new File("./output_image.png")))
	            {
	                System.out.println("-- saved");
	            }
	    } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }
	}
	
	private void drawStars(Graphics g) {
		for(int x=1; x<WIDTH-1; x++) {
			for(int y=1; y<HEIGHT-1; y++) {
				
				g.setColor(Color.white);
				
				if(rand.nextInt(100) == 0 ) g.drawRect(x, y, 1, 1);
			}
		}
	}
	
	private void drawDrillDots2(Graphics g, double angleAdd, int gx, int gy) {
		Graphics2D g2 = (Graphics2D) g;

		int size = 10;
		for(double angle=0; angle<1400; angle+=angleAdd ) {
			double t = 0.08 * angle;
			int strokeSize = (int) (200-t)/10;
			g2.setStroke(new BasicStroke(strokeSize));
			double x1 = (1+t) * Math.cos(t);
			double y1 = (1+t) * Math.sin(t);
			int x = (int) (gx+x1);
			int y = (int) (gy+y1);
			g2.drawOval(x - size/2, y - size/2 , size, size);
		}
	}
	
	private void drawGroupedPixels(Graphics g, int size) {
		Color[][] pixels = new Color[HEIGHT][WIDTH];
		
		for(int w=0; w<WIDTH-1; w+=size) pixels[w][0] = Color.black;
		for(int h=0; h<HEIGHT-1; h+=size) pixels[0][h] = Color.black;
		
		for(int x=size; x<WIDTH-1; x+=size) {
			for(int y=size; y<HEIGHT-1; y+=size) {
				float red = rand.nextFloat();
				float green = rand.nextFloat();
				float blue = rand.nextFloat();
				Color color;
				if( !pixels[x-size][y].equals(Color.black) //|| pixels[x][y-1] != Color.black) 
						&& rand.nextInt(10) < 8)  color = new Color(red, green, blue);
				else if( rand.nextInt(1000) == 1)  color = new Color(red, green, blue);
				else color = Color.black;
				pixels[x][y] = color;
				g.setColor(color);
				
				g.fillRect(x, y, size, size);
				System.out.println(pixels[x][y]);
			}
		}
	}
	
	private void drawDrillDots(Graphics g, double angleAdd) {
		Graphics2D g2 = (Graphics2D) g;
		
		int x = 600;
		int y = 200;
		int size = 15;
		for(double angle=0; angle<2880; angle+=angleAdd ) {
			double t = 0.1 * angle;
			int strokeSize = (int) (300-t)/10;
			g2.setStroke(new BasicStroke(strokeSize));
			double x1 = (1+t) * Math.cos(t);
			double y1 = (1+t) * Math.sin(t);
			x = (int) (600+x1);
			y = (int) (200+y1);
			g2.drawOval(x - size/2, y - size/2 , size, size);
		}
	}
		
	private void drawSpiderLeg(Graphics g, int x, int y, int vx, int vy) {
		g.setColor(Color.white);
		while(x > 0 && x < WIDTH*2 && y > 0 && y < HEIGHT) {
			int size = rand.nextInt(30);
			g.drawOval(x - size/2, y - size/2 , size, size);
			x+=vx+rand.nextInt(7)-3;
			y+=vy+rand.nextInt(7)-3;
		}
	}
	
	private void drawDrillSpider(Graphics g) {
		// create small randomly sized white cirle outlines, starting with many at the center, 
		// then drifting slowley out in a loose line twoards the end
		//(start in middle, pick 'direction', change 'velocity' a bit for each circle
		
		//drawSpiderLeg(g, 1, 1, 5, 5);
		drawSpiderLeg(g, 200, 600, 0, 3);
		drawSpiderLeg(g, 200, 600, 0, -3);
		drawSpiderLeg(g, 200, 600, 3, 0);
		drawSpiderLeg(g, 200, 600, -3, 0);
		
	}

	private void drawBullseye(Graphics g) {
		int size = HEIGHT;
		while(size>0){
			size -= rand.nextInt(100);
			
			float red = rand.nextFloat();
			float green = rand.nextFloat();
			float blue = rand.nextFloat();
			
			Color randomColor = new Color(red, green, blue);
			g.setColor(randomColor);
			
			//g.drawOval(WIDTH/2 - size/2, HEIGHT/2 - size/2, size, size);
		}
		
	}

	private void drawRainbowPixels(Graphics g) {
		for(int x=1; x<WIDTH-1; x++) {
			for(int y=1; y<HEIGHT-1; y++) {
				float red = x/(WIDTH/2) * rand.nextFloat();
				float green = y/(HEIGHT/2) * rand.nextFloat();
				float blue = rand.nextFloat();
				
				Color randomColor = new Color(red, green, blue);
				g.setColor(randomColor);
				
				g.drawRect(x, y, 1, 1);
			}
		}
	}

	public void drawRainbowLine(Graphics g){
		
		float red = rand.nextFloat();
		float green = rand.nextFloat();
		float blue = rand.nextFloat();
		
		Color randomColor = new Color(red, green, blue);
		g.setColor(randomColor);
		
		int x1 = rand.nextInt(WIDTH);
		int y1 = rand.nextInt(HEIGHT);
		int x2 = rand.nextInt(WIDTH);
		int y2 = rand.nextInt(HEIGHT);
		
		g.drawLine(x1, y1, x2, y2);
		
	}

	public void run() {
		
		this.render();
		
	}
	
	public static void main(String[] args){
		new RainbowLines();
	}

	

	
	
	
}