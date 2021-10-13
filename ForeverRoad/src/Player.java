import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player {
	
	ForeverRoad fr;
	
	boolean left;
	boolean right;
	boolean up;
	boolean down;
	
	float turnChange = (float) 0.1;
	float accelChange = (float) 0.1;
	
	public Player(ForeverRoad fr) {
		this.fr = fr;
	}
	
	public void update() {
		if(fr.car != null && !fr.car.totalled) {
			if(left) fr.car.turn -= turnChange;
			else if(right) fr.car.turn += turnChange;
			if(up) fr.car.accel -= accelChange;
			else if(down) fr.car.accel += accelChange;
			
		}
	}
	
	public void render(Graphics g) {
		
	}
	
	void carKeysPressed(int key) {
		if(key == KeyEvent.VK_LEFT) left = true;
		if(key == KeyEvent.VK_RIGHT)right = true;
		if(key == KeyEvent.VK_UP) up = true;
		if(key == KeyEvent.VK_DOWN) down = true;
	}
	
	void carKeysReleased(int key) {
		if(key == KeyEvent.VK_LEFT) left = false;
		if(key == KeyEvent.VK_RIGHT)right = false;
		if(key == KeyEvent.VK_UP) up = false;
		if(key == KeyEvent.VK_DOWN) down = false;
	}

}
