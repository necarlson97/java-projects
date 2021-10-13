package sorters;

import java.util.Comparator;

import structures.SwapList;

public class InsertionSorter<T> extends AbstractSorter<T> {

	public InsertionSorter(SwapList<T> list, Comparator<T> comparator) {
		super(list, comparator);
	}

	@Override
	public SwapList<T> sort() {
		for(int out=1; out<list.size(); out++){
			int in = out-1; 
			while(in>=0 && list.compare(in, in+1, comparator)>0){
				list.swap(in, in+1);
				in--;
			}
		}
		return list;
	}
	
}
