import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class AntFarm extends Canvas implements Runnable{
	
public static int DELAY = 1000 / 60;
	
	Random r = new Random();

	private boolean isFullScreen = false;
	static boolean running = true;
	static boolean debug = true;
	Thread thread;
	int count;

	static int windowWidth;
	static int windowHeight;
	static int horizonY;
	
	static char inputChar;
	
	static final int MAXANTS = 50;
	Ant[] ants = new Ant[MAXANTS];
	
	static final int MAXROOMS = 10;
	//static Room[] rooms = new Room[MAXROOMS];
	
	static LinkedList<Dig> digs = new LinkedList<Dig>();
	static LinkedList<Room> rooms = new LinkedList<Room>();
	
	public static void main(String[] args) {
		new AntFarm();
	}
	
	public AntFarm(){
		this.addKeyListener(new KeyInput());
		
		createWindow();
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void createWindow() {
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, this.getClass().getName(), this);
		}
		horizonY = (int) (windowHeight * .1);
	}

	@Override
	public void run() {
		
		int rX = r.nextInt(windowWidth);;
		int rY = horizonY;
		rooms.add(new Room("opening", 0, rX, rY));
		for(int i=1; i<MAXROOMS; i++){
			rX = (int) (r.nextInt((int) (windowWidth*.8))+windowWidth*.1);
			rY = r.nextInt(windowHeight-(3*horizonY))+2*horizonY;
			Room newRoom = new Room("test "+i, i, rX, rY);
			rooms.add(newRoom);
		}
		
		
//		ants[0] = new WorkerAnt(50, 50);
//		ants[1] = nebw WorkerAnt(50, 50);
		
		for(int i=0; i<MAXANTS; i++){
			ants[i] = new WorkerAnt(10+(i*5), 50);
			ants[i].actionQueue.add(new Move(ants[i], rooms.getFirst()));
		}
		
		while(running){
			
			for(Ant a : ants){
				if(a != null) a.run();
			}
			
			render();
			waitForNextFrame();
			count++;
		}
		
	}

	public static boolean isInTunnel(float x, float y){
		if(y < horizonY) return true;
		for(Dig d : digs){
			if(d.destX-d.size/2 < x && d.destX+d.size/2 > x &&
					d.destY-d.size/2 < y && d.destY+d.size/2 > y) return true;
		}
		return false;
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		for(Ant a : ants){
			if(a != null) {
				a.render(g);
				if(debug) a.renderQueue(g);
			}
		}
		
		g.dispose();
		bs.show();
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		for(Dig d : digs)
			d.render(g);
		
		for(Room r : rooms)
			r.render(g);
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, windowWidth, horizonY);
	}
	
	private void waitForNextFrame() {
		long tillNextFrame = DELAY - System.currentTimeMillis()%DELAY;
		if(tillNextFrame<0) {
			System.out.println("dropped frame");
			return;
		}
		try {
			thread.sleep(tillNextFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
