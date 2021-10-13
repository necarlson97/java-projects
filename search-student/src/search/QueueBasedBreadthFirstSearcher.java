package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Queue. This results in a
 * breadth-first search.
 * 
 */
public class QueueBasedBreadthFirstSearcher<T> extends Searcher<T> {
	
	private Queue<T> queue;
	
	public QueueBasedBreadthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
		queue = new LinkedList<T>();
	}

	@Override
	public List<T> findSolution() {
		List<T> states = new ArrayList<T>();
		List<Integer> pred = new ArrayList<Integer>();
		T state = searchProblem.getInitialState();
		
		queue.add(state);
		visited.add(state);
		pred.add(-1);
		
		int k = 0;
		while(!searchProblem.isGoal(state) && !queue.isEmpty()){
			state = queue.peek();
			states.add(state);	
			for(T successor : getAllUnvisited(state)){
				if(!visited.contains(successor)){
					queue.add(successor);
					visited.add(successor);
					pred.add(k);
				}
				
			}
			k++;
			queue.poll();
		}
		
		// build solution using our list of predecessor indexes, pred
		int p = pred.get(states.size() -1); // get the pred index of the goal state
		solution.add(states.get(states.size() -1));
		while(p >=0 ){
			solution.add(states.get(p));
			p = pred.get(p);
		}
		
		Collections.reverse(solution);
		
//		System.out.println();
//		for(int i=0; i<solution.size(); i++){
//			System.out.println("S"+i+": "+solution.get(i));
//		}

		return solution;
	}

	
	// Returns the next unvisited step from the list of successors
	public List<T> getAllUnvisited(T current){
		List<T> list = searchProblem.getSuccessors(current);
		for(int i=0; i<list.size(); i++){
			if(visited.contains(list.get(i))) list.remove(i);
		}
		return list;
	}	
}


class Pair<T>{

	protected T state;
	protected List<T> path;

	public Pair(T state, List<T> path){
		this.state = state;
		this.path = path;
	}

}