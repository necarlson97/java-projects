package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListIterator<T> implements Iterator<T> {

	public Node<T> head;
	public Node<T> currNode;

	// Constructors
	public ListIterator(Node<T> head) {
		this.head = head;
		if(head != null) currNode = new Node<T>(head.getData(), head);
		else currNode = new Node<T>(null, head);
	}

	@Override
	public boolean hasNext() {
		return currNode.getNext() != null;
	}

	@Override
	public T next() {
		if (currNode.getNext() != null) {
			currNode = currNode.getNext();
		}		
		else {
			throw new NoSuchElementException();
		}
		
		return currNode.getData();
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
