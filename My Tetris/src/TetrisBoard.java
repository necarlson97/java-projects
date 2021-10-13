import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TetrisBoard {
	
	Random rand = Game.r;
	
	Tetris tetris;
	
	int rows;
	int cols;
	
	Color[][] matrix;
	
	int charSize;
	int xMargin;
	int yMargin;
	int boardWidth;
	int boardHeight;
	
	Font font;
	
	Piece nextPiece;
	Piece fallingPiece;
	Piece ghost;
	
	int score = 0;
	
	boolean shiftLeft = false;
	boolean shiftRight = false;
	
	String block = "%";
	String backing = " ";
	String border = ".";
	
	List<Character> charBag = new LinkedList<Character>();
	
	public TetrisBoard(int r, int c, Tetris t) {
		tetris = t;
		rows = r;
		cols = c;
		matrix = new Color[rows][cols];
		charSize = (int) ((Game.windowHeight*.8) / rows);
		font = new Font("Press Start 2p", Font.PLAIN, charSize);
		
		boardWidth = charSize * cols;
		boardHeight = charSize * rows;
		
		xMargin = Game.windowWidth/2 - (boardWidth/2);
		yMargin = Game.windowHeight/2 - (boardHeight/2);
		
		nextPiece = nextPiece();
		newPiece();
	}
	
	public void run() {
		
		if(fallingPiece != null) {
			fallingPiece.run();
			if(shiftLeft) fallingPiece.moveLeft();
			if(shiftRight) fallingPiece.moveRight();
			
			ghost = new Ghost(fallingPiece);
			ghost.run();
		}
		
		checkRows();
		
	}

	private void checkRows() {
		
		for(int c=0; c<cols; c++) {
			if(matrix[0][c] != null) tetris.endGame();
		}
		
		for(int r=0; r<rows; r++) {
			boolean full = true;
			boolean black = true;
			for(int c=0; c<cols; c++) {
				if(matrix[r][c] == null) {
					full = false;
					black = false;
				}
				else if(!matrix[r][c].equals(Color.white)) black = false;
			}
			if(full) brightenRow(r);
			if(black) removeRow(r);
		}
	}
	
	private void brightenRow(int row) {
		for(int c=0; c<cols; c++) {
			matrix[row][c] = brighten(matrix[row][c]);
		}
	}
	
	public Color brighten(Color c) {
		
		float[] hsbVals = new float[3];
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbVals);
		
		if(hsbVals[1] > 0) hsbVals[1] -= .002;
		else hsbVals[1] = 0;
		
		return new Color(Color.HSBtoRGB(hsbVals[0], hsbVals[1], hsbVals[2])).brighter();
	}
	
	public Color brighten(Color c, int repeat) {
		
		for(int i=0; i<repeat; i++) {
			c = brighten(c);
		}
		
		return c;
	}

	private void removeRow(int row) {
		for(int r=row; r>1; r--) {
			for(int c=0; c<cols; c++) {
				matrix[r][c] = matrix[r-1][c];
			}
		}
		score++;
	}

	public void newPiece() {
		nextPiece.row = 0;
		nextPiece.col = cols/2;
		fallingPiece = nextPiece;
		nextPiece = nextPiece();
	}
	
	private Piece nextPiece() {
		
		if(charBag.isEmpty()) {
			charBag = new LinkedList<Character>(Arrays.asList('I', 'T', 'O', 'L', 'J', 'S', 'Z'));
			Collections.shuffle(charBag);
		}
		
		return new Piece(charBag.remove(0), this);
	}

	public void render(Graphics g) {
		
		g.setFont(font);
		
		renderBoard(g);
		
		if(ghost != null) ghost.render(g);
		
		if(fallingPiece != null) fallingPiece.render(g);
		if(nextPiece != null) nextPiece.render(g);
		
		g.setColor(Color.white);
		g.drawString(""+score, xMargin + boardWidth + charSize, yMargin);
		
	}

	private void renderBoard(Graphics g) {
		int intX = xMargin;
		int intY = yMargin;
		
		
		for(int r=0; r<rows; r++) {
			intX = xMargin;
			g.setColor(Color.white);
			g.drawString(border, intX-charSize, intY);
			for(int c=0; c<cols; c++) {
				if(matrix[r][c] != null) {
					g.setColor(matrix[r][c]);
					g.drawString(block, intX, intY);
				}
				else {
					g.setColor(Color.darkGray);
					g.drawString(backing, intX, intY);
				}
				intX += charSize;
			}
			g.setColor(Color.white);
			g.drawString(border, intX, intY);
			intY += charSize;
		}
		String bottom = "";
		for(int i=0; i<cols; i++) bottom += border;
		g.drawString(bottom, xMargin, intY);
		
	}

}
