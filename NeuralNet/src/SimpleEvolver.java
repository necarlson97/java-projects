import java.util.Random;

public class SimpleEvolver implements Runnable{
	
	public NodeNet nodeNet;
	public float[] desiredOutput;
	public float mutability;
	private Random r = new Random();
	
	
	public SimpleEvolver(NodeNet nodeNet, float[] desiredOutput){
		this.nodeNet = nodeNet;
		this.desiredOutput = desiredOutput;
		this.mutability = (float) .1;
	}
	
	public SimpleEvolver(NodeNet nodeNet, float[] desiredOutput, float mutability){
		this.nodeNet = nodeNet;
		this.desiredOutput = desiredOutput;
		this.mutability = mutability;
	}
	
	public NodeNet mutate(){
		NodeNet newNodeNet = new NodeNet(nodeNet);
		for(NodeLayer layer : newNodeNet.layers){
			for(Node node : layer.nodes) {
				for(float weight : node.weights){
					if(r.nextFloat() < mutability && weight > 0.01 && weight < 0.99) {
						weight += (float) (r.nextInt(20)-10)/1000; // change the weight by +/- 0.01
					}
				}
			}
		}
		System.out.println("Mutated: "+newNodeNet);
		return newNodeNet;
	}
	
	public float calculateFitness(){
		float fitness = 0;
		float[] output = nodeNet.getOutput();
		for(int i=0; i<desiredOutput.length; i++){
			fitness += Math.abs(output[i] - desiredOutput[i]);
		}
		return fitness;
	}

	@Override
	public void run() {
		System.out.println("This node net: "+nodeNet);
		NodeNet childOne = mutate();
		NodeNet childTwo = mutate();
		System.out.println("Child One: "+childOne);
		System.out.println("Child Two: "+childTwo);
		
	}
	

}
