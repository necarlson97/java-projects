package structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import comparators.ReverseIntegerComparator;

public class MinQueueTest {

	MinQueue<String> queue;

	@Before
	public void setup() {
		queue = new MinQueue<String>();
	}

	@Test(timeout = 100)
	public void testQueue() {
		queue.enqueue(100, "Low priority");
		queue.enqueue(50, "Medium priority");
		queue.enqueue(25, "High priority");
		queue.enqueue(0, "Highest priority");
		assertEquals("Highest priority", queue.dequeue());
		assertEquals("High priority", queue.dequeue());
		assertEquals("Medium priority", queue.dequeue());
		assertEquals("Low priority", queue.dequeue());
	}

	public void testDequeue2() {
		queue.enqueue(2, "High priority");
		queue.enqueue(0, "Highest priority");
		queue.enqueue(15, "low priority");
		queue.enqueue(10, "Medium priority");
		assertEquals("Highest priority", queue.dequeue());
		assertEquals("Highest priority", queue.peek());
		assertEquals("High priority", queue.dequeue());
		assertEquals("Medium priority", queue.peek());
		assertEquals("Medium priority", queue.dequeue());

		assertFalse(queue.isEmpty());
		assertEquals("Low priority", queue.peek());
		assertEquals("Low priority", queue.dequeue());
		assertTrue(queue.isEmpty());

	}

	@Test(timeout = 100)
	public void testComparator() {
		Comparator<Integer> intComparator = new ReverseIntegerComparator();
		assertEquals(intComparator.getClass(), queue.getComparator().getClass());
	}

	@Test(timeout = 100)
	public void testIterator() {
		queue.enqueue(2, "High priority");
		queue.enqueue(6, "Highest priority");
		queue.enqueue(0, "Low priority");
		queue.enqueue(1, "Medium priority");
		Iterator<Entry<Integer, String>> iter = queue.iterator();

		assertTrue(iter.hasNext());
		assertEquals("Low priority", iter.next().getValue());
		assertEquals("Medium priority", iter.next().getValue());

		assertTrue(iter.hasNext());
		assertEquals("High priority", iter.next().getValue());
		assertEquals("Highest priority", iter.next().getValue());
		assertFalse(iter.hasNext());

	}

	@Test(timeout = 100, expected = NullPointerException.class)
	public void testNull1() {
		queue.enqueue(null, "a");
	}

	@Test(timeout = 100, expected = NullPointerException.class)
	public void testNull2() {
		queue.enqueue(1, null);
	}

	@Test(timeout = 100, expected = IllegalStateException.class)
	public void testIllegalDequeue() {
		queue.dequeue();
	}

	@Test(timeout = 100, expected = IllegalStateException.class)
	public void testIllegalPeek() {
		queue.peek();
	}
}
