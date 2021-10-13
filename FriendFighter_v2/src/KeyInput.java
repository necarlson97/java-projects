

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
		
		playerOneKeyPressed(key);
		
	}
	
	private void playerOneKeyPressed(int key) {
		Fighter player = FriendFighter.player1;
		
		if(key == KeyEvent.VK_D && player.myAction != player.attackSet.get(0) 
								&& player.myAction != player.attackSet.get(1)){
			player.myAction = player.attackSet.get(0);
			player.frameStack.clear();
		}
			
			
		if(key == KeyEvent.VK_S && player.myAction != player.attackSet.get(1)) {
			player.myAction = player.attackSet.get(1);
			player.frameStack.clear();
		}
			
			
		if(key == KeyEvent.VK_LEFT)
			FriendFighter.player1.left = true;
			
		if(key == KeyEvent.VK_RIGHT)
			FriendFighter.player1.right = true;
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		playerOneKeyReleased(key);
		
	}


	private void playerOneKeyReleased(int key) {
		Fighter player = FriendFighter.player1;
		
		if(key == KeyEvent.VK_LEFT)
			FriendFighter.player1.left = false;
			
		if(key == KeyEvent.VK_RIGHT)
			FriendFighter.player1.right = false;
	}
}
