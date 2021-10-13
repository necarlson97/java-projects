import java.util.Collections;
import java.util.LinkedList;

public class NetBreeder {
	
	LinkedList<Net> nets = new LinkedList<Net>();
	
	int inSize;
	int hiddenLayers;
	int hiddenSize;
	int outSize;
	
	NetBreeder(int inSize, int hiddenLayers, int hiddenSize, int outSize) {
		this.inSize = inSize;
		this.hiddenLayers = hiddenLayers;
		this.hiddenSize = hiddenSize;
		this.outSize = outSize;
	}
	
	void newBatch(int size) {
		for(int i=0; i<size; i++) {
			Net newNet = new Net(inSize, hiddenLayers, hiddenSize, outSize);
			newNet.hiddenDrive();
			nets.add(newNet);
		}
	}
	
	Net getBest() {
		Collections.sort(nets);
		return nets.getFirst();
	}
	
	

}
