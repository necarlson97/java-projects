import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;

public class FriendFighter extends Canvas implements Runnable{
	
	public static int DELAY = 1000 / 24;
	
	private boolean isFullScreen = false;
	static boolean running = true;
	Thread friendFighterThread;
	
	static int windowWidth;
	static int windowHeight;
	static float scale;
	static int floorY;
	
	static int count = 0;
	
	static Fighter player1;
	static Fighter player2;
	
	public static void main(String[] args){
		new FriendFighter();
	}
	
	public FriendFighter(){
		this.addKeyListener(new KeyInput());
		createWindow();
		
		player1 = new Tmily((int)(windowWidth * .25));
		player2 = new Sook((int)(windowWidth * .75));
		player1.enemy = player2;
		player2.enemy = player1;
		
		friendFighterThread = new Thread(this);
		friendFighterThread.start();
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
			new Windowed(windowWidth, windowHeight, "Friend Fighter", this); 
		}
		// apx 2.5 screen pixels per sprite pixel in windowed, apx 3.6 n full
		scale = (float) .0055 * windowWidth; 
		floorY = (int) (windowHeight * .75);
	}

	@Override
	public void run() {
		int prevCount = 0;
		while(running){
			
			player1.run();
			player2.run();
			
			render();
			count++;
			waitForNextFrame();
		}
//		try {
//			friendFighterThread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		run();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		renderHealthBars(g);
		player1.render(g);
		player2.render(g);
		
		g.dispose();
		bs.show();
		
	}

	private void renderBackground(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		g.setColor(Color.darkGray);
		g.fillRect(0, floorY-20, windowWidth, windowHeight - floorY);
		
		g.setColor(Color.black);
		g.fillRect(0, floorY-5, windowWidth, 10);
		
	}
	
	private void renderHealthBars(Graphics g) {
		float playerHealthRatio;
		int barWidth;
		
		g.setColor(player1.color.brighter());
		playerHealthRatio = player1.prevHealth / player1.maxHealth;
		barWidth = (int) (playerHealthRatio * windowWidth/2);
		g.fillRect((windowWidth/2) - barWidth, 20, barWidth, 20);
		
		g.setColor(player1.color);
		playerHealthRatio = player1.health / player1.maxHealth;
		barWidth = (int) (playerHealthRatio * windowWidth/2);
		g.fillRect((windowWidth/2) - barWidth, 20, barWidth, 20);
		
		g.setColor(player2.color.darker());
		playerHealthRatio = player2.prevHealth / player2.maxHealth;
		barWidth = (int) (playerHealthRatio * windowWidth/2);
		g.fillRect((windowWidth/2), 20, barWidth, 20);
		
		g.setColor(player2.color);
		playerHealthRatio = player2.health / player2.maxHealth;
		barWidth = (int) (playerHealthRatio * windowWidth/2);
		g.fillRect((windowWidth/2), 20, barWidth, 20);
	}

	private void waitForNextFrame() {
		long tillNextFrame = DELAY - System.currentTimeMillis()%DELAY;
		if(tillNextFrame<0) return;
		try {
			friendFighterThread.sleep(tillNextFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
