import java.awt.Color;
import java.awt.Graphics;

public abstract class GameObject {

	Cell cell;
	Color color;
	int moveCost = 1;
	
	boolean gravity = false;
	
	public GameObject(Cell _cell, Color _color) {
		cell = _cell;
		color = _color;
	}
	
	public GameObject(int _row, int _col, Color _color) {
		cell = new Cell(_row, _col);
		color = _color;
	}
	
	public void update() {
		if(gravity && cell.collides(1, 0) == null)
			cell.move(1, 0);
	}
	
	public void render(Graphics g) {
		cell.render(g, color);
	}
}
