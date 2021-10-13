

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		char c = e.getKeyChar();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_F1) DotDeathmatch.debug = !DotDeathmatch.debug;
		
		if(key == KeyEvent.VK_UP) DotDeathmatch.player.forward = true;
		if(key == KeyEvent.VK_LEFT) DotDeathmatch.player.left = true;
		if(key == KeyEvent.VK_RIGHT) DotDeathmatch.player.right = true;
		if(key == KeyEvent.VK_SPACE) DotDeathmatch.player.shoot = true;
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) DotDeathmatch.player.forward = false;
		if(key == KeyEvent.VK_LEFT) DotDeathmatch.player.left = false;
		if(key == KeyEvent.VK_RIGHT) DotDeathmatch.player.right = false;
	}

}
