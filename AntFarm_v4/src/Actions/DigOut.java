package Actions;


public class DigOut extends Action {
	
	public int row;
	public int col;

	public DigOut(int row, int col) {
		super("dig out ");
		name += "("+row+","+col+")";
		this.row = row;
		this.col = col;
	}

}
