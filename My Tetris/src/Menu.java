import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	Font headerFont;
	Font bodyFont;
	
	Tetris tetris;
	
	public Menu(Tetris t) {
		tetris = t;
		headerFont = new Font("Press Start 2p", Font.PLAIN, (int) (Game.windowHeight * .05));
		bodyFont = new Font("Press Start 2p", Font.PLAIN, (int) (Game.windowHeight * .02));
	}
	
	public void space() {
		tetris.startGame();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		int xMargin = (int) (Game.windowWidth * .05);
		int yMargin;
		
		g.setFont(bodyFont);
		yMargin = (int) (Game.windowWidth * .6);
		g.drawString("◀ ▶ = left, right", xMargin, yMargin);
		yMargin = (int) (Game.windowWidth * .63);
		g.drawString("▼ = faster", xMargin, yMargin);
		yMargin = (int) (Game.windowWidth * .66);
		g.drawString("shift ▼ = slam", xMargin, yMargin);
		yMargin = (int) (Game.windowWidth * .69);
		g.drawString("space = turn", xMargin, yMargin);
		
		int length;
		
		length = "press space to start game".length();
		yMargin = (int) (Game.windowWidth * .2);
		xMargin = (Game.windowWidth / 2) - (length*bodyFont.getSize())/2;
		g.drawString("press space to start game", xMargin, yMargin + headerFont.getSize() * 3);
		
		length = "Tetris".length();
		yMargin = (int) (Game.windowWidth * .3);
		xMargin = (Game.windowWidth / 2) - (length*headerFont.getSize())/2;
		g.setFont(headerFont);
		g.drawString("Tetris", xMargin, yMargin);
		
	}

}
