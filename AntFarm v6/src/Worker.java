import java.awt.Color;

public class Worker extends Ant {
	
	Cell goalA = new Cell(50, 50);
	Cell goalB = new Cell(20, 80);
	Cell goalC = new Cell(30, 80);

	public Worker(int row, int col) {
		super(row, col, Color.orange);	
	}

	@Override
	public void run() {
		System.out.println("Running");
		Farm.get(goalA).color = Color.green;
		pathTo(goalA);
		
	}

}
