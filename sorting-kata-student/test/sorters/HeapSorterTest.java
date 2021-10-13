package sorters;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import comparators.IntegerComparator;
import comparators.LexicographicStringComparator;
import structures.ArrayBasedSwapList;
import structures.SwapList;

public class HeapSorterTest {
	@Rule
	public Timeout globalTimeout = new Timeout(500L, TimeUnit.MILLISECONDS);

	private static final Comparator<Integer> INTEGER_COMPARATOR = new IntegerComparator();
	private static final Comparator<String> STRING_COMPARATOR = new LexicographicStringComparator();

	SwapList<Integer> emptyList;
	AbstractSorter<Integer> emptySorter;
	SwapList<Integer> sortedList;
	AbstractSorter<Integer> sortedSorter;
	SwapList<String> reversedList;
	AbstractSorter<String> reversedSorter;
	SwapList<Integer> firstAsLastList;
	AbstractSorter<Integer> firstAsLastSorter;

	@Before
	public void before() {
		emptyList = new ArrayBasedSwapList<Integer>(new Integer[] {});
		emptySorter = new HeapSorter<Integer>(emptyList,
				INTEGER_COMPARATOR);

		sortedList = new ArrayBasedSwapList<Integer>(new Integer[] { -3, -1, 0,
				2, 4 });
		sortedSorter = new HeapSorter<Integer>(sortedList,
				INTEGER_COMPARATOR);

		List<String> rList = new ArrayList<String>();
		for (char c = 'z'; c >= 'a'; c--) {
			rList.add(Character.toString(c));
		}
		reversedList = new ArrayBasedSwapList<String>(rList);
		reversedSorter = new HeapSorter<String>(reversedList,
				STRING_COMPARATOR);

		firstAsLastList = new ArrayBasedSwapList<Integer>(new Integer[] { 5, 6,
				7, 8, 9, 10, 4 });
		firstAsLastSorter = new HeapSorter<Integer>(firstAsLastList,
				INTEGER_COMPARATOR);
	}
	
	@Test
	public void testRandomChars() {
		List<String> charList = new ArrayList<String>();
		Random r = new Random();
		for(int i=0; i<26; i++){
			Character c = (char)(r.nextInt(26) + 'a');
			String s = c.toString();
			if(!charList.contains(s)) charList.add(s);
			else i--;
		}
		SwapList<String> stringList =  new ArrayBasedSwapList<String>(charList);
		AbstractSorter<String> stringSorter = new HeapSorter<String>(stringList,
				STRING_COMPARATOR);
		
		SwapList<String> result = stringSorter.sort();
		assertTrue(result.isSorted(STRING_COMPARATOR));
	}
	
