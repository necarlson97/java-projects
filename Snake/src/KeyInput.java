


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_P) SnakeGame.running = !SnakeGame.running;
		if(key == KeyEvent.VK_0) SnakeGame.debug = !SnakeGame.debug;
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) SnakeGame.player.left = true;
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) SnakeGame.player.right = true;
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) SnakeGame.player.up = true;
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) SnakeGame.player.down = true;
		
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
//		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) SnakeGame.player.left = false;
//		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) SnakeGame.player.right = false;
//		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) SnakeGame.player.up = false;
//		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) SnakeGame.player.down = false;
	}

}
