package sets;

import java.util.Iterator;

public class LinkedSet<E> implements Set<E> {
	private LinkedNode<E> head = null;

	private int size;

	// Constructors
	public LinkedSet() {
	}

	public LinkedSet(E e) {
		this.head = new LinkedNode<E>(e, null);
	}

	private LinkedSet(LinkedNode<E> head) {
		this.head = head;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedNodeIterator<E>(this.head);
	}

	@Override
	public boolean contains(Object o) {
		Iterator<E> iThis = this.iterator();
		while (iThis.hasNext()) {
			E thisVar = iThis.next();
			if (thisVar.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSubset(Set<E> that) {
		Iterator<E> iThis = this.iterator();
		while (iThis.hasNext()){
			E thisVar = iThis.next();
			if(!that.contains(thisVar)){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isSuperset(Set<E> that) {
		return that.isSubset(this);
	}

	@Override
	public Set<E> adjoin(E e) {
		LinkedNode<E> newHead = new LinkedNode<E>(e,head);
		return new LinkedSet<E>(newHead);
	}

	@Override
	public Set<E> union(Set<E> that) {	
		LinkedSet<E> interSet = (LinkedSet<E>) this.intersect(that);
		LinkedSet<E> newSet = new LinkedSet<E>();
		
		Iterator<E> iInter = interSet.iterator();
		Iterator<E> iThis = this.iterator();
		
		while(iInter.hasNext()){
			newSet = (LinkedSet<E>) newSet.adjoin(iInter.next());
		}
		while(iThis.hasNext()){
			newSet = (LinkedSet<E>) newSet.adjoin(iThis.next());
		}
		
	
		return newSet;
	}

	@Override
	public Set<E> intersect(Set<E> that) {
		
		if(this.isSubset(that)) return this;
		
		Iterator<E> iThis = this.iterator();
		LinkedSet<E> newSet = new LinkedSet<E>();
		
		while (iThis.hasNext()){
			E thisVar = iThis.next();
			if(that.contains( thisVar) ) newSet = (LinkedSet<E>) newSet.adjoin(thisVar);
			else newSet.remove(thisVar);
		}
		
		return newSet;
	}

	@Override
	public Set<E> subtract(Set<E> that) {
		
		Iterator<E> iThis = this.iterator();
		LinkedSet<E> newSet = new LinkedSet<E>();

		while (iThis.hasNext()){
			E thisVar = iThis.next();
			if(!that.contains(thisVar)) newSet = (LinkedSet<E>) newSet.adjoin(thisVar);
		}
		
		return newSet;
	}

	@Override
	public Set<E> remove(E e) {
		Iterator<E> iThis = this.iterator();
		LinkedSet<E> newSet = new LinkedSet<E>();
		
		while (iThis.hasNext()){
			E thisVar = iThis.next();
			if(thisVar != e) newSet = (LinkedSet<E>) newSet.adjoin(thisVar);
		}
		
		return newSet;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (!(o instanceof Set)) {
			return false;
		}
		Set<E> that = (Set<E>) o;
		return this.isSubset(that) && that.isSubset(this);
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (E e : this) {
			result += e.hashCode();
		}
		return result;
	}
}
