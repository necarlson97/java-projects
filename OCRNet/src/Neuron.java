import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Neuron {
	
	Random rand = Engine2D.rand;
	
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

	public void render(Graphics g, int x, int y) {
		g.setColor(new Color(output, output, output));
		g.fillOval(x, y, Driver.pixelSize, Driver.pixelSize);
		g.setColor(Color.white);
		g.drawOval(x, y, Driver.pixelSize, Driver.pixelSize);
	}

}
