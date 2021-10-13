import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Network {
	
	static Random rand = new Random();
	
	Card[] training;
	Card[] test;
	Neuron[] inputLayer;
	Card currCard;
	
	Neuron[][] hiddenLayers;
	Neuron[] outputLayer;
	
	Driver driver;
	
	
	Network(Driver _driver, Neuron[] _inputLayer, Neuron[][] _hiddenLayers, Neuron[] _outputLayer) {
		driver = _driver;
		
		inputLayer = _inputLayer;
		hiddenLayers = _hiddenLayers;
		outputLayer = _outputLayer;
	}
	
	void activate(Card card) {
		setInput(card);
		for(int l=0; l<hiddenLayers.length; l++) {
			for(int i=0; i<hiddenLayers[l].length; i++)
				hiddenLayers[l][i].activate();
		}
		
		for(int i=0; i<outputLayer.length; i++)
			outputLayer[i].activate();
		
	}
	
	float[] getOutputs(Neuron[] l) {
		float[] ret = new float[l.length];
		for(int i=0; i<ret.length; i++)
			ret[i] = l[i].output;
		return ret;
	}
	
	void setInput(Card card) {
		currCard = card;
		
		for(int i=0; i<inputLayer.length; i++) {
			inputLayer[i].output = card.inputs[i]; // float to bool
		}
	}
	
	Result tryCard(Card c) {
		activate(c);
		float ret[] = new float[outputLayer.length];
		for(int i=0; i<outputLayer.length; i++)
			ret[i] = outputLayer[i].output;
		
		Result r = new Result(ret);
		return r;
	}

}
