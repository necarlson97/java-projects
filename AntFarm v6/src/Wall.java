import java.awt.Color;

public class Wall extends GameObject{

	public Wall(int row, int col) {
		super(row, col, Color.white);
		moveCost = Integer.MAX_VALUE;
	}

}
