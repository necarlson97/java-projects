package structures;

import java.util.Iterator;


public class RecursiveList<T> implements ListInterface<T> {
	
	private int size;
	public Node<T> head;


	public int size() {
		return size;
	}
	
	// returns the node at 'index'
	public Node<T> getHelper(int i, Node<T> n){
		if(i<=0) return n;
		return getHelper(i-1, n.getNext());
	}

	public ListInterface<T> insertFirst(T elem) throws NullPointerException{
		if(elem == null) throw new NullPointerException("Tried to insert null");
		
		Node<T> newNode = new Node<T>(elem, head);
		head = newNode;
		size++;
		return this;
	}

	public ListInterface<T> insertLast(T elem) throws NullPointerException{
		if(elem == null) throw new NullPointerException("Tried to insert null");
		
		insertAt(size, elem);
		return this;
	}

	public ListInterface<T> insertAt(int index, T elem) 
			throws NullPointerException, IndexOutOfBoundsException{
		if(elem == null) throw new NullPointerException("Tried to insert null");
		if(index > size) throw new IndexOutOfBoundsException("Tried to insert beyond list size");

		if(index==0){
			insertFirst(elem);
			return this;
		}
		
		Node<T> newNode = new Node<T>(elem, null);		
		Node<T> n = getHelper(index-1, head);

		newNode.setNext(n.getNext());
		n.setNext(newNode);
		size++;
		return this;

	}

	public T removeFirst() throws IllegalStateException{
		if(isEmpty()) throw new IllegalStateException();
		
		size--;
		T data = head.getData();
		head = head.getNext();
		return data;
	}
	
	public T removeLast() throws IllegalStateException{
		if(isEmpty()) throw new IllegalStateException();
		return removeAt(size-1);
	}

	public T removeAt(int i) throws IndexOutOfBoundsException{
		if(i<0 || i>=size) throw new IndexOutOfBoundsException();
		
		T data;
		if(i == 0) return removeFirst();
		else if(size == 1){
			data = head.getData();
			head = null;
		}
		else{
			Node<T> prevNode = getHelper(i-1, head);
			data = prevNode.getNext().getData();
			prevNode.setNext(prevNode.getNext().getNext());
		}
		size--;
		return data;
	}

	public T getFirst() throws IllegalStateException{
		if(isEmpty()) throw new IllegalStateException();
		return head.getData();
	}

	public T getLast() throws IllegalStateException{
		if(isEmpty()) throw new IllegalStateException();
		return get(size-1);
	}

	public T get(int i) throws IndexOutOfBoundsException{
		if(i<0 || i>=size) throw new IndexOutOfBoundsException();
		return getHelper(i, head).getData();
	}

	public boolean remove(T elem) throws NullPointerException{
		if(elem == null) throw new NullPointerException("Tried to insert null");
		
		if(size<=0) return false;
		int i = indexOf(elem);
		if(i < 0) return false;
		removeAt(i);
		return true;
	}

	public int indexOf(T elem) throws NullPointerException{
		if(elem == null) throw new NullPointerException("Tried to insert null");
		
		return indexHelper(elem, 0, head);
	}
	
	public int indexHelper(T tar, int i, Node<T> n){
		if(i>=size) return -1;
		if(tar.equals( n.getData())) return i;
		return indexHelper(tar, i+1, n.getNext());
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public Iterator<T> iterator() {
		return new ListIterator<T>(this.head);
	}
	
}