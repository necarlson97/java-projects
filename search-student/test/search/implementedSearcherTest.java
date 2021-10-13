package search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import mazes.Cell;
import mazes.Maze;
import mazes.MazeGenerator;

public class implementedSearcherTest {
	@Rule
	public Timeout globalTimeout = new Timeout(500L, TimeUnit.MILLISECONDS);

	private Maze maze;

	@Before
	public void before() {
		maze = new MazeGenerator(3, 3, 2).generateDFS();
		/* maze should now be:
		#0#1#2#
		0  S  0
		# # # #
		1     1
		# ### #
		2  G  2
		#0#1#2#
		*/
	}
	
	@Test
	public void testSetMazeStackSolution() {
		Searcher<Cell> s = new StackBasedDepthFirstSearcher<Cell>(maze);
		List<Cell> solution = s.findSolution();
		
//		for(int i=0; i<solution.size(); i++){
//			System.out.println(solution.get(i));
//		}
		
		assertEquals(5, solution.size());
		assertEquals(solution.get(0), new Cell(1, 0));
		assertEquals(solution.get(1), new Cell(0, 0));
		assertEquals(solution.get(2), new Cell(0, 1));
		assertEquals(solution.get(3), new Cell(0, 2));
		assertEquals(solution.get(4), new Cell(1, 2));
		assertTrue(s.isValidSolution(solution));
	}
	
	@Test
	public void testSetMazeQueueSolution() {
		Searcher<Cell> s = new QueueBasedBreadthFirstSearcher<Cell>(maze);
		List<Cell> solution = s.findSolution();

		
		assertEquals(5, solution.size());
		assertEquals(solution.get(0), new Cell(1, 0));
		assertEquals(solution.get(1), new Cell(0, 0));
		assertEquals(solution.get(2), new Cell(0, 1));
		assertEquals(solution.get(3), new Cell(0, 2));
		assertEquals(solution.get(4), new Cell(1, 2));
		assertTrue(s.isValidSolution(solution));
	}

	@Test
	public void testNewMazeStackSolution() {
		int w = (int) (Math.random()*(6-3) + 3);
		int h = (int) (Math.random()*(6-3) + 3);
		int s = (int) (Math.random()*(5-1) + 1);
		
		System.out.println("Stack: ");
		
		Maze newMaze = new MazeGenerator( w, h, s).generateDFS();
		Searcher<Cell> m = new StackBasedDepthFirstSearcher<Cell>(newMaze);
		List<Cell> solution = m.findSolution();
		
		System.out.println(newMaze.toString());
		System.out.println("Stack Solution: ");
		System.out.println(solution);
		
		assertTrue(m.isValidSolution(solution));
	}
	@Test
	public void testNewMazeQueueSolution() {
		int w = (int) (Math.random()*(6-3) + 3);
		int h = (int) (Math.random()*(6-3) + 3);
		int s = (int) (Math.random()*(5-1) + 1);
		
		Maze newMaze = new MazeGenerator( w, h, s).generateDFS();
		Searcher<Cell> m = new QueueBasedBreadthFirstSearcher<Cell>(newMaze);
		List<Cell> solution = m.findSolution();
		
		assertTrue(m.isValidSolution(solution));
	}
	
}
