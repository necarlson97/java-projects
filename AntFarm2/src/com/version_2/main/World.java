package com.version_2.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;


public class World extends Canvas implements Runnable{
	public static final int WIDTH = 200, HEIGHT = 200;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	
	private int index;
	
	private static BufferedImage earth = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	//private int[] pixels = new int[200*200];
	public static int[] pixels = (((DataBufferInt) earth.getRaster().getDataBuffer()).getData());
	
	
	
	public World(){
	
		handler = new Handler();
		r = new Random();
		this.addKeyListener(new KeyInput(handler));
	
		new Window(WIDTH, HEIGHT, "Ant Farm", this);
		
		for(int y=0; y <HEIGHT; y++){
			for(int x=0; x<WIDTH; x++){
				if(y<50) pixels[x+y*200] = 0x99CCFF;
				else pixels[x+y*200] = 0x1f0f00;	
			}
		}

		
		handler.addObject(new WorkerAnt(50,0,"build",handler));
		   
	
	}
	
	public void run(){
		this.requestFocusInWindow();
		long lastTime = System.nanoTime();
		double amountOfTicks = 10.0;
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
		
		g.setColor( new Color(225,0,100));
		g.fillRect(0, 0,  WIDTH, HEIGHT);
		
		g.drawImage(earth,0,0,WIDTH,HEIGHT,null);
		
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
