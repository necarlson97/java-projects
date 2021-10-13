package structures;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {

	private int upperBound;
	
	/**
	 * Adds an element to the tree.
	 * 
	 * The modified tree must still obey the BST rule, though it might not be
	 * balanced.
	 * 
	 * In addition to obeying the BST rule, the resulting tree must also obey
	 * the scapegoat rule. 
	 * 
	 * This method must only perform rebalancing of subtrees when indicated
	 * by the scapegoat rule; do not unconditionally call balance() 
	 * after adding, or you will receive no credit. 
	 * See the project writeup for details.
	 * 
	 * @param element
	 * @throws NullPointerException if element is null
	 */
	@Override
	public void add(T element) throws NullPointerException{	
		if(element == null) throw new NullPointerException();
		super.add(element);
		upperBound+=1;
		if(!isBalanced()){
			balance();
			upperBound = 0;
		}
	}
	
	@Override
	public boolean isBalanced(){
		if(2*size() < upperBound) return false;
		double logBound = Math.log10(upperBound) / Math.log10(1.5); // To get log base 3/2 of upperBound
//		System.out.println("Tree of size "+size()+": log3/2 of "+upperBound+
//				" is "+logBound+" > "+height()+" == "+(logBound > height()) );
		if(size() <= 1) return true;
		return logBound > height();
	}
	
	@Override
	public void balance(){
		BSTNode<T> goatRoot = getScapegoat(root);
		BinarySearchTree<T> goatSubtree = new BinarySearchTree<T>(goatRoot);
		goatSubtree.balance();
		
		//System.out.println(toDotFormat(root)+"\n\n");
		
		BSTNode<T> NGR = goatSubtree.getRoot(); // NGR = new goat root		
		goatRoot.setData(NGR.getData());
		goatRoot.setLeft(NGR.getLeft());
		goatRoot.setRight(NGR.getRight());
		
		//System.out.println(toDotFormat(root));
		
	}
	
	public BSTNode<T> getScapegoat(BSTNode<T> n){
		BSTNode<T> goat = n;
		double gRatio = (double) 2/3;
		if(n.getLeft() != null){
			double lRatio = (double) subtreeSize(n.getLeft())/subtreeSize(n);
			if( !(lRatio <= gRatio) )
				goat = getScapegoat(n.getLeft());
		}
		if(n.getRight() != null){	
			double rRatio = (double) subtreeSize(n.getRight())/subtreeSize(n);
			if( !(rRatio <= gRatio) )
				goat = getScapegoat(n.getRight());
		}
		return goat;
	}
	
	/**
	 * Attempts to remove one copy of an element from the tree, returning true
	 * if and only if such a copy was found and removed.
	 * 
	 * The modified tree must still obey the BST rule, though it might not be
	 * balanced.
	 * 
	 * In addition to obeying the BST rule, the resulting tree must also obey
	 * the scapegoat rule.
	 * 
	 * This method must only perform rebalancing of subtrees when indicated
	 * by the scapegoat rule; do not unconditionally call balance() 
	 * after removing, or you will receive no credit. 
	 * See the project writeup for details.

	 * @param element
	 * @return true if and only if an element removed
	 * @throws NullPointerException if element is null
	 */
	@Override
	public boolean remove(T element) {
		if(element == null) throw new NullPointerException();
		boolean removed = super.remove(element);
		if(!isBalanced()){
			balance();
			upperBound = 0;
		}
		return removed;
	}

}
