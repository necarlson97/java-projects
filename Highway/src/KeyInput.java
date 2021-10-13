

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	int key;
	char c;
	
	Game game;
	
	Player player;

	public KeyInput(Game game){
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e){
		key = e.getKeyCode();
		c = e.getKeyChar();
	
		if(player == null) player = Highway.player;
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_F1) game.debug = !game.debug;
		
		if(key == KeyEvent.VK_F) Game.toggleScreen = true;
		
		if(player != null) {
			if(key == KeyEvent.VK_UP) player.up = true;
			if(key == KeyEvent.VK_DOWN) player.down = true;
			
			if(key == KeyEvent.VK_LEFT) player.left = true;
			if(key == KeyEvent.VK_RIGHT) player.right = true;
		}
		
		
		
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(player != null) {
			if(key == KeyEvent.VK_UP) player.up = false;
			if(key == KeyEvent.VK_DOWN) player.down = false;
			
			if(key == KeyEvent.VK_LEFT) player.left = false;
			if(key == KeyEvent.VK_RIGHT) player.right = false;
		}
		
	}

}
