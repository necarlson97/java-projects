
public class BreadthMaze extends Maze{
	
	int r = 0;
	int c = 0;
	
	int stage = 0;
	final int BUILDING = 0;
	final int PATHING = 1;
	
	Cell currCell = null; 

	public BreadthMaze(int r, int c) {
		super(r, c);
	}
	
	public void run() {		
		
		if(stage == BUILDING) {
			if(cells[r][c] == null) cells[r][c] = new Cell(r, c, this);
			
			r++;
			if(r >= rows) {
				r=0;
				c++;
			}
			if(c >= cols) stage ++;
		} else if(stage == PATHING) {
			if(currCell == null) currCell = cells[0][0];
			else if(!currCell.hasOpenDoor()) 
		}
		
	}

}
