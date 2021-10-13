import java.util.LinkedList;

public class Path extends Action{
	
	boolean foundPath = false;
	LinkedList<Move> moves = new LinkedList<Move>();
	int searches = 0;

	public Path(Ant a, int x, int y) {
		super(a, "path", x, y);
	}
	
	private int addWeights(LinkedList<Move> inputs){
		int w = 0;
		int tempX = inputs.getFirst().destX;
		int tempY = inputs.getFirst().destY;
		for(Move m : inputs) {
			
			w += distanceBetween(m, tempX, tempY);
			tempX = m.destX;
			tempY = m.destY;
		}
		return w;
	}
	
	int distanceBetween(Move first, int x, int y){
		return (int) Math.sqrt(Math.pow((first.destX-x), 2) 
				+ Math.pow((first.destY-y), 2));
	}
	
	public void tryPath(){
		
	}


}
