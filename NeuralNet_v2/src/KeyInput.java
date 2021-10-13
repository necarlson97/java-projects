

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	String workingString = ">";
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		char c = e.getKeyChar();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}
