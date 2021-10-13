import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DequeTest {
	//Make instance variables to use for several tests
	
	@Before
	public void before() {
		//Do stuff before each of the tests (such as set up a deque with some data)
	}
	
	@After
	public void after() {
		//Clean up after each test (mainly useful for file i/o)
	}
	
	@Test
	public void test() {
		assertTrue(true); //Message is optional for each method but is often helpful
		assertFalse("Message", false);
		Integer expected = 1;
		Integer received = 2;
		assertNotEquals("", expected, received);
		assertEquals("Message", expected, received);
	}

	@Test(timeout = 500, expected = DequeUnderflowException.class)
	public void testException() throws DequeUnderflowException {
		Deque<Integer> dq = new LinkedDeque<Integer>();
		dq.removeFront();
	}
}
