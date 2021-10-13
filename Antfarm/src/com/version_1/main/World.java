package com.version_1.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class World extends Canvas implements Runnable{
	public static final int WIDTH = 200, HEIGHT = 200;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	private Handler blockHandler;
	
	private int index;
	
	
	public World(){
	
		handler = new Handler();
		blockHandler = new Handler();
		r = new Random();
		this.addKeyListener(new KeyInput(handler));
	
		new Window(WIDTH, HEIGHT, "Ant Farm", this);
		
		// Spawn Blocks
		for(int row=1; row <11; row+=1){
			for(int col=1; col <11; col+=1){
				index = col*10 - 10 + row- 1;
				blockHandler.addObject(new Block(row*10,col*10+50,index,handler,blockHandler));
				
			}
		}
	
		blockHandler.object.get(99).setType(1);
		
		System.out.println(blockHandler.object.get(99).type);
		
		handler.addObject(new WorkerAnt(50,50,handler,blockHandler));
		   
	
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
		
		handler.tick();
		blockHandler.tick();
		this.render();
		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor( new Color(210,210,226));
		g.fillRect(0, 0,  WIDTH, HEIGHT);
		
		g.setColor(new Color(180,200,225));
		g.fillRect(0, 0,  WIDTH, 50);
		
		
		handler.render(g);
		blockHandler.render(g);
		
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
