

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
		if(key == KeyEvent.VK_ENTER) {
			HateBot.inputString = workingString;
			workingString = ">";
		}
		else if(key == KeyEvent.VK_BACK_SPACE && !workingString.equals(">")) 
			workingString = workingString.substring(0, workingString.length()-1);
		else if(Character.isDefined(c)) workingString+=c;
		HateBot.workingMessage.fullString = workingString;
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}
