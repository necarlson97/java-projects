import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Farm extends Canvas implements Runnable{
	
	public static final int WIDTH = 800, HEIGHT = 800;
	public static final int ROWS = 100, COLS = 100;
	
	private Random r = new Random();
	
	public static Color[][] farmBlocks = new Color[ROWS][COLS];
	
	public static ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>(); 
	public static ArrayList<Ant> ants = new ArrayList<Ant>();
	
	public static int holeWeight = 0;
	
	//public boolean running = true;
	
	public Farm(){
		new Window(WIDTH, HEIGHT, "Ant Farm", this);
		
		// Set sky and dirt pixels
		for(int r=0; r <ROWS; r++){
			for(int c=0; c<COLS; c++){
				if(r<10) farmBlocks[r][c] = Color.white;
				else farmBlocks[r][c] = Color.black;	
			}
		}
		
		//Add Waypoint
		waypoints.add(new Waypoint((r.nextInt(COLS-2)*8)+8, 9*8, "hole"));
		waypoints.add(new Waypoint((r.nextInt(COLS-2)*8)+8, (r.nextInt(10)+40)*8, "trash"));
		waypoints.add(new Waypoint((r.nextInt(COLS-2)*8)+8, (r.nextInt(10)+50)*8, "food"));
		waypoints.add(new Waypoint((r.nextInt(COLS-2)*8)+8, (r.nextInt(10)+60)*8, "brood"));
		
		// Starter Ants
		ants.add(new Worker(160, 8, new Task("visit", "hole", 1)));
		ants.add(new Worker(160, 8, new Task("visit", "hole", 1)));
		ants.add(new Worker(160, 8, new Task("visit", "hole", 1)));
		ants.add(new Worker(160, 8, new Task("visit", "hole", 1)));
		
		boolean running = true;
		while(running){
			this.run();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			this.run();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		for(int r=0; r<ROWS; r++) {
			for(int c=0; c<COLS; c++) {
				g.setColor(farmBlocks[c][r]); 
				// I am sorry about r and c but it works for now
				g.fillRect(r*8, c*8, 8, 8);
			}
		}
		
		for(Ant a : ants) {
			a.render(g);
		}
		
		for(Point p : waypoints) {
			p.render(g);
		}
		
		g.dispose();
		bs.show();
	}

	public void tick() {
		for(Ant a : ants) {
			a.tick();
		}
		
		if(!waypoints.isEmpty()) {
			int piley = waypoints.get(0).y+(holeWeight/64);
			int pilex = waypoints.get(0).x+(holeWeight);
			//System.out.println("Pile x: "+pilex+" Pile y: "+piley);
			for(int i=waypoints.get(0).x/8; i<=pilex/8; i++){
				farmBlocks[piley/8][i] = Color.gray;
			}
		}
	}
	
	public void run() {
		this.tick();
		this.render();
	}

	public static void main(String[] args) {
		new Farm();
	}

}
