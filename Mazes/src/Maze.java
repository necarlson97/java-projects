import java.awt.Graphics2D;

public abstract class Maze {
	
	int rows;
	int cols;
	
	Cell[][] cells;
	
	int cellWidth;
	int cellHeight;
	
	public Maze(int r, int c) {
		rows = r;
		cols = c;
		
		cellWidth = Game.windowWidth/cols;
		cellHeight = Game.windowHeight/rows;
		
		cells = new Cell[rows][cols];
	}

	public void run() {
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				cells[r][c].run();
			}
		}
	}

	public void render(Graphics2D g) {
		for(Cell[] row : cells) {
			for(Cell c : row) {
				if(c != null) c.render(g);
			}
		}
	}
	
	public void openDoor(Cell c1, Cell c2) {
		if(c1.row == c2.row) {
			if(c1.col == c2.col-1) {
				c1.right = c2;
				c2.left = c1;
			} else if(c1.col == c2.col+1) {
				c1.left = c2;
				c2.right = c1;
			} else System.out.println("Error connecting "+c1+" to "+c2);
		} else if(c1.col == c2.col) {
			if(c1.row == c2.row-1) {
				c1.up = c2;
				c2.down = c1;
			} else if(c1.row == c2.row+1) {
				c1.down = c2;
				c2.up = c1;
			} else System.out.println("Error connecting "+c1+" to "+c2);
		} else System.out.println("Error connecting "+c1+" to "+c2);
	}

}
