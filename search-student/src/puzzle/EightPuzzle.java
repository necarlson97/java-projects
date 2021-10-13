package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import search.SearchProblem;
import search.Solver;

/**
 * A class to represent an instance of the eight-puzzle.
 * 
 * The spaces in an 8-puzzle are indexed as follows:
 * 
 * 0 | 1 | 2
 * --+---+---
 * 3 | 4 | 5
 * --+---+---
 * 6 | 7 | 8
 * 
 * The puzzle contains the eight numbers 1-8, and an empty space.
 * If we represent the empty space as 0, then the puzzle is solved
 * when the values in the puzzle are as follows:
 * 
 * 1 | 2 | 3
 * --+---+---
 * 4 | 5 | 6
 * --+---+---
 * 7 | 8 | 0
 * 
 * That is, when the space at index 0 contains value 1, the space 
 * at index 1 contains value 2, and so on.
 * 
 * From any given state, you can swap the empty space with a space 
 * adjacent to it (that is, above, below, left, or right of it,
 * without wrapping around).
 * 
 * For example, if the empty space is at index 2, you may swap
 * it with the value at index 1 or 5, but not any other index.
 * 
 * Only half of all possible puzzle states are solvable! See:
 * https://en.wikipedia.org/wiki/15_puzzle
 * for details.
 * 

 * @author liberato
 *
 */
public class EightPuzzle implements SearchProblem<List<Integer>> {
	
	private List<Integer> startingState;
	private List<Integer> goalState;
	
	/**
	 * Creates a new instance of the 8 puzzle with the given starting values.
	 * 
	 * The values are indexed as described above, and should contain exactly the
	 * nine integers from 0 to 8.
	 * 
	 * @param startingValues
	 *            the starting values, 0 -- 8
	 * @throws IllegalArgumentException
	 *             if startingValues is invalid
	 */
	public EightPuzzle(List<Integer> startingValues) {
		startingState = startingValues;
//		goalState = new ArrayList<Integer>();
//		for(int i=1; i<9; i++){
//			goalState.add(i);
//		}
		
		goalState = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 });
		
	}

	@Override
	public List<Integer> getInitialState() {
		return startingState;
	}

	@Override
	public List<List<Integer>> getSuccessors(List<Integer> currentState) {
		List<List<Integer>> successors = new ArrayList<List<Integer>>();
		
		int zIndex = currentState.indexOf(0);
		int to;
		
		// Swap right and left
		to = zIndex+1;
		if(to!=9 && to!=3 && to!=6)
			successors.add(swap(currentState, zIndex, to));
		
		to = zIndex-1;
		if(to!=-1 && to!=2 && to!=5) 
			successors.add(swap(currentState, zIndex, to));

		// Swap up and down
		to = zIndex-3;
		if(to>=0)
			successors.add(swap(currentState, zIndex, to));
		
		to = zIndex+3;
		if(to<=8)
			successors.add(swap(currentState, zIndex, to));
		
		
//		for(int i=0; i<successors.size(); i++){
//			System.out.println("S :"+successors.get(i));
//		}
		
		return successors;
	}
	
	public List<Integer> swap(List<Integer> toSwap, int from, int to){
		List<Integer> swapped = new ArrayList<Integer>();
		swapped.addAll(toSwap);
		int temp = swapped.get(from);
		swapped.set(from, swapped.get(to));
		swapped.set(to, temp);
		return swapped;
	}

	@Override
	public boolean isGoal(List<Integer> state) {
		return (state.equals(goalState));
	}

	public static void main(String[] args) {
		EightPuzzle e = new EightPuzzle(Arrays.asList(new Integer[] { 1, 2, 3,
				4, 0, 6, 7, 5, 8 }));

		List<List<Integer>> r = new Solver<List<Integer>>(e).solveWithBFS();
		for (List<Integer> l : r) {
			System.out.println(l);
		}
	}
}
