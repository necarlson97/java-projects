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
	
	int x;
	int y;
	
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
	
	Node(Node n, Net net) {
		x = n.x;
		y = n.y;
		this.net = net;
		
		ins = new Node[0];
		value = n.value;
	}
	
	Node(Node n, Layer prev) {
		x = n.x;
		y = n.y;
		net = prev.net;
		
		ins = new Node[prev.nodes.length];
		weights = n.weights;
		for(int i=0; i<ins.length; i++) {
			ins[i] = prev.nodes[i];
		}
		value = n.value;
	}
	
	void activate() {
		active = true;
		float sum = 0;
		for(int i=0; i<ins.length; i++)
			sum += ins[i].value * weights[i];
		value = SigmoidTools.sigmoid(sum);
		active = false;
	}
	 
	void render(Graphics g) {
		g.setColor(valueColor(value));
		g.fillOval(x, y, net.nodePixels, net.nodePixels);
		
		if(active) g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawOval(x, y, net.nodePixels, net.nodePixels);
	}
	
	static Color valueColor(float v) {
		if(v < 0 || v > 1) {
			System.out.println("Bad Color Value: "+v);
			return Color.red;
		}
		return new Color(v, v, v);
	}

}
