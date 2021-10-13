import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class BotBreeder implements Runnable {
	
	Bot[] brood;
	
	boolean driven = false;
	boolean breeding = false;
	boolean driving = false;
	
	int defaultTrials = 30;
	
	float progress;
	
	int x;
	int y;
	int width;
	int height = 100;
	
	int fs;
	
	volatile boolean everbreed;
	
	Thread thread;
	
	boolean running = true;
	
	int broodCount = 0;
	
	BotBreeder(int broodSize) {
		brood = new Bot[broodSize];
		x = 0;
		y = Display.windowHeight-height;
		fs = Display.font.getSize();
		width = fs*6;
		thread = new Thread(this);
		thread.start();
	}
	
	void newBatch() {
		for(int i=0; i<brood.length; i++)
			brood[i] = new Bot();
	}
	
	void drive() {
		drive(defaultTrials);
	}
	
	void drive(int trials) {
		driving = true;
		for(int i=0; i<brood.length; i++) {
			if(brood[i] == null) brood[i] = new Bot();
			for(int t=0; t<trials; t++) {
				brood[i].drive();
				brood[i].success += brood[i].tempSuccess; 
			}
			brood[i].success = brood[i].success / trials;
			progress = (float)i / brood.length;
		}
		Arrays.sort(brood);
		progress = 0;
		driving = false;
		driven = true;
	}
	
	Bot first() {
		if(brood[0] == null) return null;
		return new Bot(brood[0]);
	}
	
	void breed() {
		broodCount++;
		breeding = true;
		for(int i=brood.length/2; i<brood.length; i++) {
			brood[i] = new Bot();
			progress = ((float)i / brood.length)-.5f;
		}
		progress = 0;
		breeding = false;
		driven = false;
		drive();
	}
	
	void render(Graphics g) {
		if(breeding) renderBreeding(g);
		else if(driving) renderDriving(g);
	}

	private void renderBreeding(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Breeding... "+broodCount, x, y);
		g.setColor(Color.green);
		int progressHeight = (int) (progress * height);
		g.fillRect(x, y+(height-progressHeight), width, progressHeight);
	}

	private void renderDriving(Graphics g) {
		g.setColor(Color.white);
	 	g.drawString("Driving... "+broodCount, x, y);
		g.setColor(Color.red);
		int progressHeight = (int) (progress * height);
		g.fillRect(x, y+(height-progressHeight), width, progressHeight);
	}

	@Override
	public void run() {
		while(running) {
			if(everbreed) {
				breed();
			}
		}
	}

}
