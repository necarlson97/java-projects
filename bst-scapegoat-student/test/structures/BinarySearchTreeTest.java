package structures;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.Timeout;

public class BinarySearchTreeTest {

	private BSTInterface<Integer> emptyTree;
	private BSTInterface<String> oneNodeTree;
	private BSTInterface<String> balancedTree;
	private BSTInterface<String> unbalancedTree;
	private BSTInterface<String> stickTree;
	private static final String FOO = "foo";

	@Rule
    public Timeout timeout = new Timeout(1L, TimeUnit.SECONDS);
	
	@Before
	public void before() {
		emptyTree = new BinarySearchTree<Integer>();
		
		oneNodeTree = new BinarySearchTree<String>();
		oneNodeTree.add(FOO);
		
		balancedTree = new BinarySearchTree<String>();
		balancedTree.add("d");
		balancedTree.add("b");
		balancedTree.add("c");
		balancedTree.add("a");
		balancedTree.add("f");
		balancedTree.add("e");
		balancedTree.add("g");
		
		unbalancedTree = new BinarySearchTree<String>();
		unbalancedTree.add("a");
		unbalancedTree.add("c");
		unbalancedTree.add("d");
		unbalancedTree.add("f");
		unbalancedTree.add("e");
		unbalancedTree.add("b");
		
		stickTree =  new BinarySearchTree<String>();
		stickTree.add("a");
		stickTree.add("b");
		stickTree.add("c");
		stickTree.add("d");

	}
	
	@Test
	public void testEmpty() {
		assertTrue(emptyTree.isEmpty());
	}

	@Test
	public void testNotEmpty() {
		assertFalse(oneNodeTree.isEmpty());
		assertFalse(balancedTree.isEmpty());
		assertFalse(unbalancedTree.isEmpty());
		assertFalse(stickTree.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, emptyTree.size());
		assertEquals(1, oneNodeTree.size());
		assertEquals(4, stickTree.size());
		assertEquals(6, unbalancedTree.size());
		assertEquals(7, balancedTree.size());
	}
	
	@Test
	public void testContains() {
		assertTrue(oneNodeTree.contains(FOO));
		assertTrue(stickTree.contains("c"));
		assertTrue(balancedTree.contains("f"));
		assertTrue(unbalancedTree.contains("b"));
	}
	
	@Test
	public void testRemove() {
		assertFalse(stickTree.remove("z"));
		assertTrue(stickTree.remove("d"));
	}
	
	@Test
	public void testGet() {
		assertEquals(null, stickTree.get("Z"));
		assertEquals(FOO, oneNodeTree.get(FOO));
		assertEquals("a", balancedTree.get("a"));
		assertEquals("f", unbalancedTree.get("f"));
		assertEquals("b", stickTree.get("b"));
	}
	
	@Test
	public void testAdd() {
		emptyTree.add(1);
		assertFalse(emptyTree.isEmpty());
		assertEquals(1, emptyTree.size());
	}
	
	@Test
	public void testGetMinimum() {
		assertEquals(null, emptyTree.getMinimum());
		assertEquals(FOO, oneNodeTree.getMinimum());
		assertEquals("a", unbalancedTree.getMinimum());
		assertEquals("a", balancedTree.getMinimum());
		assertEquals("a", stickTree.getMinimum());
	}

	@Test
	public void testGetMaximum() {
		assertEquals(FOO, oneNodeTree.getMaximum());
		assertEquals(null, emptyTree.getMaximum());
		assertEquals("f", unbalancedTree.getMaximum());
		assertEquals("g", balancedTree.getMaximum());
		assertEquals("d", stickTree.getMaximum());
	}

	@Test
	public void testHeight() {
		assertEquals(-1, emptyTree.height());
		assertEquals(0, oneNodeTree.height());
		assertEquals(2, balancedTree.height());
		assertEquals(4, unbalancedTree.height());
		assertEquals(3, stickTree.height());
	}
	
	@Test
	public void testPreorderIterator() {
		Iterator<String> i = oneNodeTree.preorderIterator();
		while (i.hasNext()) {
			assertEquals(FOO, i.next());			
		}
		assertFalse(i.hasNext());
		
		Iterator<String> j = balancedTree.preorderIterator();
		assertEquals("d", j.next());
		assertEquals("b", j.next());
		assertEquals("a", j.next());
		assertEquals("c", j.next());
		assertEquals("f", j.next());
		assertEquals("e", j.next());
		assertEquals("g", j.next());
		assertFalse(j.hasNext());
		
		Iterator<String> k = stickTree.preorderIterator();
		assertEquals("a", k.next());
		assertEquals("b", k.next());
		assertEquals("c", k.next());
		assertEquals("d", k.next());
		assertFalse(k.hasNext());
		
	}

