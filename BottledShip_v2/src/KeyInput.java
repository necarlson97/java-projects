

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
	
		Ship ship = BottledShip.ship;
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_D) BottledShip.debug = !BottledShip.debug;
		if(key == KeyEvent.VK_P && ship != null) ship.powerOn = !ship.powerOn;
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}
