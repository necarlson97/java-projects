

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	int key;
	char c;
	
	Game game;
	public KeyInput(Game game){
		this.game = game;
	}
	
	
	public void keyPressed(KeyEvent e){
		key = e.getKeyCode();
		c = e.getKeyChar();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_F1) game.debug = !game.debug;
		
		if(key == KeyEvent.VK_F) Game.toggleScreen = true;
		
		if(e.isShiftDown()) handleShift(e);
		else {
			if(key == KeyEvent.VK_UP) {
				Player.up = true;
				BurningHouse.checkStairs(true);
			}
			if(key == KeyEvent.VK_DOWN) {
				Player.down = true;
				BurningHouse.checkStairs(false);
			}
			
			if(key == KeyEvent.VK_LEFT) Player.left = true;
			if(key == KeyEvent.VK_RIGHT) Player.right = true;
			
			if(key == KeyEvent.VK_ENTER) BurningHouse.sparkFire();
		}
		
		
		
	}

	private void handleShift(KeyEvent e) {
		if(key == KeyEvent.VK_LEFT) BurningHouse.changeFireSpeed(-1);
		if(key == KeyEvent.VK_RIGHT) BurningHouse.changeFireSpeed(1);	
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) Player.up = false;
		if(key == KeyEvent.VK_DOWN) Player.down = false;
		
		if(key == KeyEvent.VK_LEFT) Player.left = false;
		if(key == KeyEvent.VK_RIGHT) Player.right = false;
	}

}
