import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Neuron {
	
	Random rand = Network.rand;
	
	Neuron[] inputs;
	float[] weights;
	float output;
	
	float error = 0;
	
	int x;
	int y;
	
	public Neuron(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Neuron(Neuron[] prevInputs, int x, int y) {
		this.x = x;
		this.y = y;
		inputs = new Neuron[prevInputs.length];
		weights = new float[prevInputs.length];
		for(int i=0; i<inputs.length; i++) {
			inputs[i] = prevInputs[i];
			weights[i] = (rand.nextFloat()*2)-1;
		}
	}
	
	public Neuron(Neuron[] prevNeurons, float[] _weights, float _output, float _error, int x, int y) {
		this.x = x;
		this.y = y;
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
	
	void train() {
		float delta = (float) ((1.0 - output) * 
				(1.0 + output) * error * Driver.learningRate);
		for(int i=0; i<inputs.length; i++) {
			inputs[i].error += weights[i] * error;
			weights[i] += inputs[i].output * delta;
		}
	}
	
	private float sigmoid(float x) {
		return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
	}

	public void render(Graphics g) {		
		g.setColor(clampColor(output, output, output));
		g.fillOval(x, y, Driver.pixelSize, Driver.pixelSize);
		g.setColor(clampColor(error, .1f, .1f));
		g.drawOval(x, y, Driver.pixelSize, Driver.pixelSize);
	}

	public void renderLines(Graphics g) {
		if(inputs == null) return;
		for(int i=0; i<inputs.length; i++) {
			g.setColor(clampColor(weights[i], weights[i], weights[i]));
			g.drawLine(x+Driver.pixelSize/2, y+Driver.pixelSize/2, inputs[i].x, inputs[i].y);
		}
	}
	
	private Color clampColor(float f1, float f2, float f3) {
		if(f1 > 1) f1 = 1;
		else if(f1 < 0) f1 = 0;
		if(f2 > 1) f2 = 1;
		else if(f2 < 0) f2 = 0;
		if(f3 > 1) f3 = 1;
		else if(f3 < 0) f3 = 0;
		
		return new Color(f1, f2, f3);
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
