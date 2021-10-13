package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure
 * to allow for unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {
	
	private LLNode<T> top;
	private int size;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T pop() throws StackUnderflowException {
		if(top == null) throw new StackUnderflowException("Attempted to pop from empty stack");
		T data = top.getData();
		top = top.getNext();
		size--;
		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T top() throws StackUnderflowException {
		if(top == null) throw new StackUnderflowException("Attempted to top an empty stack");
		return top.getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void push(T elem) {
		LLNode<T> newNode = new LLNode<T>(elem);
		newNode.setNext(top);
		top = newNode;
		size++;
	}

}
