

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		else if(key == 0);
		else if(key == KeyEvent.VK_0){
			AntFarm.debug = !AntFarm.debug;
		}
			
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}
