package structures;

import java.util.Comparator;
import java.util.Iterator;
import structures.HeapIterator;

import comparators.IntegerComparator;

public class MaxQueue<V> implements PriorityQueue<Integer, V> {
	
	private Comparator<Integer> comparator = new IntegerComparator();
	private StudentArrayHeap<Integer, V> heap = new StudentArrayHeap<Integer, V>(comparator);

	@Override
	public PriorityQueue<Integer, V> enqueue(Integer priority, V value) 
			throws NullPointerException {
		if(priority == null || value == null) throw new NullPointerException();
		heap.add(priority, value);
		return this;
	}

	@Override
	public V dequeue() throws IllegalStateException {
		if(isEmpty()) throw new IllegalStateException();
		return heap.remove();
	}

	@Override
	public V peek() {
		if(isEmpty()) throw new IllegalStateException();
		return heap.peek();
	}

	@Override
	public Iterator<Entry<Integer, V>> iterator() {
		return new HeapIterator<V, Integer>(heap);
	}

	@Override
	public Comparator<Integer> getComparator() {
		return comparator;
	}

	@Override
	public int size() {
		return heap.size();
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}
}

