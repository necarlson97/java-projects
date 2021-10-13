
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	
	public KeyInput(){
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		char c = e.getKeyChar();
		
		Sniffer.typed += c;
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}
