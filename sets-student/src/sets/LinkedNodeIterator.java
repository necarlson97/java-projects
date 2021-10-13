package sets;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedNodeIterator<E> implements Iterator<E> {
	
	public LinkedNode<E> head;
	public LinkedNode<E> currNode;

	// Constructors
	public LinkedNodeIterator(LinkedNode<E> head) {
		this.head = head;
		if(head != null) currNode = new LinkedNode<E>(head.getData(), head);
		else currNode = new LinkedNode<E>(null, head);
	}

	@Override
	public boolean hasNext() {
		return currNode.getNext() != null;
	}

	@Override
	public E next() {
		if (currNode.getNext() != null) {
			currNode = currNode.getNext();
		}		
		else {
			throw new NoSuchElementException();
		}
		
		return currNode.getData();
	}

	// Leave this one alone.
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
