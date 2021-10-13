

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
				BurningHouse.player.up = true;
				BurningHouse.player.checkStairs(true);
			}
			if(key == KeyEvent.VK_DOWN) {
				BurningHouse.player.down = true;
				BurningHouse.player.checkStairs(false);
			}
			
			if(key == KeyEvent.VK_LEFT) BurningHouse.player.left = true;
			if(key == KeyEvent.VK_RIGHT) BurningHouse.player.right = true;
			
			if(key == KeyEvent.VK_ENTER) BurningHouse.sparkFire();
			
			if(key == KeyEvent.VK_SPACE) BurningHouse.player.spacePressed();
			
			if(key == KeyEvent.VK_D) BurningHouse.player.dPressed();
			
		}
		
		
		
	}

	private void handleShift(KeyEvent e) {
		if(key == KeyEvent.VK_LEFT) BurningHouse.changeFireSpeed(-1);
		if(key == KeyEvent.VK_RIGHT) BurningHouse.changeFireSpeed(1);	
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) BurningHouse.player.up = false;
		if(key == KeyEvent.VK_DOWN) BurningHouse.player.down = false;
		
		if(key == KeyEvent.VK_LEFT) BurningHouse.player.left = false;
		if(key == KeyEvent.VK_RIGHT) BurningHouse.player.right = false;
	
	}

}
