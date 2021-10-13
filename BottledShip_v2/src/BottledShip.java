import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BottledShip extends Canvas implements Runnable {
	
	static int DESIREDFRAMERATE = 60;
	public static int DELAY = 1000 / DESIREDFRAMERATE;
	int frameRate = DESIREDFRAMERATE;
	int frameCount = 0;
	
	long prevFrameTime = System.currentTimeMillis();
	
	Random r = new Random();

	private boolean isFullScreen = false;
	static boolean running = true;
	Thread bottledShipThread;
	int count;

	static int windowWidth;
	static int windowHeight;
	
	int surfacePoints = 10;
	static Water water;
	static ImageShip ship;
	
	static boolean debug = false;

	public static void main(String[] args) {
		new BottledShip();
	}

	public BottledShip() {
		this.addKeyListener(new KeyInput());
		createWindow();
		
		setUp();
		
		bottledShipThread = new Thread(this);
		bottledShipThread.start();
	}

	public void createWindow() {
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Bottled Ship", this);
		}
	}
	
	private void setUp() {
		water = new Water(surfacePoints);
		ship = new Schooner(windowWidth/2, windowHeight/4);
	}

	@Override
	public void run() {
		while (running) {
			
			water.run(count);
			ship.run();
			
			render();
			count++;
			waitForNextFrame();
			frameCount++;
		}
	}


	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		renderBackground(g);
		
		ship.render(g);
		water.render(g);
		if(debug) renderFrameRate(g);
		
		g.dispose();
		bs.show();

	}

	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
	}
	
	private void renderFrameRate(Graphics g) {
		g.setColor(Color.white);
		g.drawString(String.valueOf(frameRate), 100, 100);
	}
	
	private void waitForNextFrame() {
		if(frameCount % DESIREDFRAMERATE == 0) frameRate = DESIREDFRAMERATE;
		
		long currTime = System.currentTimeMillis();
		long tillNextFrame = DELAY - ( currTime - prevFrameTime);
		prevFrameTime = currTime;
		if(tillNextFrame<0) {
			return;
		}
		try {
			bottledShipThread.sleep(tillNextFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		prevFrameTime = currTime;
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

}
