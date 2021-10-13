import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Neuron {
	
	Random rand = Network.rand;
	
	Neuron[] inputs;
	float[] weights;
	float output;
	
	float error = 0;
	
	public Neuron() {
	}
	
	public Neuron(Neuron[] prevInputs) {
		inputs = new Neuron[prevInputs.length];
		weights = new float[prevInputs.length];
		for(int i=0; i<inputs.length; i++) {
			inputs[i] = prevInputs[i];
			weights[i] = (rand.nextFloat()*2)-1;
		}
	}
	
	public Neuron(Neuron[] prevNeurons, float[] _weights, float _output, float _error) {
		output = _output;
		error = _error;
		inputs = prevNeurons;
		weights = _weights;
	}
	
	public void activate() {
		float sum = 0;
		for(int i=0; i<inputs.length; i++) {
			sum += inputs[i].output * weights[i];
		}
		output = sigmoid(sum);
		error = 0;
	}
	
	void setError(float desired) {
		error = desired - output;
	}
	
	private float sigmoid(float x) {
		return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
	}
	
	public String toString() {
		String str = "o: "+output+" e: "+error;
		if(inputs == null) return str;
		str += " \ni:";
		for(Neuron n : inputs) 
			str += " "+n.output;
		str += "\nw:";
		for(float f : weights) 
			str += " "+f;
		return str;
	}

}
