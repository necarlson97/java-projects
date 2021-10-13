import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Maze extends Engine2D {
	
	public class Wall {
		
		int wallx;
		int wally;
		int wallheight;
		int wallwidth;
		
		Wall(int x, int y, int w, int h) {
			wallx = x;
			wally = y;
			wallheight = h;
			wallwidth = w;
		}
		
		void render(Graphics2D g) {
			g.setColor(Color.gray);
			g.fillRect(wallx, wally, wallwidth, wallheight);
		}
		
		
	}

	
	Wall wall1 = new Wall(80, 80, 200, 50);
	Wall wall2 = new Wall(100, 200, 30, 50);
	Wall wall3 = new Wall(500, 80, 90, 50);

	// Anything we need to do before the game starts
	@Override
	public void setup() {
		
	}

	// Runs once every frame to move things around in game
	@Override
	public void update() { 
		
	}

	
	// Runs once every frame to draw stuff to the screen
	@Override
	void render(Graphics2D g) {
		wall1.render(g);
		wall2.render(g);
		wall3.render(g);
	}

	// Runs when a key is pressed
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	// Runs when a key is released
	@Override
	public void keyReleased(KeyEvent e) {

		
	}

	// Starts the game
	public static void main(String[] args) {
		new Maze();
	}

}
