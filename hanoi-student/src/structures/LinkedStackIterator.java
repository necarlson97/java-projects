package structures;


import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedStackIterator<T> implements Iterator<T> {
	
	int current = 0;
	private ListImplementation<T> list;

	public LinkedStackIterator(ListImplementation<T> list) {
		this.list = list;
		if(list != null) list = new ListImplementation<T>();
	}

	@Override
	public boolean hasNext() {
		try{
			list.get(current);
			return true;
		}
		catch(Exception NoSuchElementException){
			return false;
		}
		
	}

	@Override
	public T next() {
		T data = list.get(current);
		current ++;
		return data;
	}

	@Override
	public void remove() {
	}
}
