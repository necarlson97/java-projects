

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_P && FriendFighter.running) FriendFighter.running = false;
		else if(key == KeyEvent.VK_P) FriendFighter.running = true;
		
//		if(key == KeyEvent.VK_S || key == KeyEvent.VK_D 
//				|| key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) FriendFighter.player1.setInactive();
		
		if(key == KeyEvent.VK_D) {
			FriendFighter.player1.heavyAttack = -2;
			FriendFighter.player1.lightAttack = -1;
		}
			
		if(key == KeyEvent.VK_A && FriendFighter.player1.heavyAttack == -2) 
			FriendFighter.player1.heavyAttack = -1;
		
		if(key == KeyEvent.VK_LEFT)
			FriendFighter.player1.left = true;
			
		if(key == KeyEvent.VK_RIGHT)
			FriendFighter.player1.right = true;
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT)
			FriendFighter.player1.left = false;
			
		if(key == KeyEvent.VK_RIGHT)
			FriendFighter.player1.right = false;

	}
}
