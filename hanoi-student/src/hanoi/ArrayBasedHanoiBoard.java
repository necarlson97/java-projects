package hanoi;

/**
 * A {@link ArrayBasedHanoiBoard} is a simple implementation of
 * {@link HanoiBoard}
 * 
 * @author jcollard
 * 
 */
public class ArrayBasedHanoiBoard implements HanoiBoard {
	/**
	 * Creates a {@link ArrayBasedHanoiBoard} with three empty {@link HanoiPeg}s
	 * and {@code n} rings on peg 0.
	 */
	
	private StackBasedHanoiPeg[] pegArray = new StackBasedHanoiPeg[3];
	
	public ArrayBasedHanoiBoard(int n) {
		if(n<0) throw new IllegalArgumentException("Cannot create board with negative moves");
		for(int i=0; i<pegArray.length; i++){
			pegArray[i] = new StackBasedHanoiPeg();
		}
		for(int i=0; i<n; i++){
			pegArray[0].addRing(new HanoiRing(n-i));
		}
		
	}

	@Override
	public void doMove(HanoiMove move) throws IllegalHanoiMoveException {
		if (!isLegalMove(move)) {
			throw new IllegalHanoiMoveException("Could not perform illegal move.");
		}
		int from = move.getFromPeg();
		int to =move.getToPeg();
				
		pegArray[to].addRing(pegArray[from].remove());
		
	}

	@Override
	public boolean isSolved() {
		return ( !pegArray[0].hasRings() && !pegArray[1].hasRings() );
	}

	@Override
	public boolean isLegalMove(HanoiMove move) {
		int from = move.getFromPeg();
		int to =move.getToPeg();
		
		if( !pegArray[from].hasRings() ) return false;
		if( !pegArray[to].hasRings() ) return true;
		
		return( to!=from && 
				pegArray[to].getTopRing().getSize() >= pegArray[from].getTopRing().getSize() );
	}
}
