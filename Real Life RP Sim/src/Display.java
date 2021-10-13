import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Display extends Engine2D{
	Simulator sim;
	
	@Override
	public void setup() {
		sim = new Simulator();
	}

	@Override
	public void update() {
		sim.update();
	}

	@Override
	void render(Graphics2D g) {
		sim.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_SPACE) sim.takeTurn();
		if(key == KeyEvent.VK_E) sim.everRun = !sim.everRun;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
