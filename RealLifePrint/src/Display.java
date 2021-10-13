import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Display extends Engine2D{
	
	Card card;
	SheetsInfo si;
	
	@Override
	public void setup() {
		si = new SheetsInfo();
	}

	@Override
	public void update() {
		
	}

	@Override
	void render(Graphics2D g) {
		if(card != null) card.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
