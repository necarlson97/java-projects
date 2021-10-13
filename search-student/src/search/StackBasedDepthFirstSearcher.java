package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Stack. This results in a
 * depth-first search.
 * 
 */
public class StackBasedDepthFirstSearcher<T> extends Searcher<T> {
	
	private Stack<T> path;
	
	public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
		path = new Stack<T>();
	}

	@Override
	public List<T> findSolution() {
		
		visited.add(searchProblem.getInitialState());
		path.push(searchProblem.getInitialState());
		
		while(!searchProblem.isGoal(path.peek())){
			T next = getNextUnvisited(path.peek());
			if(next == null) path.pop();
			else{
				visited.add(next);
				path.add(next);
			}
		}
		
		for(int i=0; i<path.size(); i++){
			solution.add(path.get(i));
		}
		
//		System.out.println();
//		for(int i=0; i<solution.size(); i++){
//			System.out.println("S"+i+": "+solution.get(i));
//		}	
		
		//just for good measure
		if (!isValidSolution(path)) 
			throw new RuntimeException("searcher should never find an invalid solution!");
		
		return solution;
	}
	
	// Returns the next unvisited step from the list of successors
	public T getNextUnvisited(T current){
		List<T> list = searchProblem.getSuccessors(current);
		for(int i=0; i<list.size(); i++){
			if(!visited.contains(list.get(i))) return list.get(i);
		}
		return null;
	}
}
