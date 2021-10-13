import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Node {
	
	Random rand = Engine2D.rand;
	
	Net net;
	
	float value;
	Node[] ins;
	float[] weights;
	
	private boolean active;
	private boolean training;
	
	int x;
	int y;
	
	float error;
	
	// An input node needs no previous layer
	Node(int x, int y, Net net) {	
		this.x = x;
		this.y = y;
		this.net = net;
		
		ins = new Node[0];
	}
	
	// A hidden layer or output does
	Node(int x, int y, Layer prev) {
		this.x = x;
		this.y = y;
		net = prev.net;
		
		ins = new Node[prev.nodes.length];
		weights = new float[prev.nodes.length];
		for(int i=0; i<ins.length; i++) {
			ins[i] = prev.nodes[i];
			weights[i] = (rand.nextFloat()*2)-1;
		}
	}
	
	void activate() {
		active = true;
		float sum = 0;
		for(int i=0; i<ins.length; i++)
			sum += ins[i].value * weights[i];
		value = sigmoid(sum);
		active = false;
	}
	
	void train() {
		training = true;
		float delta = (float) ((1.0 - value) * 
				(1.0 + value) * error * net.learningRate);
		for(int i=0; i<ins.length; i++) {
			weights[i] += ins[i].value * delta;
		}
		training = false;
	}
	
	private float sigmoid(float x) {
		return (float) (1/( 1 + Math.pow(Math.E,(-1*x))));
	}
	 
	void render(Graphics g) {
		g.setColor(valueColor());
		g.fillOval(x, y, net.nodePixels, net.nodePixels);
		
		if(active) g.setColor(Color.red);
		else if(training) g.setColor(Color.green);
		else g.setColor(Color.white);
		g.drawOval(x, y, net.nodePixels, net.nodePixels);
	}
	
	private Color valueColor() {
		return new Color(value, value, value);
	}

}
