

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_R) {
			Birds.running = false;
		}
		
		Bird player = Birds.player;
		
		if(key == KeyEvent.VK_SPACE) player.flapUp = true;

	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		Bird player = Birds.player;
		
		if(key == KeyEvent.VK_SPACE) player.flapDown = true;
	}
}
