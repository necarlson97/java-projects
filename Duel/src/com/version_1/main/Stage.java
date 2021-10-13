package com.version_1.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Stage extends Canvas implements Runnable{

	private static final long serialVersionUID = -3170905411930965561L;

	public static final int WIDTH = 1000, HEIGHT = 500;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	private Window window;
	
	
	public Stage(){

		handler = new Handler();
		r = new Random();
		this.addKeyListener(new KeyInput(handler));
		
		new Window(WIDTH, HEIGHT, "Who will win?", this);
		
		handler.addObject(new Limb(1,1,handler));
		
		
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
		new Stage();
		
	}
	
}				
