import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class CycleFinderQueue{
	
	LinkedList<GraphNode> queue = new LinkedList<GraphNode>();
	
	// A simple implementation of a prioritq queue for the Cycle Finder
	public CycleFinderQueue(){
		
	}
	
	
	// Insert new nodes, the commented out area is the implementation of the 'priotizing',
	// which ended up costing more time than it saved, at least on the given inputs sizes
	public void add(GraphNode k){
		int i=0;
//		for(GraphNode n : queue){
//			if(k.compareTo(n) > 0) {
//				queue.add(i, k);
//				return;
//			}
//			i++;
//		}
		queue.add(k);
	}
	
	// Add a collection of graph nodes (in our case, a 'batch' of edge nodes
	// which is added each step of the cycle finding algorythm)
	public void addAll(Collection<GraphNode> all){
		for(GraphNode n : all){
			add(n);
		}
	}
	
	// View node at head
	public GraphNode peek(){
		return queue.getFirst();
	}
	
	// Remove nodes from head
	public GraphNode poll(){
		return queue.removeFirst();
	}

}
