package search;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstraction over the idea of a search.
 *
 * @author liberato
 *
 * @param <T>
 */
public abstract class Searcher<T> {
	protected final SearchProblem<T> searchProblem;
	protected final List<T> visited;
	protected List<T> solution;

	/**
	 * Instantiates a searcher.
	 * 
	 * @param searchProblem
	 *            the search problem for which this searcher will find and
	 *            validate solutions
	 */
	public Searcher(SearchProblem<T> searchProblem) {
		this.searchProblem = searchProblem;
		this.visited = new ArrayList<T>();
		this.solution = new ArrayList<T>();
	}

	/**
	 * Finds and return a solution to the problem, consisting of a list of
	 * states.
	 * 
	 * The list should start with the initial state of the underlying problem.
	 * Then, it should have one or more additional states. Each state should be
	 * a successor of its predecessor. The last state should be a goal state of
	 * the underlying problem.
	 * 
	 * If there is no solution, then this method should return an empty list.
	 * 
	 * @return a solution to the problem (or an empty list)
	 */
	public abstract List<T> findSolution();

	/**
	 * Checks that a solution is valid.
	 * 
	 * A valid solution consists of a list of states. The list should start with
	 * the initial state of the underlying problem. Then, it should have one or
	 * more additional states. Each state should be a successor of its
	 * predecessor. The last state should be a goal state of the underlying
	 * problem.
	 * 
	 * @param solution
	 * @return true iff this solution is a valid solution
	 * @throws NullPointerException
	 *             if solution is null
	 */
	public final boolean isValidSolution(List<T> solution) {
		// check to see the first step of the solution is the game's inital state
		if(solution.isEmpty() || 
				!searchProblem.getInitialState().equals(solution.get(0)) ){
			System.out.println("Initial state or empty");
			return false;
		}
			
		
		for(int i = 1; i<solution.size(); i++){
			// solution step i-1 successors must include step i, therefore validating step i
			if(!searchProblem.getSuccessors(solution.get(i-1))
				.contains(solution.get(i))){
				System.out.println("Middle error");
				return false;
			}
		}

		// and finaly, the last step must lead to a valid end goal state
		if(!searchProblem.isGoal(solution.get(solution.size()-1))){
			System.out.println("Final wasnt goal");
			return false;
		}
			
		
		return true;
	}
}
