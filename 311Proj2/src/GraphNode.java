import java.util.Collection;
import java.util.LinkedList;

public class GraphNode implements Comparable{
	
	// Tree-ish level so that as we search our previously colored nodes for bad cycles,
	// we can find the shortest of these cycles
	int level = -1; 
	// The integer value given by the input, as well as this nodes desired index in the Graph Arrray
	int name;
	// 'n' represents 'not yet colored', 'R' is red, 'B' is blue, and 'V' is 'visited by oddc cycle search
	char color = 'n';
	LinkedList<GraphNode> edges = new LinkedList<GraphNode>();
	LinkedList<GraphNode> path = new LinkedList<GraphNode>();
	
	public GraphNode(int name){
		this.name = name;
	}
	
	// Searches edges for color conflicts
	public GraphNode colorMatch(){
		for(GraphNode e : edges) {
			if(color == e.color) return e;
		}
		return null;
	}
	
	// Sets my edges color and level, as well as returning any of them 
	// that have yet to be visited as I have
	public LinkedList<GraphNode> colorVisitEdges(){
		LinkedList<GraphNode> unvisited = new LinkedList<GraphNode>();
		char otherColor = otherColor(color);
		for(GraphNode e : edges) {
			if(e.color == 'n') {
				e.color = otherColor;
				e.level = level+1;
				unvisited.add(e);
			}
		}
		return unvisited;
	}
	
	
	// Much the same as above, but for the odd cycle finder. 
	// As such, it uses 'V' as the indication for visited nodes, and 
	// it only stops if the cycle path is going to be odd upon completion
	public LinkedList<GraphNode> aStarVisitEdges(GraphNode finish){
		LinkedList<GraphNode> unvisited = new LinkedList<GraphNode>();
		for(GraphNode e : edges) {
			if(e.color != 'V' && !(e==finish && path.size()%2==0)) {
				e.color = 'V';
				e.path.addAll(path);
				e.path.add(this);
				unvisited.add(e);
			}
		}
		return unvisited;
	}
	
	private char otherColor(char c){
		if(c == 'B') return 'R';
		else return 'B';
	}
	
	// So that we may order our nodes based on level, which becomes handy in the odd cycle searcher
	public int compareTo(Object o) {
		if(!(o instanceof GraphNode)) return 0;
		GraphNode other =  (GraphNode)o;
		return other.level - this.level;
	}
	
	// And all below are simply printing helper methods for output and debugging 
	private String edgesString(){
		String s = "";
		for(GraphNode e : edges)
			s+= ", "+e.name;
		return "( "+s.substring(2)+" )";
	}
	
	public String moreInfo(){
		return "N: "+name+", C: "+color+", L: "+level+" E: "+edgesString();
	}
	
	public String toString(){
		return ""+name+" "+color;
	}

	

}
