

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
		Player player = SpaceTrails.player;
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.up = true;
		else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.left = true;
		else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.down = true;
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.right = true;
		
		if(key == KeyEvent.VK_E) player.tryToInteract();
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		Player player = SpaceTrails.player;
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) player.up = false;
		else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.left = false;
		else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) player.down = false;
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.right = false;
	}
}
