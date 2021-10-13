
import static org.junit.Assert.*;

import org.junit.Test;




public class HashtableTest {
	int maxSize=5;
	Hashtable linearHash = new Hashtable(maxSize);

	@Test
	public void testput() {
		
		linearHash.put("Florida", "Tallahassee");
		linearHash.put("Massachusetts", "Boston");
	
		assertEquals(true, linearHash.contains("Massachusetts"));
		
		assertEquals(2, linearHash.getSize());
		
	}
	
	@Test
	public void testget() {
		linearHash.put("Florida", "Tallahassee");
		linearHash.put("Massachusetts", "Boston");
		assertEquals("Boston", linearHash.get("Massachusetts"));
		linearHash.put("Maine", "Augusta");
		linearHash.put("Maryland", "Annapolis");
		assertEquals(null, linearHash.get("Annapolis"));
		assertEquals(4, linearHash.getSize());
	
	}
	
	
	@Test
	public void testrehash() {
		linearHash.put("Florida", "Tallahassee");
		linearHash.put("Massachusetts", "Boston");

		
		assertEquals("Boston", linearHash.get("Massachusetts"));
		linearHash.put("Maine", "Augusta");
		linearHash.put("Maryland", "Annapolis");
		assertEquals(null, linearHash.get("Annapolis"));
		assertEquals(4, linearHash.getSize());
		
		linearHash.put("New Jersey", "Trenton");

		assertEquals(true, linearHash.isFull());
		linearHash.put("Texas", "Austin");
		linearHash.printHashTable();

		assertEquals("Austin", linearHash.get("Texas"));
		assertEquals("Boston", linearHash.get("Massachusetts"));

		assertEquals(6, linearHash.getSize());
	}
	
	
	

}
