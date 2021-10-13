package sorters;

import java.util.Comparator;

import structures.SwapList;

public class HeapSorter<T> extends AbstractSorter<T> {


	public HeapSorter(SwapList<T> list, Comparator<T> comparator) {
		super(list, comparator);
	}

	@Override
	public SwapList<T> sort() {
		System.out.println(list.toString());
		heapify();
		//System.out.println(list.toString());
		for(int i=list.size()-1; i>=1; i--){
			list.swap(0, i);
			bubbleDown(0, i-1);
		}
		System.out.println(list.toString());
		return list;
	}
	
	public SwapList<T> heapify(){
		for(int i=list.size()/2-1; i>=0; i--){
			bubbleDown(i, list.size()-1);
		}
		return list;
	}
	
	public void bubbleDown(int index, int heapSize){
		int l = (2*index)+1; // left child index
		int r = (2*index)+2; // right child index
		
		if(index < 0 ) return;
		
		// Try to minimise the comparisons made below vvv 
		if(hasGreaterChild(index, l, heapSize) && 
				hasGreaterChild(index, r, heapSize)){ // if both childen are greater
			if(list.compare(l, r, comparator) >= 0) {
				list.swap(index,l);	bubbleDown(l, heapSize); }
			
			else {
				list.swap(index,r);	bubbleDown(r, heapSize); }
			
		}
		else if(hasGreaterChild(index, l, heapSize) ){ // only has left bigger
			list.swap(index,l);	bubbleDown(l, heapSize); }
		else if(hasGreaterChild(index, r, heapSize) ){ // only has right bigger
			list.swap(index,r);	bubbleDown(r, heapSize); }
		else return; // neither is bigger
	}
	
	public boolean hasGreaterChild(int index, int child, int heapSize){
		return child <= heapSize && list.compare(child, index, comparator) >= 0;
		
	}
	
}
