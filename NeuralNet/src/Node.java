import java.util.Random;

public class Node {
	
	public float value;
	public float[] weights;
	public Random r = new Random();
	
	// Generates random weights, but is given a value
	public Node(float value, int nextLayerSize){
		this.value = value;
		this.weights = generateNewWeights(nextLayerSize);
	}
	
	// Is given a value and weights
	public Node(float value, float[] weights){
		this.value = value;
		this.weights = weights;
	}
	
	// Creates new node identical to the one given
	public Node(Node givenNode) {
		this.value = givenNode.value;
		this.weights = givenNode.weights.clone();
	}
	
	// Generates random value and random weights, to create an origonal node FOR TESTING ONLY
	public Node(int nextLayerSize){
		this.value = r.nextFloat();
		this.weights = generateNewWeights(nextLayerSize);
	}

	public float calculateNextLayerNode(int index){
		if(weights == null || index > weights.length) return -1;
		
		return value*weights[index];
	}
	
	private float[] generateNewWeights(int nextLayerSize){
		float[] tempWeights = new float[nextLayerSize];
		for(int i = 0; i<nextLayerSize; i++){
			tempWeights[i] = r.nextFloat();
		}
		return tempWeights;
	}
	
	public String toString(){
		return "["+String.valueOf(value)+"]";
	}

}
