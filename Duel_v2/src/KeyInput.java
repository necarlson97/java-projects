

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		else if(key == 0);
		else if(key == KeyEvent.VK_F1){
			if(Duel.helpCount > 500) Duel.helpCount = 0;
			else Duel.helpCount = 500;
		}
		else if(key == KeyEvent.VK_D && Duel.player.alive){
			Duel.player.gun = true;
		}
		else if(Duel.player.alive && Duel.enemy.alive && Duel.player.gun) {
			Duel.shooter = Duel.player;
			Duel.victim = Duel.enemy;
		}
			
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			Duel.player.gun = false;
		}
	}

}
