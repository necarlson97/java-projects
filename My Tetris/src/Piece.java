import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Piece {
	
	LinkedList<Block> shape;
	char type;
	Color color;
	int row;
	int col;
	
	TetrisBoard board;
	
	final int MAXDELAY = 50;
	int delayLevel = MAXDELAY;
	int currDelay = delayLevel;
	
	static boolean up;
	static boolean down;
	static boolean left;
	static boolean right;
	
	String display = "$";
	
	public Piece(char t, int r, int c, TetrisBoard b) {
		this.row = r;
		this.col = c;
		
		board = b;
		type = t;
		color = setColor(t);
		shape = setShape(t);
	}
	
	public Piece(char t, TetrisBoard b) {
		// The starting location for an 'on deck' piece
		this.row = 2;
		this.col = -4;
		
		board = b;
		type = t;
		color = setColor(t);
		shape = setShape(t);
	}
	
	public void run() {	
		if(down) currDelay = 1;
		else currDelay = delayLevel;
		
		if(delayLevel > 1 && board.score <= MAXDELAY) delayLevel = (MAXDELAY - board.score);
		
		if(Game.count%currDelay == 0) {
			if(pieceBelow()) landPiece();
			else row ++;
		}
	}
	
	public void slam() {
		while(!pieceBelow()) row++;
	}
	
	public void moveLeft() {
		if(!pieceLeft()) col--;
	}
	
	public void moveRight() {
		if(!pieceRight()) col++;
	}
	
	public void turn() {
		for(Block b : shape) {
			int tempCol = b.col;
			b.col = b.row * -1;
			b.row = tempCol;
			
			if(row+b.row >= board.rows) row--;
			while(col+b.col >= board.cols-1) col--;
			while(col+b.col < 1) col++;
		}
		
	}
	
	boolean pieceBelow() {
		return collision(1, 0);
	}
	
	boolean pieceLeft() {
		return collision(0, -1);
	}
	
	boolean pieceRight() {
		return collision(0, 1);
	}
	
	private boolean collision(int searchR, int searchC) {		
		for(Block b : shape) {
			int r = row+b.row+searchR;
			int c = col+b.col+searchC;
			
			if(r < 0) continue;
			if(r >= board.rows || c < 0 || c >= board.cols) return true;
			if(board.matrix[r][c] != null ) return true;
		}
		return false;
	}

	private void landPiece() {
		down = false;
		
		for(Block b : shape) {
			int r = row + b.row;
			int c = col + b.col;
			
			if(r < 0) {
				board.tetris.endGame();
				return;
			}
			board.matrix[r][c] = color.darker().darker();
		}
		board.tetris.music.playClick();
		board.newPiece();	
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		for(Block b : shape) {
			
			int drawX = board.xMargin+(b.col+col)*board.charSize;
			int drawY = board.yMargin+(b.row+row)*board.charSize;
			
			g.drawString(display, drawX, drawY);
		}
	}

	private Color setColor(char t) {
		if(t == 'I') return Color.cyan;
		else if(t == 'O') return Color.yellow;
		else if(t == 'T') return Color.magenta;
		else if(t == 'J') return Color.orange;
		else if(t == 'L') return new Color(100, 100, 255); // A lighter blue
		else if(t == 'S') return Color.green;
		else if(t == 'Z') return Color.red;
		else return Color.gray;
	}

	private LinkedList<Block> setShape(char t) {
		
		shape = new LinkedList<Block>();
			
		if(t == 'I') {
			shape.add(new Block(-1, 0, color));
			shape.add(new Block(0, 0, color));
			shape.add(new Block(1, 0, color));
			shape.add(new Block(2, 0, color));
		}
		else if(t == 'O') {
			shape.add(new Block(0, 0, color));
			shape.add(new Block(0, 1, color));
			shape.add(new Block(1, 0, color));
			shape.add(new Block(1, 1, color));
		}
		else if(t == 'T') {
			shape.add(new Block(-1, 0, color));
			shape.add(new Block(0, 0, color));
			shape.add(new Block(1, 0, color));
			shape.add(new Block(0, 1, color));
		}
		
		else if(t == 'L') {
			shape.add(new Block(-2, 0, color));
			shape.add(new Block(-1, 0, color));
			shape.add(new Block(0, 0, color));
			shape.add(new Block(0, 1, color));
		}
		else if(t == 'J') {
			shape.add(new Block(-2, 0, color));
			shape.add(new Block(-1, 0, color));
			shape.add(new Block(0, 0, color));
			shape.add(new Block(0, -1, color));
		}
		
		else if(t == 'S') {
			shape.add(new Block(0, 1, color));
			shape.add(new Block(0, 0, color));
			shape.add(new Block(1, 0, color));
			shape.add(new Block(1, -1, color));
		}
		else if(t == 'Z') {
			shape.add(new Block(0, -1, color));
			shape.add(new Block(0, 0, color));
			shape.add(new Block(1, 0, color));
			shape.add(new Block(1, 1, color));
		}
		
		return shape;
	}

	public String toString() {
		return type+" r: "+row+" c:"+col;
	}
	
}
