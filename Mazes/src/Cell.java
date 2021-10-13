import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	int row;
	int col;
	
	int width;
	int height;
	
	Color color = Color.gray;
	
	Cell up;
	Cell down;
	Cell left;
	Cell right;
	
	public Cell(int r, int c, Maze m) {
		row = r;
		col = c;
		width = m.cellWidth;
		height = m.cellHeight;
	}
	
	public void run() {
		color = Color.white;
	}
	
	public void render(Graphics g) {
		int x = col*width;
		int y = row*height;
		
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.black);
		if(up == null) g.drawRect(x, y, width, 1);
		if(down == null) g.drawRect(x, y+height-1, width, 1);
		if(left == null) g.drawRect(x, y, 1, height);
		if(right == null) g.drawRect(x+width-1, y, 1, height);
	}
	
	public boolean hasOpenDoor() {
		return !(left==null && right ==null && up==null && down==null);
	}

}
