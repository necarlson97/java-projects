import java.awt.Color;

public class Dirt extends GameObject{

	public Dirt(int row, int col) {
		super(row, col, Color.darkGray);
		moveCost = 5;
	}

}
