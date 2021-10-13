import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public abstract class Game extends Canvas implements Runnable{
	
	static Random r = new Random();
	
	public static long FPS = 60;
	long start = System.currentTimeMillis();

	static boolean toggleScreen = false;
	static boolean isFullScreen = false;
	static boolean running = true;
	static boolean debug = false;
	Thread thread;
	static int count;

	static int windowWidth;
	static int windowHeight;
	
	JFrame frame;
	
	public Game(){
		this.addKeyListener(new KeyInput(this));
		
		createView();
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void toFullScreen() {
		isFullScreen = true;
		frame.dispose();
		createView();
		handleScreenResize();
	}
	
	public void toWindowed() {
		isFullScreen = false;
		frame.dispose();
		createView();
		handleScreenResize();
	}
    
    abstract void handleScreenResize();

	public void createView() {
    	String windowName = this.getClass()+"";
    	windowName = windowName.replace("class ", "");
		if (isFullScreen) {
			createFullScreen(windowName);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			createWindowed(windowName, windowWidth, windowHeight);
		}
	}
    
    public void createFullScreen(String title){
	    	frame = new JFrame(title);
	    	
	    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	    	int width = gd.getDisplayMode().getWidth();
	    	int height = gd.getDisplayMode().getHeight();
	    	
	    	windowWidth = width;
	    	windowHeight = height;
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setUndecorated(true);
		frame.add(this);
		frame.setVisible(true);
		frame.setFocusable(true);
    }
    
    public void createWindowed(String title, int width, int height){
    		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
		frame.setFocusable(true);
    }
    
    abstract void setup();
    
    @Override
	public void run() {
    	
    		setup();
		
		while(running){
			
			if(toggleScreen) {
				toggleScreen = false;
				if(isFullScreen) toWindowed();
				else toFullScreen();
			}
			runGame();
			
			render();
			waitForNextFrame();
			count++;
		}
	}
    
    abstract void keyPressed(KeyEvent e);
    
    abstract void keyReleased(KeyEvent e);

	abstract void runGame();

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g1 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g1;
		g.setStroke(new BasicStroke(2));
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
