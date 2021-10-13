package structures;

import java.util.ArrayList;
import java.util.Comparator;

public class StudentArrayHeap<P, V> extends AbstractArrayHeap<P, V> {
	
	protected final Comparator<P> comparator;
	protected final ArrayList<Entry<P, V>> heap;

	protected StudentArrayHeap(Comparator<P> comparator) {
		super(comparator);
		this.comparator = comparator;
		this.heap = super.heap;
	}

	@Override
	protected int getLeftChildOf(int index) throws IndexOutOfBoundsException {
		if(index<0) throw new IndexOutOfBoundsException();
		return (2*index)+1;
	}

	@Override
	protected int getRightChildOf(int index) throws IndexOutOfBoundsException {
		if(index<0) throw new IndexOutOfBoundsException();
		return (2*index)+2;
	}

	@Override
	protected int getParentOf(int index) throws IndexOutOfBoundsException {
		if(index<1) throw new IndexOutOfBoundsException();
		return (index-1)/2;
	}

	@Override
	protected void bubbleUp(int index) {
		if(index < 1) return;
		Entry<P, V> curr = heap.get(index); 
		int parentIndex = getParentOf(index);
		Entry<P, V> parent = heap.get(parentIndex); 
		
		//If the current 'node' is greater than its parents, swap with the parent, and recursivly repeat
		if(comparator.compare(curr.getPriority(), parent.getPriority()) > 0){
			swap(index,parentIndex);	
			bubbleUp(parentIndex);
		}
	}

	@Override
	protected void bubbleDown(int index) {
		int lChildIndex = getLeftChildOf(index);
		int rChildIndex = getRightChildOf(index);
		
		if(index < 0 || lChildIndex >= size() || rChildIndex >= size()) return;
		
		Entry<P, V> curr = heap.get(index); 
		Entry<P, V> lChild = heap.get(lChildIndex); 
		Entry<P, V> rChild = heap.get(rChildIndex); 
		
		
		//If the current 'node' is lesser than its children, swap with the child, and recursivly repeat
		if(comparator.compare(lChild.getPriority(), curr.getPriority()) > 0){
			swap(index,lChildIndex);	
			bubbleDown(lChildIndex);
		}
		else if(comparator.compare(rChild.getPriority(), curr.getPriority()) > 0){
			swap(index,rChildIndex);	
			bubbleDown(rChildIndex);
		}
		
	}
}
