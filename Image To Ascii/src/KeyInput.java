

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	int key;
	char c;
	
	Game game;
	public KeyInput(Game game){
		this.game = game;
	}
	
	
	public void keyPressed(KeyEvent e){
		key = e.getKeyCode();
		c = e.getKeyChar();
	
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		if(key == KeyEvent.VK_F1) game.debug = !game.debug;
	
		if(key == KeyEvent.VK_UP) ImageToAscii.changeFont(-1);
		if(key == KeyEvent.VK_DOWN) ImageToAscii.changeFont(1);
		
		if(key == KeyEvent.VK_LEFT) ImageToAscii.interval--;
		if(key == KeyEvent.VK_RIGHT) ImageToAscii.interval++;
		
		if(key == KeyEvent.VK_I) ImageToAscii.renderImage = !ImageToAscii.renderImage;
		if(key == KeyEvent.VK_O) ImageToAscii.renderAscii = !ImageToAscii.renderAscii;
		
		if(key == KeyEvent.VK_OPEN_BRACKET) ImageToAscii.changeFontSize(-1);
		if(key == KeyEvent.VK_CLOSE_BRACKET) ImageToAscii.changeFontSize(1);
		
		if(key == KeyEvent.VK_ENTER) ImageToAscii.output = true;
		
	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}
