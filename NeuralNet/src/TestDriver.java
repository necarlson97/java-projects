
public class TestDriver {
	
	public static void main(String[] args){
		
		int netDepth = 10;
		int inputSize = 2;
		int middleSize = 8;
		int outputSize = 2;
		
		float[] inputs = {1, 0};
		
		int[] layerSizes = new int[netDepth];
		layerSizes[0] = inputSize;
		for(int i=1; i<netDepth-1; i++){
			layerSizes[i] = middleSize;
		}
		layerSizes[netDepth-1] = outputSize;
		
		NodeNet nodeNet = new NodeNet(layerSizes, inputs);
		NodeNetVisualizer visualizer = new NodeNetVisualizer(nodeNet);
		visualizer.run();
		
		float[] desiredOutput = {0, 1};
		SimpleEvolver se = new SimpleEvolver(nodeNet, desiredOutput);
		visualizer.update(se.nodeNet);
		se.run();
	}

}
