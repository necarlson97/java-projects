

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
		
		game.keyPressed(e);
		
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		game.keyReleased(e);
	}

}
