import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Display extends Engine2D{

	String str = "";
	
	public static void main(String[] args) {
		new Display(); 
	}

	@Override
	public void setup() {
		PrintBox.setLoc(100, 100);
	}

	@Override
	public void update() {
		str += "Yes \n";
		PrintBox.setString(str);
	}

	@Override
	void render(Graphics2D g) {
		PrintBox.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
