package hanoi;

import structures.LinkedStack;

/**
 * A {@link StackBasedHanoiPeg} is an implementation of {@link HanoiPeg}.
 * 
 * @author jcollard
 */
public class StackBasedHanoiPeg implements HanoiPeg {

	/**
	 * Creates a new {@link StackBasedHanoiPeg} that has no rings.
	 */
	
	private LinkedStack<HanoiRing> rings;
	private int size;
	
	public StackBasedHanoiPeg() {
		this.rings = new LinkedStack<HanoiRing>();
	}

	@Override
	public void addRing(HanoiRing ring) throws IllegalHanoiMoveException {
		if( hasRings() && rings.peek().getSize() <= ring.getSize()){
			throw new IllegalHanoiMoveException("Tied to place larger disc on smaller");
		}
		size++;
		rings.push(ring);
	}

	@Override
	public HanoiRing remove() throws IllegalHanoiMoveException {
		if( !hasRings() ) throw new IllegalHanoiMoveException("Tried to remove from empty peg");
		size--;
		return rings.pop();
	}

	@Override
	public HanoiRing getTopRing() throws IllegalHanoiMoveException {
		if( !hasRings() ) throw new IllegalHanoiMoveException("Tried to view empty peg");	
		return rings.peek();
	}

	@Override
	public boolean hasRings() {
		return size>0;
	}
}
