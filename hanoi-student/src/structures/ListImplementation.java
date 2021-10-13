package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListImplementation<T> implements ListInterface<T>{
	
	
	// How should this stack be implimented to work best with iterator??
	// Right now the first thing in, stays at that positin (so 0 is always the BASE of the stack)

	private int size;
	private LinkedStack<T> stack;
	
	public ListImplementation(){
		this.stack = new LinkedStack<T>();
		
	}
	
	public int size() {
		return size = stack.getSize();
	}

	public boolean isEmpty() {
		return size==0;
	}

	public T get(int n) throws NoSuchElementException {
		if(n>stack.getSize()-1) throw new NoSuchElementException();
		
		int stackSize = stack.getSize();
		LinkedStack<T> tempStack = new LinkedStack<T>();
		for(int i=0; i<stackSize-1 -n; i++){
			tempStack.push(stack.pop());
		}
		
		T data = stack.peek();
		int tempSize = tempStack.getSize();
		for(int i=0; i<tempSize; i++){
			stack.push(tempStack.pop());
		}
		
		return data;
	}

	public ListInterface<T> append(T elem) {
		if(elem == null) throw new NullPointerException();
		stack.push(elem);
		return this;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedStackIterator<T>(this);
	}
	
	

}
