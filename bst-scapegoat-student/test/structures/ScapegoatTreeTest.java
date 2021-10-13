package structures;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.Timeout;

public class ScapegoatTreeTest {

	private BSTInterface<Integer> tree;
	private BSTInterface<String> stickTree;

	@Rule
    public Timeout timeout = new Timeout(1L, TimeUnit.SECONDS);
	
	@Before
	public void before() {
		tree = new ScapegoatTree<Integer>();
		stickTree = new ScapegoatTree<String>();
	}
	
	@Test
	public void testAdd() {
		stickTree.add("a");
		stickTree.add("b");
		stickTree.add("c");
		stickTree.add("d");
		assertEquals(3, stickTree.height());
		stickTree.add("e");
		assertEquals(3, stickTree.height());
	}
	
	@Test 
	public void testRemove() {
		for (int i: new int[] {3, 1, 5, 0, 4, 2, 6} ) {
			tree.add(i);
		}

		for (int i: new int[] {1, 2, 0, 4}) {
			tree.remove(i);
		}
		
		BSTInterface<Integer> smallTree = new BinarySearchTree<Integer>();
		smallTree.add(5);
		smallTree.add(3);
		smallTree.add(6);
		
		assertTrue(tree.equals(smallTree));
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testAddException(){
		tree.add(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testRemoveException(){
		stickTree.remove(null);
	}
}