	@Test
	public void testInorderIterator() {
		Iterator<String> i = oneNodeTree.inorderIterator();
		while (i.hasNext()) {
			assertEquals(FOO, i.next());			
		}
		
		Iterator<String> j = balancedTree.inorderIterator();
		assertEquals("a", j.next());
		assertEquals("b", j.next());
		assertEquals("c", j.next());
		assertEquals("d", j.next());
		assertEquals("e", j.next());
		assertEquals("f", j.next());
		assertEquals("g", j.next());
		assertFalse(j.hasNext());
		
		Iterator<String> l = unbalancedTree.inorderIterator();
		assertEquals("a", l.next());
		assertEquals("b", l.next());
		assertEquals("c", l.next());
		assertEquals("d", l.next());
		assertEquals("e", l.next());
		assertEquals("f", l.next());
		assertFalse(j.hasNext());
		
		Iterator<String> k = stickTree.inorderIterator();
		assertEquals("a", k.next());
		assertEquals("b", k.next());
		assertEquals("c", k.next());
		assertEquals("d", k.next());
		assertFalse(k.hasNext());
	}

	@Test
	public void testPostorderIterator() {
		Iterator<Integer> i = emptyTree.postorderIterator();
		assertFalse(i.hasNext());
		
		Iterator<String> j = balancedTree.postorderIterator();
		assertEquals("a", j.next());
		assertEquals("c", j.next());
		assertEquals("b", j.next());
		assertEquals("e", j.next());
		assertEquals("g", j.next());
		assertEquals("f", j.next());
		assertEquals("d", j.next());
		assertFalse(j.hasNext());
		
		Iterator<String> k = stickTree.postorderIterator();
		assertEquals("d", k.next());
		assertEquals("c", k.next());
		assertEquals("b", k.next());
		assertEquals("a", k.next());
		assertFalse(k.hasNext());
	}
	
	@Test
	public void testEquals() {
		BSTInterface<String> tree = new BinarySearchTree<String>();
		assertFalse(oneNodeTree.equals(tree));
		tree.add(new String("foo"));
		assertTrue(oneNodeTree.equals(tree));
		
		tree = new BinarySearchTree<String>();
		tree.add("a");
		tree.add("b");
		tree.add("c");
		tree.add("d");
		
		assertTrue(stickTree.equals(tree));
		tree.add("e");
		assertFalse(stickTree.equals(tree));
		tree.remove("e");
		assertTrue(stickTree.equals(tree));
	}
	
	@Test
	public void testSameValues() {
		BSTInterface<Integer> tree = new BinarySearchTree<Integer>();
		assertTrue(emptyTree.sameValues(tree));
		
		emptyTree.add(1);
		emptyTree.add(2);
		tree.add(2);
		tree.add(1);
		assertTrue(emptyTree.sameValues(tree));
		
		tree.add(3);
		assertFalse(emptyTree.sameValues(tree));
		tree.remove(3);
		assertTrue(emptyTree.sameValues(tree));
	}
	
	@Test 
	public void testIsBalanced() {
		assertTrue(emptyTree.isBalanced());
		emptyTree.add(1);
		assertTrue(emptyTree.isBalanced());
		emptyTree.add(2);
		assertTrue(emptyTree.isBalanced());
		emptyTree.add(3);
		assertFalse(emptyTree.isBalanced());
		
		assertFalse(stickTree.isBalanced());
		assertFalse(unbalancedTree.isBalanced());
		assertTrue(balancedTree.isBalanced());
	}
	
	@Test 
	public void testBalance() {
		emptyTree.add(1);
		emptyTree.add(2);
		emptyTree.add(3);
		assertFalse(emptyTree.isBalanced());
		emptyTree.balance();
		assertTrue(emptyTree.isBalanced());
		
		assertFalse(unbalancedTree.isBalanced());
		unbalancedTree.balance();
		assertTrue(unbalancedTree.isBalanced());
	}

	@Test (timeout = 500, expected = NullPointerException.class)
	public void testContainsException(){
		stickTree.contains(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testRemoveException(){
		unbalancedTree.remove(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testGetException(){
		balancedTree.get(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testAddException(){
		emptyTree.add(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testEqualsException(){
		stickTree.equals(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testSameValuesException(){
		unbalancedTree.sameValues(null);
	}
}