	@Test
	public void testRandomInts() {
		List<Integer> intList = new ArrayList<Integer>();
		Random r = new Random();
		for(int i=0; i<26; i++){
			int n = (int)(r.nextInt(26));
			if(!intList.contains(n)) intList.add(n);
			else i--;
		}
		SwapList<Integer> numberList =  new ArrayBasedSwapList<Integer>(intList);
		AbstractSorter<Integer> numberSorter = new HeapSorter<Integer>(numberList,
				INTEGER_COMPARATOR);
		
		SwapList<Integer> result = numberSorter.sort();
		assertTrue(result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testEmpty() {
		SwapList<Integer> result = emptySorter.sort();
		assertTrue(result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSorted() {
		SwapList<Integer> result = sortedSorter.sort();
		assertTrue(result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedComparisons() {
		SwapList<Integer> result = sortedSorter.sort();
		// note: this is the number the solution (based upon DJW's pseudocode)
		// returns; yours should not be much different
		//
		// the graded tests will have some slack here
		assertEquals(12, result.getComparisons());
	}

	@Test
	public void testSortedSwaps() {
		SwapList<Integer> result = sortedSorter.sort();
		// note: this is the number the solution (based upon DJW's pseudocode)
		// returns; yours should not be much different
		//
		// the graded tests will have some slack here
		assertEquals(10, result.getSwaps());
	}

	@Test
	public void testReversed() {
		SwapList<String> result = reversedSorter.sort();
		assertTrue(result.isSorted(STRING_COMPARATOR));
	}

	@Test
	public void testReversedComparisons() {
		SwapList<String> result = reversedSorter.sort();
		// note: this is the number the solution (based upon DJW's pseudocode)
		// returns; yours should not be much different
		//
		// the graded tests will have some slack here
		assertEquals(150, result.getComparisons());
	}

	@Test
	public void testReversedSwaps() {
		SwapList<String> result = reversedSorter.sort();
		// note: this is the number the solution (based upon DJW's pseudocode)
		// returns; yours should not be much different
		//
		// the graded tests will have some slack here
		assertEquals(87, result.getSwaps());
	}

	@Test
	public void testFirstAsLast() {
		SwapList<Integer> result = firstAsLastSorter.sort();
		assertTrue(result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testFirstAsLastComparisons() {
		SwapList<Integer> result = firstAsLastSorter.sort();
		// note: this is the number the solution (based upon DJW's pseudocode)
		// returns; yours should not be much different
		//
		// the graded tests will have some slack here
		assertEquals(21, result.getComparisons());
	}

	@Test
	public void testFirstAsLastSwaps() {
		SwapList<Integer> result = firstAsLastSorter.sort();
		// note: this is the number the solution (based upon DJW's pseudocode)
		// returns; yours should not be much different
		//
		// the graded tests will have some slack here
		assertEquals(16, result.getSwaps());
	}

	@Test
	public void testRandomLists() {
		List<SwapList<Integer>> randomizedLists = new ArrayList<SwapList<Integer>>();
		Random random = new Random(0);
		for (int length = 1; length < Math.pow(2, 8); length *= 2) {
			for (int count = 0; count < Math.min(length, 10); count++) {
				List<Integer> list = new ArrayList<Integer>(length);
				for (int i = 0; i < length; i++) {
					list.add(random.nextInt());
				}
				randomizedLists.add(new ArrayBasedSwapList<Integer>(list));
			}
		}

		for (SwapList<Integer> list : randomizedLists) {
			AbstractSorter<Integer> sorter = new HeapSorter<Integer>(list,
					INTEGER_COMPARATOR);
			SwapList<Integer> result = sorter.sort();
			assertTrue(result.isSorted(INTEGER_COMPARATOR));
		}
	}
	
//	@Test
//	public void testHeapify() {
//		
//		SwapList<Integer> heapList = new ArrayBasedSwapList<Integer>(
//				new Integer[] { 25, 17, 36, 2, 3, 100, 1, 19, 7 });
//		HeapSorter<Integer> heapSorter = new HeapSorter<Integer>(heapList, INTEGER_COMPARATOR);
//		
//		heapList = heapSorter.heapify();
//		
//		SwapList<Integer> heapResult = new ArrayBasedSwapList<Integer>(
//				new Integer[] { 100, 19, 36, 17, 3, 25, 1, 2, 7 });
//		
//		assertEquals(heapResult, heapList);
//	}
//	
//	@Test
//	public void testBubbleDown() {
//		SwapList<Integer> heapList = new ArrayBasedSwapList<Integer>(
//				new Integer[] { 25, 17, 36, 2, 3, 100, 1, 19, 7 });
//		HeapSorter<Integer> heapSorter = new HeapSorter<Integer>(heapList, INTEGER_COMPARATOR);
//		
//		heapSorter.bubbleDown(3, heapList.size()-1);
//		
//		SwapList<Integer> heapResult = new ArrayBasedSwapList<Integer>(
//				new Integer[] { 25, 17, 36, 19, 3, 100, 1, 2, 7 });
//		
//		assertEquals(9, heapList.size());
//		assertEquals(heapResult, heapList);
//		
//	}
	
}
