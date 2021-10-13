

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
		
		if(key == KeyEvent.VK_P) Driver.paused = !Driver.paused;
		
		if(e.isShiftDown()) handleShift(e);
		else {
			if(key == KeyEvent.VK_ENTER) Driver.reset();
			
			if(Driver.player.alive) handlePlayerInputs();
		}
		
	}

	private void handlePlayerInputs() {
		if(key == KeyEvent.VK_LEFT) Driver.player.left = true;
		if(key == KeyEvent.VK_RIGHT) Driver.player.right = true;
		if(key == KeyEvent.VK_UP) {
			Driver.player.up = true;
			Driver.player.upPressed();
		}
		if(key == KeyEvent.VK_DOWN) {
			Driver.player.down = true;
			Driver.player.downPressed();
		}
		
		if(key == KeyEvent.VK_S) Driver.player.sPressed();
		if(key == KeyEvent.VK_D) Driver.player.dPressed();
		
		if(key == KeyEvent.VK_SPACE) Driver.player.spacePressed();	
	}


	private void handleShift(KeyEvent e) {
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) Driver.player.left = false;
		if(key == KeyEvent.VK_RIGHT) Driver.player.right = false;
		if(key == KeyEvent.VK_UP) Driver.player.up = false;
		if(key == KeyEvent.VK_DOWN) Driver.player.down = false;
	
	}

}
