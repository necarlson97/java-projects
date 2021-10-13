import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;

public abstract class Game extends Canvas implements Runnable{
	
	Random r = new Random();
	
	public static long FPS = 60;
	long start = System.currentTimeMillis();

	private boolean isFullScreen = false;
	static boolean running = true;
	static boolean debug = false;
	Thread thread;
	static int count;

	static int windowWidth;
	static int windowHeight;
	
	
	public Game(){
		this.addKeyListener(new KeyInput(this));
		
		createWindow();
		
		thread = new Thread(this);
		thread.start();
	}
    
    public void createWindow() {
    	String windowName = this.getClass()+"";
    	windowName = windowName.replace("class ", "");
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, windowName, this);
		}
	}
    
    abstract void setup();
    
    @Override
	public void run() {
    	
    	setup();
		
		while(running){
			
			runGame();
			
			render();
			waitForNextFrame();
			count++;
		}
	}

	abstract void runGame();

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g1 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g1;
		g.setStroke(new BasicStroke(3));
		renderBackground(g);
		renderGame(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
	}
	
	abstract void renderGame(Graphics2D g);
	
	private void waitForNextFrame() {

	    long wait = 1000 / FPS;
	    long diff = System.currentTimeMillis() - start;
	    
	    if (diff < wait) {
	    	try {
				thread.sleep((long) (wait - diff));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    else if (debug) {
	    	System.out.println("frame lag, wanted: "+wait+"ms was: "+diff+"ms ");
	    }
	    start = System.currentTimeMillis();
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
