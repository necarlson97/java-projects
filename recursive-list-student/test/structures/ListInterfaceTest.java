package structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ListInterfaceTest {

	private ListInterface<String> list;
	
	@Before
	public void setup(){
          list = new RecursiveList<String>();
	}
	
	@Test (timeout = 500)
	public void testNewListsDoNotShareRefrences(){
		RecursiveList<String> otherList = new RecursiveList<String>();
		assertTrue("New list should be empty.", otherList.isEmpty() );
		assertEquals("New list should be size 0.", 0, otherList.size() );
		assertEquals("List with one object should be size 1.", 1, 
				otherList.insertFirst("Cat").size() );
		
		assertTrue("Diffrent list should still be empty.", list.isEmpty());
		
	}
	
	@Test (timeout = 500)
	public void testInsertLastAndGetLast(){
		
		list = new RecursiveList<String>();
		assertTrue("New list should be empty.", list.isEmpty() );
		
		list.insertLast("a");
		list.insertLast("b");
		list.insertLast("c");
		list.insertLast("d");
		list.insertLast("last");
		
		assertEquals("List should be size 5.", 5, list.size() );
		assertEquals("Last element should be 'last'", "last", list.getLast());
	}
	
	@Test (timeout = 500)
	public void testInsertAtAndGetAt(){
		
		list = new RecursiveList<String>();
		assertTrue("New list should be empty.", list.isEmpty() );
		
		list.insertFirst("a");
		assertEquals("List should be size 1.", 1, list.size() );
		list.insertLast("b");
		assertEquals("List should be size 2.", 2, list.size() );
		list.insertLast("c");
		list.insertLast("d");
		list.insertLast("last");
		
		assertEquals("List should be size 5.", 5, list.size() );
		
		list.insertAt(0, "first");
		list.insertAt(1, "A");
		list.insertAt(3, "B");
		list.insertAt(5, "C");
		list.insertAt(7, "D");	
		
		assertEquals("List should be size 10.", 10, list.size() );
		assertEquals("First element should be 'first'", "first", list.getFirst());
		assertEquals("0th element should be 'first'", "first", list.get(0));
		assertEquals("1st element should be 'A'", "A", list.get(1));
		assertEquals("2nd element should be 'a'", "a", list.get(2));
		assertEquals("3rd element should be 'B'", "B", list.get(3));
		assertEquals("4th element should be 'b'", "b", list.get(4));
		assertEquals("9th element should be 'last'", "last", list.get(9));
		assertEquals("Last element should be 'last'", "last", list.getLast());
	}
	
	@Test (timeout = 500)
	public void testInsertFirstAndGetAt(){

		list = new RecursiveList<String>();
		
		list.insertFirst("last");
		list.insertFirst("c");
		list.insertFirst("b");
		list.insertFirst("a");
		list.insertFirst("first");
		
		assertEquals("First element should be 'first'", "first", list.getFirst());
		assertEquals("0th element should be 'first'", "first", list.get(0));
		assertEquals("1st element should be 'a'", "a", list.get(1));
		assertEquals("2nd element should be 'b'", "b", list.get(2));
		assertEquals("3rd element should be 'c'", "c", list.get(3));
		assertEquals("4th element should be 'last'", "last", list.get(4));
		assertEquals("Last element should be 'last'", "last", list.getLast());
		
	}
	
	@Test (timeout = 500)
	public void testInsertFirstIsEmptySizeAndGetFirst1() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		assertEquals("Insert First should return instance of self", list, list.insertFirst("hello"));
		assertFalse("List should now have elements.", list.isEmpty());
		assertEquals("List should now have 1 element.", 1, list.size());
		assertEquals("First element should .equals \"hello\".", "hello", list.getFirst());
		list.insertFirst("world");
		assertEquals(2, list.size());
		list.insertFirst("foo");
		assertEquals(3, list.size());
		assertEquals("First element should .equals \"foo\".", "foo", list.getFirst());
	}

	@Test (timeout = 500)
	public void testRemoveMethods(){
		RecursiveList<String> list = new RecursiveList<String>();
		
		list.insertFirst("first");
		list.insertLast("a");
		list.insertLast("b");
		list.insertLast("c");
		list.insertLast("d");
		list.insertLast("last");
		
		assertEquals("First element should be 'first'", "first", list.getFirst());
		assertEquals("Last element should be 'last'", "last", list.getLast());
		assertEquals("List should be size 6.", 6, list.size() );
		
		list.removeFirst();
		assertEquals("First element should be 'a'", "a", list.getFirst());
		assertEquals("List should be size 5.", 5, list.size() );
		
		list.removeFirst();
		assertEquals("0th element should be 'b'", "b", list.get(0));
		assertEquals("List should be size 4.", 4, list.size() );
		
		list.removeLast();
		assertEquals("Last element should be 'd'", "d", list.getLast());
		assertEquals("List should be size 3.", 3, list.size() );
		
		list.removeAt(1);
		assertEquals("List should be size 2.", 2, list.size() );
		assertEquals("0th element should be 'b'", "b", list.get(0));
		assertEquals("1st element should be 'd'", "d", list.get(1));
		
		assertEquals("First element should be 'b'", "b", list.getFirst());
		assertEquals("Last element should be 'd'", "d", list.getLast());

		assertTrue( list.remove("d") );

		assertEquals("First element should be 'b'", "b", list.getFirst());
		assertEquals("Last element should be 'b'", "b", list.getLast());
		assertEquals("List should be size 1.", 1, list.size() );
				
		assertTrue( list.remove("b") );
		assertEquals("List should be size 0.", 0, list.size() );
		
		assertFalse( list.remove("c") );
		
		list.insertFirst("a");
		list.insertFirst("b");
		list.insertFirst("a");
		list.insertFirst("first");
		
		list.removeAt(0);
		
		assertEquals("List should be size 3.", 3, list.size() );
		assertEquals("First element should be 'a'", "a", list.getFirst());
		assertEquals("Last element should be 'a'", "a", list.getLast());
		
		assertFalse( list.remove("c") );
		assertTrue( list.remove("a") );
		
		assertEquals("First element should be 'b'", "b", list.getFirst());
		assertEquals("Last element should be 'a'", "a", list.getLast());
		
		
		
		
	}
	
	@Test (timeout = 500)
	public void testIndexMethods(){
		
		list = new RecursiveList<String>();
		
		list.insertLast("a");
		list.insertLast("b");
		list.insertLast("c");
		list.insertLast("d");
		list.insertLast("last");
		
		assertEquals("'Last' should be indexed at 4", 4, list.indexOf("last"));
		assertEquals("'a' should be indexed at 0", 0, list.indexOf("a"));
		assertEquals("'c' should be indexed at 0", 2, list.indexOf("c"));
		assertEquals("'p' should return -1", -1, list.indexOf("p"));
	}

	@Test (timeout = 500)
	public void testIterator(){
		list = new RecursiveList<String>();
		
		list.insertLast("a");
		list.insertLast("b");
		list.insertLast("c");
		
		Iterator<String> iter = list.iterator();
		
		assertTrue(iter.hasNext());
		assertEquals("First node data is a", "a", iter.next());
		assertEquals("Next node data is b", "b", iter.next());
		assertTrue(iter.hasNext());
		assertEquals("Next node data is c", "c", iter.next());
		assertFalse(iter.hasNext());

	}
	
	@Test (timeout = 500, expected = IllegalStateException.class)
	public void testIllegalStateRemoveFirst(){
		list = new RecursiveList<String>();
		list.removeFirst();
	}
	
	@Test (timeout = 500, expected = IllegalStateException.class)
	public void testIllegalStateRemoveLast(){
		list = new RecursiveList<String>();
		list.removeLast();
	}
	
	@Test (timeout = 500, expected = IndexOutOfBoundsException.class)
	public void testIndexOutOfBoundsRemoveAtNegative(){
		list = new RecursiveList<String>();
		list.insertFirst("a");
		list.removeAt(-1);
	}
	
	@Test (timeout = 500, expected = IndexOutOfBoundsException.class)
	public void testIndexOutOfBoundsRemoveAt(){
		list = new RecursiveList<String>();
		list.insertFirst("a");
		list.removeAt(1);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testNullPointerInsertFirst(){
		list = new RecursiveList<String>();
		list.insertFirst(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testNullPointerInsertLast(){
		list = new RecursiveList<String>();
		list.insertLast(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testNullPointerInsertAt(){
		list = new RecursiveList<String>();
		list.insertAt(0, null);
	}
	
	@Test (timeout = 500, expected = IllegalStateException.class)
	public void testIllegalStateGetFirst(){
		list = new RecursiveList<String>();
		list.getFirst();
	}
	
	@Test (timeout = 500, expected = IllegalStateException.class)
	public void testIllegalStateGetLast(){
		list = new RecursiveList<String>();
		list.getLast();
	}
	
	@Test (timeout = 500, expected = IndexOutOfBoundsException.class)
	public void testIndexOutOfBoundsGetNegative(){
		list = new RecursiveList<String>();
		list.insertFirst("a");
		list.get(-1);
	}
	
	@Test (timeout = 500, expected = IndexOutOfBoundsException.class)
	public void testIndexOutOfBoundsGet(){
		list = new RecursiveList<String>();
		list.get(0);
		list.insertFirst("a");
		list.get(1);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testNullPointerRemove(){
		list = new RecursiveList<String>();
		list.insertFirst("a");
		list.remove(null);
	}
	
	@Test (timeout = 500, expected = NullPointerException.class)
	public void testNullPointerIndexOf(){
		list = new RecursiveList<String>();
		list.insertFirst("a");
		list.indexOf(null);
	}
	
}
