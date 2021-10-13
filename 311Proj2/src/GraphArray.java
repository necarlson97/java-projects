
public class GraphArray {

	// This array holds all the graph nodes. It starts at size two, but expands,
	// for it is my implementation of an array list. I have been meaning to try one, this
	// project seems like a good excuse
	// Each node is 'named' with a positive integer 1-n, and as such, they are stored there
	protected GraphNode[] array = new GraphNode[2];

	public GraphArray(){

	}

	// Just for protection against our of bounds errors on our array
	private boolean contains(int i){
		if(i >= array.length || array[i] == null) return false;
		return true;
	}

	// Adds a node to our array, doubling the size if need be
	public GraphNode add(GraphNode n){
		while(n.name >= array.length)
			array = doubleArray(array);
		array[n.name] = n;
		return n;
	}

	// A function for returning a node at that location, creating one if no node yet exists
	public GraphNode get(int i){
		if(!contains(i)) return add(new GraphNode(i));
		else return array[i];
	}

	// A function for returning an existing node at that location, NOT creating any new ones
	public GraphNode at(int i){
		if(!contains(i)) return null;
		else return array[i];
	}

	// A function to double our array size for when it has reached capacity
	private GraphNode[] doubleArray(GraphNode[] input) {
		GraphNode[] output = new GraphNode[2*input.length];
        System.arraycopy(output, 0, input, 0, input.length);
		return output;
	}

	// The current capacity of the list
	int getCurrMax(){
		return array.length;
	}

	public String toString(){
		String s = "";
		for(GraphNode n : array){
			if(n!=null) s+= n+"\n";
		}
		return s;
	}

}
