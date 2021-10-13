

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
		
		if(key == KeyEvent.VK_UP) EngineTest.player.forward = true;
		if(key == KeyEvent.VK_DOWN) EngineTest.player.backward = true;
		
		if(key == KeyEvent.VK_LEFT) EngineTest.player.left = true;
		if(key == KeyEvent.VK_RIGHT) EngineTest.player.right = true;
		
		
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) EngineTest.player.forward = false;
		if(key == KeyEvent.VK_DOWN) EngineTest.player.backward = false;
		
		if(key == KeyEvent.VK_LEFT) EngineTest.player.left = false;
		if(key == KeyEvent.VK_RIGHT) EngineTest.player.right = false;

	}

}
