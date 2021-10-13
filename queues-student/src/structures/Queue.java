package structures;

import java.util.NoSuchElementException;

public class Queue<T> implements UnboundedQueueInterface<T> {
	public Node<T> front;
	private Node<T> rear;
	private int size;
	
	public Queue() {
	}

	public Queue(Queue<T> other) {
		Node<T> currNode = other.front;
		while(currNode != null){
			this.enqueue(currNode.getData());
			currNode=currNode.getNext();
		}
	}
	
	@Override
	public boolean isEmpty() {
		return front==null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void enqueue(T element) {
		size++;
		Node<T> newNode = new Node<T>(element, null);
		if(rear == null) front = newNode;
		else rear.setNext(newNode);
		rear = newNode;
	}

	@Override
	public T dequeue() throws NoSuchElementException {
		size--;
		if(isEmpty())
			throw new NoSuchElementException("Tried to dequeue empty queue");
		T data = (T) front.getData();
		front = front.getNext();
		if(front==null) rear = null;
		return data;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException("Tried to peek empty queue");
		return (T) front.getData();
	}

	@Override
	public UnboundedQueueInterface<T> reversed() {
		if(isEmpty()) return this;
		Queue<T> newQueue = new Queue<T>();
		
		int backIndex = 1;
		for(int i=0; i<size; i++){
			Queue<T> thisQueue = new Queue<T>(this);
			for(int j=0; j<size-backIndex; j++) thisQueue.dequeue(); //rids que of all but last element
			newQueue.enqueue(thisQueue.dequeue());
			backIndex++;
			
		}
		
		return newQueue;
	}
}
