import java.util.List;


public class MaxHeap<T extends Comparable<T>> {

	/* List representing max heap*/
	List<T> list;

	/**
	 * Constructor
	 * @param list The list with random order of elements
	 * (This list might not follow max heap rule.)
	 */
	public MaxHeap(List<T> list){
		this.list=list;
		heapify(list); //convert list to max-heap list
	}

	/**
	 * Converts the provided list into a heap.
	 * @param list The list to heapify.
	 */
	private void heapify(List<T> list) {

		int start=(list.size()-2)/2;

		while(start>=0){
			bubbleDown(start--, list.size()-1);
		}
	}


	/**
	 * Swaps the elements at i1 and i2.
	 */
	private void swap( int i1, int i2) {		
		T temp = list.get(i2);
		list.set(i2, list.get(i1));
		list.set(i1, temp);
	}

	/**
	 * Set the element of node at index i with the given element
	 * @param i index of element to be replaced
	 * @param elem New element to replace the element at index i
	 */
	public void setNode(int i, T elem){
		if( list.get(i).compareTo(elem) < 0) {
			list.set(i, elem);
			bubbleUp(0, i);	
		}
		else {
			list.set(i, elem);
			bubbleDown(i, size());
		}
	}

	/**
	 * Bubbles an element up the heap to its correct position.
	 * Assuming the elements above it obey the heap rule,
	 * this will result in a valid heap from the start to the end index.
	 * @param start The start index of the heap 
	 * @param end The index of the element to bubble up.
	 */	
	public void bubbleUp(int start, int end){
		if(end < 1 || end <= start) return;
		T curr = list.get(end); 
		int parentIndex = (end-1)/2;
		T parent = list.get(parentIndex); 
		
		//If the current 'node' is greater than its parents, swap with the parent, and recursivly repeat
		if(curr.compareTo(parent) > 0){
			swap(end,parentIndex);	
			bubbleUp(start, parentIndex);
		}

	}
	
	/**
	 * Bubbles an element down the heap to its correct position.
	 * Assuming the elements below it obey the heap rule,
	 * this will result in a valid heap from the start to the end index.
	 * @param start The index of the element to bubble down.
	 * @param end The last index of the heap (the end of the list may not be part of the heap).
	 */
	private void bubbleDown( int start, int end) {
		int lChildIndex = (2*start)+1;
		int rChildIndex = (2*start)+2;
		
		if(start > end || start < 0|| lChildIndex >= size() || rChildIndex >= size()) return;
		
		T curr = list.get(start); 
		T lChild = list.get(lChildIndex); 
		T rChild = list.get(rChildIndex); 
		
		
		//If the current 'node' is lesser than its children, swap with the child, and recursivly repeat
		if(lChild.compareTo(curr) > 0){
			swap(start,lChildIndex);	
			bubbleDown(lChildIndex, end);
		}
		else if(rChild.compareTo(curr) > 0){
			swap(start,rChildIndex);	
			bubbleDown(rChildIndex, end);
		}
	
	}

	/**
	 * Get the list representing the heap
	 * @return list The list representing the heap
	 */	
	protected List<T> getList(){
		return list;
	}

	/**
	 * Get the size of the list
	 * @return int The size of the list
	 */	
	public int size(){
		return list.size();
	}

}
