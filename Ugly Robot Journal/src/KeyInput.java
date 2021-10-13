

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	Game game;
	public KeyInput(Game game){
		this.game = game;
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		char c = e.getKeyChar();
	
		if(key == KeyEvent.VK_ESCAPE) {
			Journal.save();
			System.exit(1);
		}
		if(key == KeyEvent.VK_F1) game.debug = !game.debug;
		
		if(e.isControlDown()) {
			
			if(key == KeyEvent.VK_F) Journal.nextFont();
			if(key == KeyEvent.VK_OPEN_BRACKET) Journal.changeFontSize(-1);
			if(key == KeyEvent.VK_CLOSE_BRACKET) Journal.changeFontSize(1);
			
		}
		
		if(e.isShiftDown()) {
			
			if(key == KeyEvent.VK_ENTER) {
				Journal.save();
				Journal.dateStart = -10;
				Journal.entryY = Journal.entryYBase;
				Journal.entryString = "";
				Journal.load();
			}
			if(key == KeyEvent.VK_UP) {
				Journal.save();
				Journal.dateStart --;
				Journal.entryY = Journal.entryYBase;
				Journal.entryString = "";
				Journal.load();
			}
			if(key == KeyEvent.VK_DOWN) {
				Journal.save();
				Journal.dateStart ++;
				Journal.entryY = Journal.entryYBase;
				Journal.entryString = "";
				Journal.load();
			}
			
			
		}
		
		else {
			if(key == KeyEvent.VK_ENTER) Journal.save();
			if(key == KeyEvent.VK_UP) Journal.textUp = true;
			if(key == KeyEvent.VK_DOWN) Journal.textDown = true;
		}
		
		if(Journal.stillLoading) {
			if(key == KeyEvent.VK_ENTER) {
				Journal.stillLoading = false;
				Journal.loading = Journal.loadingMax;
			}
		}
		
			
		else if(key == KeyEvent.VK_BACK_SPACE){
			if(Journal.entryString.length()>0)
				Journal.entryString = Journal.entryString.substring(0, Journal.entryString.length()-1);
		}
			
		else if((c+"").matches("[ -~\\n]")) Journal.entryString+=c;
	
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) Journal.textUp = false;
		if(key == KeyEvent.VK_DOWN) Journal.textDown = false;
	}

}
