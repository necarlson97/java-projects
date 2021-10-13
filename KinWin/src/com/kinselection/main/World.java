package com.kinselection.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class World extends Canvas implements Runnable{
	public static final int WIDTH = 500, HEIGHT = 800;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	
	private int event, year, kindAvg, fertAvg, hpCapAvg;
	
	private int test = 0;
	
	static int yearHes = 0;
	
	static int famine;
	static int plague = 0;
	
	private int xPoly1[] = {445,458,458};
    private int yPoly1[] = {26,32,20};
	private Polygon poly1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
	
	private int xPoly2[] = {462,462,475};
    private int yPoly2[] = {20,32,26};
	private Polygon poly2 = new Polygon(xPoly2, yPoly2, xPoly2.length);
	
	
	public World(){
	
		handler = new Handler();
		r = new Random();
		this.addKeyListener(new KeyInput(handler));
	
		new Window(WIDTH, HEIGHT, "Kin Selection Sim", this);
		
		
	    
	    
	
	}
	
	public void run(){
		this.requestFocusInWindow();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void tick(){
		try {
		    Thread.sleep(yearHes);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
//		if(year <= 100 && handler.object.size() > 1){
//			WorldObject temp = handler.object.getFirst();
//			System.out.println("Eldest Stats: K"+temp.kind+" F"+temp.fert+" HC"+temp.hpCap);
//		}
		
		
		if(handler.object.size() == 0){
			
//			if(year >= 100){
//				System.out.println("\nYear of Death: " + year + "\nKindness: " + kindAvg);
//			}
			
			year = 0;
			plague = 0;
			test += 1;
			handler.addObject(new Individual(245,10,0,10,5,10,handler));
		}
		
		event = r.nextInt(50);
		if(event ==0){
			if(kindAvg < 7 || kindAvg > 13) plague-=1;
			else plague+=1;
		}
		plague = (int) clamp(plague,0,20);
		
		handler.tick();
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
		g.fillRect(0, 0,  WIDTH, HEIGHT);
		
		g.setColor(Color.white);
		
		g.drawString("Test #"+test, 15, 15);
		
		year +=1; 
		g.drawString("Year: " + year, 15, 30);
		
		
		if(handler.object.size() > 0){
			for(int i = 0; i < handler.object.size(); i++){
				kindAvg += handler.object.get(i).kind;
				fertAvg += handler.object.get(i).fert;
				hpCapAvg += handler.object.get(i).hpCap;
			}		
			kindAvg /= handler.object.size();
			fertAvg /= handler.object.size();
			hpCapAvg /= handler.object.size();		
		}
		
		if(handler.object.size() >= 1){
			if(kindAvg >= 20) g.drawString("Eldest Kindness: "+handler.object.getFirst().kind, 15, 45);
			else g.drawString("Kindness Avg: "+kindAvg, 15, 45);
			
			if(kindAvg >= 20) g.drawString("Eldest Fertility: "+handler.object.getFirst().fert, 15, 60);
			else g.drawString("Fertility Avg: "+fertAvg, 15, 60);
			
			if(kindAvg >= 20) g.drawString("Eldest Gene Health: "+handler.object.getFirst().hpCap, 15, 75);
			else g.drawString("Gene Health: "+hpCapAvg, 15, 75);
		}
		
		
		famine = (int) handler.object.size()/70;
		if(famine > 0){
			g.setColor(Color.pink);
			g.drawString("Famine Level: "+famine, 15, 90);
		}
		if(plague > 0){
			g.setColor(Color.yellow);
			g.drawString("Plague Level: "+plague, 15, 105);
		}
		
		g.setColor(Color.white);
		
		g.drawString("Speed: "+(10-yearHes/50), 410, 15);
		g.fillPolygon(poly1);
		g.fillPolygon(poly2);
		
		g.setColor(Color.black);
		
		g.drawString("+ -", 450, 30);
		
		
		g.setColor(Color.gray);
		if(handler.object.size() == 0){
			g.fillOval(50, 100, 400, 400);
			g.fillRect(150, 400, 200, 200);
		}
		
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max){
		if(var >= max) 
			return var = max;
		else if(var <= min) 
			return var = min;
		else 
			return var;
	}
	
	public static void main(String[] args){
		new World();
		
	}

}
