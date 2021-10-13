import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MaxHeapTest {
	MaxHeap<Integer> heap;
	
	@Before
	public void before() {
		List<Integer> list = new ArrayList<Integer>();
		for(int num : new int[] {5, 2, 8, 4, 3, 7, 6, 9, 1})
			list.add(num);
		
		heap = new MaxHeap<Integer>(list);
		assertEquals("[9, 5, 8, 4, 3, 7, 6, 2, 1]", list.toString());
		
		//System.out.println("Before: \n"+heap.getList().toString()+"\n");

	}
	
	@Test
	public void testBubbleDownToLeaf() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		int index=1;
		int newElem= 0; //decreased: 5 to 0
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+heap.getList().toString()+"\n");


		assertEquals("[9, 4, 8, 2, 3, 7, 6, 0, 1]", heap.getList().toString());
		
		
	}
	
	@Test
	public void testBubbleDownToMiddle() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		int index=1;
		int newElem= 2; //decreased: 5 to 2
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+list.toString()+"\n");
		

		assertEquals("[9, 4, 8, 2, 3, 7, 6, 2, 1]", heap.getList().toString());
		
		
	}
	
	@Test
	public void testNoBubbleDown() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		int index=1;
		int newElem= 4; //decreased: 5 to 4
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+list.toString()+"\n");
		

		assertEquals("[9, 4, 8, 4, 3, 7, 6, 2, 1]", heap.getList().toString());
		
		
	}
	
	@Test
	public void test_SameValue() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		
		int index=1;
		int newElem= 5; //same: 5 to 5
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+list.toString()+"\n");
	
		assertEquals("[9, 5, 8, 4, 3, 7, 6, 2, 1]", heap.getList().toString());
		
		
	}
	
	@Test
	public void testBubbleUp() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		
		int index=heap.size()-1;
		int newElem= 11; //increased: 1 to 11
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+list.toString()+"\n");

		assertEquals("[11, 9, 8, 5, 3, 7, 6, 2, 4]", heap.getList().toString());
		
		
	}
	
	
	@Test
	public void testBubbleUpToMiddle() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		
		int index=heap.size()-1;
		int newElem= 8; //increased: 1 to 8
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+list.toString()+"\n");

		assertEquals("[9, 8, 8, 5, 3, 7, 6, 2, 4]", heap.getList().toString());		
	}
	
	@Test
	public void testNoBubbleUp() {
		//heap is: [9, 5, 8, 4, 3, 7, 6, 2, 1]
		
		int index=3;
		int newElem= 2; //increased: 4 to 2
		heap.setNode(index, newElem);
		//System.out.println("After: \n"+list.toString()+"\n");
		
		assertEquals("[9, 5, 8, 2, 3, 7, 6, 2, 1]", heap.getList().toString());
	}
	
	

	@Test
	public void testOneElement() {
		//heap is: [0]
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);
		heap= new MaxHeap<Integer>(list);
		//System.out.println("Before: \n"+list.toString()+"\n");
		heap.setNode(0, 1); //increased from 0 to 1
		//System.out.println("After: \n"+list.toString()+"\n");

		assertEquals("[1]", list.toString());
	}



	@Test(timeout = 500, expected = NullPointerException.class)
	public void testException() {
		int index=3;
		Integer newElem= null; //set to null
		heap.setNode(index, newElem);
	}
}
