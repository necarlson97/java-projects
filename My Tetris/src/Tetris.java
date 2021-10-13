import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Tetris extends Game{
	
	TetrisBoard board;
	Menu menu;
	Music music;
	
	int rows = 20;
	int cols = 10;
	
	boolean paused = false;
	
	public static void main(String args[]) {
		new Tetris();
	}

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		menu = new Menu(this);
		music = new Music();
	}

	@Override
	void runGame() {
		if(board != null && !paused) board.run();
	}
	
	void startGame() {
		board = new TetrisBoard(rows, cols, this);
		menu = null;
	}
	
	void endGame() {
		board = null;
		menu = new Menu(this);
	}
	
	@Override
	void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_R) startGame();
		if(key == KeyEvent.VK_P) paused = !paused;
		if(key == KeyEvent.VK_M) music.mute();
		if(board != null && !paused) {
			switch(key) {
				case KeyEvent.VK_DOWN: board.fallingPiece.down = true; break;
				case KeyEvent.VK_LEFT: board.fallingPiece.moveLeft(); break;
				case KeyEvent.VK_RIGHT: board.fallingPiece.moveRight(); break;
				case KeyEvent.VK_SPACE: board.fallingPiece.turn(); break;
			}
			if(e.isShiftDown()) {
				switch(key) {
					case KeyEvent.VK_DOWN: board.fallingPiece.slam(); break;
					case KeyEvent.VK_LEFT: board.shiftLeft = true; break;
					case KeyEvent.VK_RIGHT: board.shiftRight = true; break;
				}
			}
		} else if(menu != null) {
			switch(key) {
				case KeyEvent.VK_SPACE: menu.space();
			}
		}
	}
	
	@Override
	void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(board != null) {
			if(key == KeyEvent.VK_DOWN) board.fallingPiece.down = false;
			if(key == KeyEvent.VK_LEFT) board.shiftLeft = false;
			if(key == KeyEvent.VK_RIGHT) board.shiftRight = false;
		}
			
	}

	@Override
	void renderGame(Graphics2D g) {		
		if(board!=null) board.render(g);
		else if(menu!=null) menu.render(g);
	}

}
