import java.awt.event.KeyEvent;

public class Player extends Pilot {

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) left = true;
		if(key == KeyEvent.VK_RIGHT) right = true;
		if(key == KeyEvent.VK_UP) up = true;
		if(key == KeyEvent.VK_DOWN) down = true;
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) left = false;
		if(key == KeyEvent.VK_RIGHT) right = false;
		if(key == KeyEvent.VK_UP) up = false;
		if(key == KeyEvent.VK_DOWN) down = false;
	}
	
	

}
