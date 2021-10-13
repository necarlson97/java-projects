import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;

public abstract class DisplayEngine1 extends Canvas implements Runnable, KeyListener, MouseListener {
	
	public static long FPS = 60;
	
	Thread thread;
	long frameStart = System.currentTimeMillis();
	static int frameCount;
	
	static int windowWidth = 1000;
	static int windowHeight = 800;
	
	JFrame frame;
	
	static Random rand = new Random();
	
	
	public DisplayEngine1()  {
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		setFrame((""+this.getClass()).replace("class ", ""));
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void setFrame(String windowName) {
		setFrame(windowName, windowWidth, windowHeight, false, true);
	}
	
	public void setFrame(String windowName, int width, int height, boolean resize, boolean decorated) {
		if(frame != null) frame.dispose();
		windowWidth = width;
		windowHeight = height;
		
	    	frame = new JFrame(windowName);
			
		frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
		frame.setMaximumSize(new Dimension(windowWidth, windowHeight));
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
		
		frame.setResizable(resize);
		frame.setUndecorated(!decorated);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
		frame.setFocusable(true);
	}
	
	public void fullscreen() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		setFrame(frame.getName(), gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight(), false, false);
	}
	
	@Override
	public void run() {
		setup();
		
		while(true){
			update();
			
			preRender();
			waitForNextFrame();
			frameCount++;
		}
	}
	
	abstract public void setup(); 
	
	abstract public void update();
	
	abstract void render(Graphics2D g);
	
	public void preRender(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}

		Graphics g1 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g1;
		
		cleanFrame(g);
		
		render(g);
		
		g.dispose();
		bs.show();
	}
	
	private void cleanFrame(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	private void waitForNextFrame() {
	    long wait = 1000 / FPS;
	    long difference = System.currentTimeMillis() - frameStart;
	    
	    if (difference < wait) {
		    	try {
		    		Thread.sleep((long) (wait - difference));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }

	    frameStart = System.currentTimeMillis();
	}
	

	public void addNotify() {
        super.addNotify();
        requestFocus();
    } 
	
}

	




