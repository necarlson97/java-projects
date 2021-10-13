/**
 * Defines a bounded sized set of non-negative ints.
 * The implementation uses a list that is populated
 * with ListNode objects.  All ListNode objects are
 * allocated when a set is created, and simply move
 * around in the list thereafter.
 * 
 * Each method is implemented so that it makes at
 * most one traversal of the linked list, i.e., is
 * as efficient as it can be in terms of using
 * getNext and setNext on the ListNodes in the list.
 *
 * @author Eliot Moss
 *
 * Copyright 2015 Eliot Moss
 */

public class ListBoundedIntSet implements BoundedIntSet {

  /**
   * points to the first node of the list
   */
  private ListNode head;

  /**
   * Builds a bounded int set of a particular size.
   * This implementation pre-allocates all the nodes, and reuses
   * the same nodes for this set from here on.
   * @param size an int giving the desired size, assumed > 0
   */
  public ListBoundedIntSet (final int size) {
    ListNode current = null;
    int i = size;
    while (--i >= 0) {
      final ListNode node = new ListNode();
      node.setNext(current);
      current = node;
    }
    head = current;
  }

  /**
   * Creates a bounded int set with the given elements in the
   * given order -- handy for testing.  Note that non-negative
   * elements must come first, followed by any -1 elements (to
   * create a bounded int set of a large size than the initial
   * non-empty elements).
   * 
   * @param elements an int[] giving the initial elements of the set
   */
  public ListBoundedIntSet (final int [] elements) {
    ListNode current = null;
    int i = elements.length;
    while (--i >= 0) {
      final ListNode node = new ListNode();
      node.setNext(current);
      node.value = elements[i];
      current = node;
    }
    head = current;
  }

  /**
   * @return an int giving the number of values present in the set
   */
  public int size () {
    int count = 0;
    ListNode curr = head;
    while (true) {
      if (curr == null || curr.value < 0) {
        return count;
      } else {
        ++count;
        curr = curr.getNext();
      }
    }
  }

  /** 
  * @return an int giving the maximum number of values the set can contain
   * (We assume this operation is rare, so we do not store the size.)
   */
  public int capacity () {
    int count = 0;
    ListNode curr = head;
    while (true){
      if (curr == null) {
        return count;
      } else {
        ++count;
        curr = curr.getNext();
      }
    }
  }


  /**
   * Pulls node curr to the head of the list, given a pointer to the
   * previous node.  For convenience, the method does nothing if curr is null.
   * @param prev the previous ListNode; null if curr is at the head of the list
   * @param curr the ListNode to pull to the head of the list;
   */
  private void pullToHead (final ListNode prev, final ListNode curr) {
    // TODO: Have this actually pull node curr to the head of the list,
	  
    // first checking that neither prev not curr are null
	  if(prev == null || curr == null) return;
	  
	  // So we unchain the current node by linking the node just before it to the node just after it
	  // (Note, this should work just fine even if curr.getNext() is '-1' or 'null')
	  prev.setNext(curr.getNext());
	  
	  // And now we attach our current node to the head of the list
	  curr.setNext(head);
	  head = curr;
	  
  }

  /**
   * Determines whether a given (presumed non-negative) value is in the set;
   * if it is present, it pulls the node to the head of the list, on the notion
   * that this may improve average performance in the typical case.
   * @param value an int giving the value whose presence should be tested
   * @return a boolean, true iff the value is in the set
   */
  public boolean contains (final int value) {
    // Note: It would be nice if we could use a loop of this form:
    // for (final ListNode curr = head; curr != null; curr = curr.getNext()) { ... }
    // The problem is, we need to know the node *before* the one we are searching for
    // so that we can do the unchaining necessary to pull the node to the head of the
    // list.  Notice the pattern using prev and curr.

    // TODO: when the node is found, pull it to the head of the list
    ListNode prev = null;
    ListNode curr = head;
    while (true) {
      if (curr == null || curr.value == -1) { 
    	  // If we have arrived here, we have fully traversed out list, and must return false
        return false;
      } else if (curr.value == value) {
    	  // But if we have located the node with the desired value, we must move it to the head
    	  pullToHead(prev, curr);
    	  // And then return true, for it was located
        return true;
      } else {
        prev = curr;
        curr = curr.getNext();
      }
    }
  }

  /**
   * Removes a (presumed non-negative) value if it is in the set;
   * this implementation works by shuffling values one element toward
   * the head of the list.
   * @param value an int giving the value to be removed
   * @return a boolean indicating whether the operation changed the set, i.e.,
   * true if the item was present, false if it was not
   */
  public boolean remove (final int value) {
    // Note: there is an invariant (rule) that the empty nodes must be at the
    // end of the list
    ListNode curr = head;
    while (true) {
      if (curr == null || curr.value == -1) {
        return false;
      } else if (curr.value == value) {
        break;
      }
      curr = curr.getNext();
    }
    while (true) {
      ListNode next = curr.getNext();
      if (next == null) {
        curr.value = -1;
        return true;
      } else {
        curr.value = next.value;
        if (curr.value < 0) {
          return true;
        }
      }
      curr = next;
    }
  }

  /**
   * Adds a value to the set if it is not present;
   * whether the item is present or not, the node with the
   * value in it is left at the head of the list.  (The
   * idea is that this "pull to front" behavior may improve
   * performance.)
   * @param value an int (presumed non-negative) giving the value to add
   * @return an int decribing what happened:
   *   0 means the value was present already and simply pulled to the head of the list
   *   1 means the value was not present, but an empty node could be used for it
   *   2 means the value was not present, and the value previously at the end of the
   *     list was discarded
   */
  public int add (final int value) {
    // TODO: implement the method!
	  
	ListNode prevPrev = null; // The 'previous's previous', so what we may call 'pullToHead(prevPrev, prev)'
	ListNode prev = null;
	ListNode curr = head;
	
	if(head.value == value) return 0; // If our head has our desired value, we can stop right here
	while (true) {
		
	  if (curr == null) { 
		  // If we have arrived here, our list was full, and we must discard an existing value 
		  // so that we may add the given value. Here is where we use prevPrev to pull the prev to the head 
		  prev.value = value;
		  
		  // And his will not run if there is no need (if the list was size one, making prev the head and prevPrev null)
		  pullToHead(prevPrev, prev); 
	    return 2;
	  } else if (curr.value == -1) {
		  // If we arrive here, our list is not yet full, and we need only set this node
		  // to the given value, and pull it to the head
		  curr.value = value;
		  pullToHead(prev, curr);
		return 1;
	  } else if (curr.value == value) {
		  // If we arrive here, our desired value already exists within the set,
		  // and pulling it to the head is all that is needed
		  pullToHead(prev, curr);
	    return 0;
	  } else { // and this is simply to update our values as we iterate through the list
		prevPrev = prev;
	    prev = curr;
	    curr = curr.getNext();
	  }
	  
	}
  }
}
