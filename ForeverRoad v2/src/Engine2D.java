import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;

public abstract class Engine2D extends Canvas implements Runnable, KeyListener {
	
	// FPS stands for 'Frames per Second', which is how many times a second that we update the screen
	// 60 should look smooth to the human eye
	public static long FPS = 60;
	
	// The 'thread' helps keep track of the actions this program is preforming,
	// and lets us pause those actions to, say, wait for the next fame if our current
	// frame was finished too quickly
	Thread thread;
	long frameStart = System.currentTimeMillis();
	static int frameCount;
	
	
	// Variables for the pixel width and height of our game display
	static int windowWidth = 1000;
	static int windowHeight = 800;
	
	// A variable that represents the window the game will be displayed in
	JFrame frame;
	
	// Many games need random numbers, and this is a generator we can use to produce those
	static Random rand = new Random();
	
	
	// Here is the 'constructor', which is where the object is 'constructed', it is the first thing called,
	// and here does nothing more than setup the engine as using keypresses and using a window
	public Engine2D()  {
		this.addKeyListener(this);
		
		createView();
		
		thread = new Thread(this);
		thread.start();
	}
	
	// This method opens up a window for our game
	public void createView() {
		
		// We will just name this window the same thing we named our file
	    	String windowName = this.getClass()+"";
	    	windowName = windowName.replace("class ", "");	
	    	frame = new JFrame(windowName);
			
	    	// We will set the size of our window based on the varables above
		frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
		frame.setMaximumSize(new Dimension(windowWidth, windowHeight));
		frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
		
		// We want the window to stay the same size
		frame.setResizable(false);
		// to close when the game exits
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// to appear in the middle of the screen
		frame.setLocationRelativeTo(null);
		// And to be visible and 'ontop' when the game starts up
		frame.add(this);
		frame.setVisible(true);
		frame.setFocusable(true);
	}
	
	// This is called once the window is finished
	@Override
	public void run() {
		// It tells the game to preform its setup
		setup();
		
		// Then as long as the program is running
		while(true){
			// We preform the calculations for each frame
			update();
			
			// Then render the frame
			preRender();
			// Then wait for the next one
			waitForNextFrame();
			// And increment our count
			frameCount++;
		}
	}
	
	// Here is where we let the game do whatever it needs to before the frames actually
	// start, so this could be like loading up some sprites, or creating a player, or whatever
	abstract public void setup(); 
	
	// This is the method that gets called once a frame to 'do whatever it is the game does'
	// Since this is so dependent on the game, the engine leaves it blank, as I can make
	// no assumtions as to what you will be doing here, it will be entirly dependent
	// on the game, but it could be moving the player's position, checking collisons,
	// all of those actions will happen here
	abstract public void update();
	
	// Here is where we will 'draw' all of the visual elemets of our game to the window.
	// Again, this is left 'abstact', as in the game will ahve to define for itself
	// what kinds of things it wants to draw
	abstract void render(Graphics2D g);
	
	// However, there are a handful of things I know any render function will need,
	// and here is where we get those out of the way.
	public void preRender(){
		// Here we are telling the program we want to draw things to the window
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		// And specifying we are using a 2d enviornent to draw
		Graphics g1 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g1;
		
		// We will 'clean the screen' of the previous drawings by displaying a large black square
		cleanFrame(g);
		
		// Here we call that abstract method that each game will have their own individual definition for
		render(g);
		
		// And lastly we actualy deliver the things we have drawn to the window
		g.dispose();
		bs.show();
	}
	
	// Here we draw a big black square over everything to clean the screen, as discussed above
	private void cleanFrame(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
	}
	
	// Since our program uses the keyboard, the progam will call these methods for us
	// whenever a key is pressed or released
	@Override
	public abstract void keyPressed(KeyEvent e);

	@Override
	public abstract void keyReleased(KeyEvent e);
	
	// This is simmilar to above, but is timed to be more useful for typing applications,
	// like if we were building a program like Microsoft Word. I don't think we will
	// need anything like that, so I don't make it abstact for our game to define,
	// I just leave it blank instead
	@Override
	public void keyTyped(KeyEvent e) { }

	// Here we have our program chill out if it finished calculating the fame early
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
	

	// And this just allows our frame to start with 'focus', 
	// which just means we don't have to click it to let it know we
	// want to use it, the window will appear ontop of everything
	// and ready to await our inputs
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
}

	




