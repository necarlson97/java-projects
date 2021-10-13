import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class BasicGame extends Engine2D{
	
	boolean spooky = false;
	

	@Override
	public void setup() {
		// When is setup called?
		System.out.println("bang!");
		
		System.out.println("Hello its me, the setup!");
		
	}

	@Override
	public void update() {
		// TODO Put a print here to see when update is called
	}

	@Override
	void render(Graphics2D g) {
		// TODO Put a print here to see when render is called
		
		
		// How would I draw a circle of width and height 50?
		g.setColor(Color.white);
		g.fillOval(200, 100, 400, 50);
		
	
		// What does this code do?
		int x = 200;
		int y = 100;
		int width = 100;
		int height = 800;
		g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.blue);
		g.drawRect(20, 20, 20, 20);
		
		// What 'units' are used in height, width, x and y?
		
		// How would I draw a blue oval ontop of a yellow square?
		
		// When is this called?
		if(spooky) {
			g.setColor(Color.pink);
			g.fillOval(0, 0, windowWidth, windowHeight);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Put a print here to see when this is called
		
		// Can you use a print here to see what this 'e.getKeyCode()' returns?
		int keyCode = e.getKeyCode();
		
		
		
		// What about this keyChar?
		char keyChar = e.getKeyChar();
		
		if(keyChar == 'e') {
			System.out.println("Hello world");
		}
		System.out.println(keyChar);
		
		
		
		// When is this called?
		if(keyCode == KeyEvent.VK_SPACE) {
			System.out.println("Spooky");
			spooky = true;
		}
		
		
		
		// How would I check to see if the left arrow was pressed? Or the delete key?
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Check the method above, and try to see if you can print a statement every time
		// someone lets go of the spacebar
		System.out.println("key released");
	}
	
	// Also we need this down here just to let the program know to 
	// start our game when we press 'run'.
	// 'static void main(String[] args) is where every java program starts,
	// so you will memorize it fast!
	public static void main(String[] args) {
		new BasicGame();
	}

}
