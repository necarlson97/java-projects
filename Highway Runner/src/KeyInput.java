

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
		
		if(key == KeyEvent.VK_UP) HighwayRunner.player.up = true;
		if(key == KeyEvent.VK_DOWN) HighwayRunner.player.down = true;
		
		if(key == KeyEvent.VK_LEFT) HighwayRunner.player.left = true;
		if(key == KeyEvent.VK_RIGHT) HighwayRunner.player.right = true;
		
		if(key == KeyEvent.VK_ENTER) HighwayRunner.player = new Player();
		
		if(key == KeyEvent.VK_SHIFT) HighwayRunner.player.boost = true;
		
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) HighwayRunner.player.up = false;
		if(key == KeyEvent.VK_DOWN) HighwayRunner.player.down = false;
		
		if(key == KeyEvent.VK_LEFT) HighwayRunner.player.left = false;
		if(key == KeyEvent.VK_RIGHT) HighwayRunner.player.right = false;
		
		if(key == KeyEvent.VK_SHIFT) HighwayRunner.player.boost = false;
	}

}
