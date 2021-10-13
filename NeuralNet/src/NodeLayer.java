
public class NodeLayer {
	
	public int size;
	public int nextLayerSize;
	public Node[] nodes;
	
	// Given size, next layer size, and an array of nodes
	public NodeLayer(Node[] nodes, int nextLayerSize){
		this.size = nodes.length;
		this.nextLayerSize = nextLayerSize;
		this.nodes = nodes;
	}
	
	// Given an array of nodes (this is the output layer)
	public NodeLayer(Node[] nodes){
		this.size = nodes.length;
		this.nextLayerSize = 0;
		this.nodes = nodes;
	}
	
	// Given a layer, to be identical
		public NodeLayer(NodeLayer givenLayer, int nextLayerSize){
			this.size = nodes.length;
			this.nextLayerSize = nextLayerSize;
			this.nodes = setGivenNodes(givenLayer);
		}
	
	// Given a layer (this is the output layer)
	public NodeLayer(NodeLayer givenLayer){
		this.size = givenLayer.nodes.length;
		this.nextLayerSize = 0;
		this.nodes = setGivenNodes(givenLayer);
	}
	
	// Given size and next layer size, generates node array for this layer RANDOMIZED FOR TESTING
	public NodeLayer(int size, int nextLayerSize){
		this.size = size;
		this.nextLayerSize = nextLayerSize;
		this.nodes = generateRandomNodes();
	}
	
	// No next layer (this is the output layer) RANDOMIZED FOR TESTING
	public NodeLayer(int size){
		this.size = size;
		this.nextLayerSize = 0;
		this.nodes = generateRandomNodes();
	}
	
	private Node[] setGivenNodes(NodeLayer givenLayer){
		Node[] tempNodes = new Node[size];
		for(int i=0; i<givenLayer.nodes.length; i++){
			tempNodes[i] = new Node(givenLayer.nodes[i]);
		}
		return tempNodes;
	}
	
	private Node[] generateRandomNodes(){
		Node[] tempNodes = new Node[size];
		for(int i=0; i<size; i++){
			tempNodes[i] = new Node(nextLayerSize);
		}
		return tempNodes;
	}
	
	public String toString(){
		String returnString = "";
		for(Node n : nodes){
			returnString += n;
		}
		return returnString;
	}

}
