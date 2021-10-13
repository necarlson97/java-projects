
public class Ghost extends Piece{
	
	public Ghost(Piece p) {
		super(p.type, p.row, p.col, p.board);

		color = board.brighten(p.color, 10);
		shape = p.shape;
	}
	
	@Override 
	public void run() {
		while(!pieceBelow()) row++;
	}

}
