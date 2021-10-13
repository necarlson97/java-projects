
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

public class SnakeGame extends Canvas implements Runnable{
	
	public static int DELAY = 1000 / 60;
	
	static Random r = new Random();
	
	private boolean isFullScreen = false;
	static boolean running = true;
	Thread snakeThread;
	
	static boolean debug = false;
	
	static int windowWidth;
	static int windowHeight;
	
	public static int boxSize;
	
	public static int boxRows;
	public static int boxCols = 20;
	
	static int count = 0;
	
	static Snake player;
	
	static final int MAXFOOD = 20;
	static Food[] food = new Food[MAXFOOD];
	
	public static void main(String[] args){
		new SnakeGame();
	}
	
	public SnakeGame(){
		this.addKeyListener(new KeyInput());
		
		createWindow();
		
		snakeThread = new Thread(this);
		snakeThread.start();
	}


	public void createWindow(){
		if(isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this); 
		}
		else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Snake Game", this); 
		}

		boxSize = windowWidth / (boxCols+2); 
		boxRows = (windowHeight / boxSize) -2;
	}
	
	public static void sizeChange(float change){
		
		player.snakeHead.x = (int) player.snakeHead.x/boxSize;
		player.snakeHead.y = (int) player.snakeHead.y/boxSize;
		
		boxCols *= change;
		boxSize = windowWidth / (boxCols+2); 
		boxRows = (windowHeight / boxSize) -1;

		player.snakeHead.x*= boxSize;
		player.snakeHead.y*= boxSize;
		
		for(int i=0; i<change; i++)
			addFood();
	}

	@Override
	public void run() {
		
		player = new Snake();
		for(int i=0; i<2; i++)
			addFood();
		
		while(running){
			
			player.run(count);
			
			render();
			count++;
			waitForNextFrame();
		}
		try {
			snakeThread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		run();
	}

	static void addFood() {
		Food f = new Food();
		
		if(r.nextInt(5)==0) f = new FastFood();
		else if(r.nextInt(7)==0) f = new GridFood();
		else if(r.nextInt(10)==0) f = new DiscoFood();
		
		for(int i=0; i<MAXFOOD; i++){
			if(food[i]==null) {
				food[i] = f;
				return;
			}
		}
	}
	
	static void removeFood(int removeIndex){
		food[removeIndex] = null;
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		renderFood(g);
		player.render(g);
		
		g.dispose();
		bs.show();
		
	}

	private void renderFood(Graphics g) {
		for(Food f : food)
			if(f!=null) f.render(g);
	}

	private void renderBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		g.setColor(Color.black);
		g.fillRect(boxSize, boxSize, boxCols*boxSize, boxRows*boxSize);
		
		
		if(debug){
			g.setColor(Color.gray);
			for(int i=1; i<=boxCols+1; i++){
				g.drawLine(i*boxSize, 0, i*boxSize, windowHeight);
			}
			g.setColor(Color.darkGray);
			for(int i=1; i<=boxRows+1; i++){
				g.drawLine(0, i*boxSize, windowWidth, i*boxSize);
			}
		}
	}
	

	private void waitForNextFrame() {
		long tillNextFrame = DELAY - System.currentTimeMillis()%DELAY;
		if(tillNextFrame<0) return;
		try {
			snakeThread.sleep(tillNextFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
