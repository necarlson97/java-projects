
public class NodeNet {
	
	public int[] layerSizes;
	public int depth;
	public NodeLayer[] layers;
	
	public NodeNet(int[] layerSizes, float[] inputs){
		this.layerSizes = layerSizes;
		this.depth = layerSizes.length;
		this.layers = new NodeLayer[layerSizes.length];
		
		setInputNodes(inputs);
		propegateValues();
	}
	
	public NodeNet(NodeNet nodeNet){
		this.layerSizes = nodeNet.layerSizes;
		this.depth = layerSizes.length;
		this.layers = new NodeLayer[layerSizes.length];
		
		setGivenWeights(nodeNet.layers);
		propegateValues();
	}
	
	public NodeNet(int[] layerSizes){ // Generates random values FOR TESTING ONLY
		this.layerSizes = layerSizes;
		this.depth = layerSizes.length;
		this.layers = generateRandomLayers();
	}
	
	public void setGivenWeights(NodeLayer[] givenLayers){
		
		// set input nodes as the first layer from the given layers
		layers[0] = new NodeLayer(givenLayers[0]);
		
		// set the rest of the net node's values as '0.0', but give them weights
		for(int j=1; j<layers.length-1; j++){
			Node[] weightedNodes = new Node[layerSizes[j]];
			for(int n=0; n<weightedNodes.length; n++) {
				float[] weights = givenLayers[j].nodes[n].weights;
				weightedNodes[n] = new Node(0, weights);
			}
			layers[j] = new NodeLayer(weightedNodes);
		}
		
		// And lastly, some blank output nodes
		Node[] blankNodes = new Node[layerSizes[depth-1]];
		for(int n=0; n<blankNodes.length; n++){
			blankNodes[n] = new Node(0, 0);
		}
		layers[depth-1] = new NodeLayer(blankNodes);
	}
	
	public void setInputNodes(float[] inputs){
		Node[] inputNodes = new Node[inputs.length];
		for(int i=0; i<inputs.length; i++){
			inputNodes[i] = new Node(inputs[i], layerSizes[1]);
		}
		layers[0] = new NodeLayer(inputNodes, layerSizes[1]);
		
		// set the rest of the net as '0.0' for now, just so they are initialized for propagation
		for(int j=1; j<layers.length-1; j++){
			Node[] blankNodes = new Node[layerSizes[j]];
			for(int n=0; n<blankNodes.length; n++) {
				blankNodes[n] = new Node(0, layerSizes[j+1]);
			}
			layers[j] = new NodeLayer(blankNodes);
		}
		
		// And lastly, some blank output nodes
		Node[] blankNodes = new Node[layerSizes[depth-1]];
		for(int n=0; n<blankNodes.length; n++){
			blankNodes[n] = new Node(0, 0);
		}
		layers[depth-1] = new NodeLayer(blankNodes);
		
	}
	
	public void propegateValues(){
		for(int l=0; l<layers.length-1; l++){
			NodeLayer layer = layers[l];
			for(int n=0; n<layer.nodes.length; n++){
				Node node = layer.nodes[n];
				for(int w=0; w<node.weights.length; w++){
					float[] weights = node.weights;
					float nextLayerNode = node.value * weights[w];
					layers[l+1].nodes[w].value += nextLayerNode;
				}
			}
		}
	}
	
	public float[] getOutput() {
		float[] output = new float[layerSizes[depth-1]];
		int i=0;
		for(Node n : layers[depth-1].nodes){
			output[i]=n.value;
			i++;
		}
		return output;
	}

	private NodeLayer[] generateRandomLayers() {
		NodeLayer[] tempLayers = new NodeLayer[depth];
		
		// Generates the first and the middle layers, using the current and next layer size
		for(int i=0; i<depth-1; i++){
			tempLayers[i] = new NodeLayer(layerSizes[i], layerSizes[i+1]); 
		}
		// Generates the last layer, with no 'next layer size'
		tempLayers[depth-1] = new NodeLayer(layerSizes[depth-1]);
		
		return tempLayers;
	}
	
	public String toString(){
		String returnString = "";
		for(NodeLayer l : layers){
			returnString += l + "\n";
		}
		return returnString;
	}
}
