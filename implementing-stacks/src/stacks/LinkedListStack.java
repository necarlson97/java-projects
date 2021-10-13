package stacks;

public class LinkedListStack<T> implements Stack<T> {
    LLNode<T> head;

    public LinkedListStack() {
        head = null;
    }

    @Override
    public boolean isEmpty() {
    	
        return (head == null);
    }
    
    @Override
    public T pop() throws StackUnderflowException {
    	
        if(this.isEmpty()) throw new StackUnderflowException
        ("Attempted to pop from empty stack.");
    	T data = head.getInfo();
        head = head.getLink();
    	return data;
    }

    @Override
    public T peek() throws StackUnderflowException {
    	
        if(this.isEmpty()) throw new StackUnderflowException("Attempted to peek on empty stack.");
        return head.getInfo();
    }

    @Override
    public void push(T element) {
    	LLNode<T> newNode = new LLNode<T>(element);
    	newNode.setLink(head);
    	head = newNode;
    }


}
