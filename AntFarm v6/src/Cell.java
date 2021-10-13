import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	int row;
	int col;

	public Cell(int _row, int _col) {
		row = _row;
		col = _col;
	}
	
	public Cell(Cell cell, int r, int c) {
		row = cell.row + r;
		col = cell.col + c;
	}
	
	public boolean inBounds() {
		return (row >= 0 && row < Farm.rows && 
				col >= 0 && col < Farm.cols);
				
	}
	
	public void move(int r, int c) {
		if(!inBounds()) {
			System.out.println(this+" tried to move "+r+","+c);
			return;
		}
		row += r;
		col += c;
			
	}
	
	public GameObject collides(int r, int c) {
		Cell checkCell = new Cell(row+r, col+c);
		if(Farm.objs.containsKey(checkCell))
			return Farm.objs.get(checkCell);
		else 
			return null;
	}
	
	public String toString() {
		return "("+row+","+col+")";
	}

	public void render(Graphics g, Color color) {
		g.setColor(color);
		int x = col * Farm.pixelSize;
		int y = row * Farm.pixelSize;
		g.fillRect(x, y, Farm.pixelSize, Farm.pixelSize);
		g.setColor(Color.white);
		g.drawRect(x, y, Farm.pixelSize, Farm.pixelSize);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
