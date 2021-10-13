package structures;

import java.util.Iterator;
import java.util.List;

public class HeapIterator<V, P> implements Iterator<structures.Entry<P, V>> {

	public AbstractArrayHeap<P, V> heap;
	public List<Entry<P, V>> list;

	// Constructors
	public HeapIterator(AbstractArrayHeap<P, V> h) {
		this.heap = h;
		list = heap.asList();
	}
	
	@Override
	public boolean hasNext() {
		return !heap.isEmpty();
	}

	@Override
	public Entry<P, V> next() throws NullPointerException{
		if(!hasNext()) throw new NullPointerException();
		Entry<P, V> ent = list.get(0);
		heap.remove();
		return ent;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
