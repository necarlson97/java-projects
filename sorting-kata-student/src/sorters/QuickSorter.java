package sorters;

import java.util.Comparator;

import structures.SwapList;

public class QuickSorter<T> extends AbstractSorter<T> {

	public QuickSorter(SwapList<T> list, Comparator<T> comparator) {
		super(list, comparator);
	}
	
	/*
	 * Note: When choosing a pivot, choose the element in the middle of
	 * the sub-array. That is,
	 * 
	 * pivotIndex = (firstIndex + lastIndex) / 2;
	 */

	@Override
	public SwapList<T> sort() {
		int low = 0;
		int high = list.size()-1;
		recursiveQuickSort(low, high);
		return list;
	}
	
	public void recursiveQuickSort(int low, int high){
		if(low < high) {
			int p = partition(low, high);
			recursiveQuickSort(low, p-1);
			recursiveQuickSort(p+1, high);
		}
		
	}
	
	public int partition(int low, int high){
		int piv = (low+high) /2;
		int storeIndex = low;
		list.swap(piv, high);
		
		for(int i=low; i<=high-1; i++){
			if(list.compare(i, high, comparator) <=0){
				list.swap(i, storeIndex);
				storeIndex++;
			}
		}
		list.swap(storeIndex, high);
		return storeIndex;
	}
}