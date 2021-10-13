

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		//To see if someone tried contacting the computer
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);

		if(key == KeyEvent.VK_W) TrappedAiSim.watched=true;
		else if(key == KeyEvent.VK_I) TrappedAiSim.watched=false;
		
		else TrappedAiSim.keyboardTouched=true;
			
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) TrappedAiSim.watched=false;
		else TrappedAiSim.keyboardTouched=false;
		
	}

}
