

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		char c = e.getKeyChar();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_F1) TwitterTyper.debug = !TwitterTyper.debug;
		if(key == KeyEvent.VK_DOWN) TwitterTyper.speedUp = true;
		
		if(key == KeyEvent.VK_ENTER) TwitterTyper.inputChar = c;
		
		else if(Character.isDefined(c) || c == ' ') {
			TwitterTyper.inputChar = Character.toLowerCase(c);
			TwitterTyper.changeChar = true;
		}
		
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_DOWN) TwitterTyper.speedUp = false;
	}

}
