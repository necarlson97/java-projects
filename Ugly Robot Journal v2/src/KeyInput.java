

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
	
		if(key == KeyEvent.VK_ESCAPE) {
			if(Journal.entry != null) Journal.entry.save();
			System.exit(1);
		}
		if(key == KeyEvent.VK_F1) game.debug = !game.debug;
		
		if( Journal.loadingScreen != null ) handleLoadingOnly(e);
		else handleLoadedOnly(e);
		
		if(e.isControlDown()) {
			
			if(key == KeyEvent.VK_F) Journal.nextFont();
			if(key == KeyEvent.VK_OPEN_BRACKET) Journal.changeFontSize(-1);
			if(key == KeyEvent.VK_CLOSE_BRACKET) Journal.changeFontSize(1);
			
		}	
		
	}


	private void handleLoadingOnly(KeyEvent e)  {
		if(e.isShiftDown()) {
			if(key == KeyEvent.VK_ENTER) {
				Journal.sidebar = new DateSidebar();
				Journal.entry = new Entry(Journal.sidebar.selectedDateName());
				Journal.loadingScreen.loading = Journal.loadingScreen.loadingMax;
			}
		}
		else if(key == KeyEvent.VK_ENTER) {
			Journal.sidebar = new DateSidebar();
			Journal.entry = new Entry(Journal.sidebar.selectedDateName());
			Journal.loadingScreen = null;
		}
	}
	
	private void handleLoadedOnly(KeyEvent e) {
		if(e.isShiftDown()) {
			
			if(key == KeyEvent.VK_ENTER) {
				Journal.sidebar.centerDate();
			}
			if(key == KeyEvent.VK_UP) {
				Journal.sidebar.changeDate(-1);
			}
			if(key == KeyEvent.VK_DOWN) {
				Journal.sidebar.changeDate(1);
			}
			
			if(key == KeyEvent.VK_LEFT) Journal.entry.changeContentIndex(-10);
			if(key == KeyEvent.VK_RIGHT) Journal.entry.changeContentIndex(10);
			
			
		}
		
		else {
			if(key == KeyEvent.VK_ENTER) Journal.entry.save();
			if(key == KeyEvent.VK_UP) Journal.entry.textUp = true;
			if(key == KeyEvent.VK_DOWN) Journal.entry.textDown = true;
			
			if(key == KeyEvent.VK_LEFT) Journal.entry.changeContentIndex(-1);
			if(key == KeyEvent.VK_RIGHT) Journal.entry.changeContentIndex(1);
		}
		
		if(key == KeyEvent.VK_BACK_SPACE) Journal.entry.deleteChar();
		if((c+"").matches("[ -~\\n]")) Journal.entry.addChar(c);
		
	}


	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) Journal.entry.textUp = false;
		if(key == KeyEvent.VK_DOWN) Journal.entry.textDown = false;
	}

}
