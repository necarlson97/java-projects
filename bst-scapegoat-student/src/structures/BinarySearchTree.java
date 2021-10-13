package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements
		BSTInterface<T> {
	protected BSTNode<T> root;
	
	public BinarySearchTree(){
		
	}
	public BinarySearchTree(BSTNode<T> root){
		this.root = root;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		return get(t) != null;
	}

	public boolean remove(T t) {
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			return node;
		}
	}

	public T get(T t) throws NullPointerException{
		if(root == null) throw new NullPointerException();
		BSTNode<T> n = root;
		return getHelper(t, n);
	}
	
	public T getHelper(T t, BSTNode<T> n) {
		if(n.getData().equals(t)) return n.getData();
		//else if(n.getLeft() == null && n.getRight() == null) return null;
		if(n.getLeft() != null && n.getData().compareTo(t) > 0 )
			return getHelper(t, n.getLeft());
		if(n.getRight() != null && n.getData().compareTo(t) < 0)
			return getHelper(t, n.getRight());
		return null;
	}

	public void add(T t) throws NullPointerException {
		if(t == null) throw new NullPointerException();
		root = addToSubtree(t, root);
	}

	private BSTNode<T> addToSubtree(T t, BSTNode<T> node) {
		if (node == null) {
			return new BSTNode<T>(t, null, null);
		}
		BSTNode<T> child;
		if (t.compareTo(node.getData()) <= 0) {
			child = addToSubtree(t, node.getLeft());
			node.setLeft(child);
		} else {
			child = addToSubtree(t, node.getRight());
			node.setRight(child);
		}
		//child.setParent(node); removed due to autograder compatability issues

		return node;
	}

	@Override
	public T getMinimum() {
		BSTNode<T> n = root;
		if(n == null) return null;
		while(n.getLeft()!=null){
			n=n.getLeft();
		}
		return n.getData();
	}

	@Override
	public T getMaximum() {
		BSTNode<T> n = root;
		if(n == null) return null;
		while(n.getRight()!=null){
			n=n.getRight();
		}
		return n.getData();
	
		//return getHighestValue(root); another way to do it, dryer code, but felt cheaty
	}


	@Override
	public int height() {
		if(isEmpty()) return -1;
		return heightHelper(root, 0);
	}
	
	public int heightHelper(BSTNode<T> n, int h){
		if(n.getLeft()!=null && n.getRight()!= null){
			int l = heightHelper(n.getLeft(), h+1);
			int r = heightHelper(n.getRight(), h+1);
			return l>r ? l : r ;
		}
		else if(n.getLeft() != null){
			return heightHelper(n.getLeft(), h+1);
		}
		else if(n.getRight() != null){
			return heightHelper(n.getRight(), h+1);
		}
		else return h;
	}

	@Override
	public Iterator<T> preorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		preorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			queue.add(node.getData());
			preorderTraverse(queue, node.getLeft());
			preorderTraverse(queue, node.getRight());
		}
	}

	@Override
	public Iterator<T> inorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			inorderTraverse(queue, node.getLeft());
			queue.add(node.getData());
			inorderTraverse(queue, node.getRight());
		}
	}

	@Override
	public Iterator<T> postorderIterator() {
		Queue<T> queue = new LinkedList<T>();
		postorderTraverse(queue, root);
		return queue.iterator();
	}
	
	private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
		if (node != null) {
			postorderTraverse(queue, node.getLeft());
			postorderTraverse(queue, node.getRight());
			queue.add(node.getData());
		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {
		BSTNode<T> n1 = root;
		BSTNode<T> n2 = other.getRoot();
		return equalsHelper(n1, n2);
	}
	
	private boolean equalsHelper(BSTNode<T> node1, BSTNode<T> node2) {
		if (node1 == null && node2 == null) return true;
		else if (node1 == null || node2 == null) return false;
		return equalsHelper(node1.getLeft(), node2.getLeft())
				&& node1.getData().equals(node2.getData())
				&& equalsHelper(node1.getRight(), node2.getRight());
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		if(this.size() != other.size()) return false;
		Iterator<T> thisIter = this.inorderIterator();
		Iterator<T> otherIter = other.inorderIterator();
		while(thisIter.hasNext()){
			if(!thisIter.next().equals(otherIter.next())) return false;
		}
		return true;
	}

	@Override
	public boolean isBalanced() {
		if(size() == 0) return true;
		return Math.pow(2, height()) <= size() && size() < Math.pow(2, height()+1);
	}

	@Override
	public void balance() {
		Iterator<T> iter = inorderIterator();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size()];
		for(int i=0; i<size(); i++){
			array[i] = iter.next();
		}
		BinarySearchTree<T> newTree = new BinarySearchTree<T>();
		balanceHelper(newTree, array, 0, size());
		root=newTree.getRoot();
	}
	
	public void balanceHelper(BinarySearchTree<T> t, T[] a, int l, int h){
		int m = (h+l)/2;
		if(a[m] == null ) return;
		t.add(a[m]);
		a[m] = null;
		balanceHelper(t, a, m, h);
		balanceHelper(t, a, l, m);
	}

	@Override
	public BSTNode<T> getRoot() {
		// DO NOT MODIFY
		return root;
	}
	
	public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
		// DO NOT MODIFY
		// see project description for explanation

		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}
}