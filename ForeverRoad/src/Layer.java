import java.awt.Color;
import java.awt.Graphics;

public class Layer {
	
	Net net;
	
	Node[] nodes;
	
	private boolean active;
	private boolean training;
	
	int x;
	int y;
	
	int height = 0;
	
	Layer(int x, int y, int size, Net net) {
		this.x = x;
		this.y = y;
		this.net = net;
		
		nodes = new Node[size];
		for(int i=0; i<nodes.length; i++) {
			nodes[i] = new Node(x, y+height, net);
			height += net.nodePixels;
		}
	}
	
	Layer(int x, int y, int size, Layer prev, Net net) {
		this.x = x;
		this.y = y;
		this.net = net;
		
		nodes = new Node[size];
		for(int i=0; i<size; i++) {
			nodes[i] = new Node(x, y+height, prev);
			height += net.nodePixels;
		}
	}
	
	void recieveInput(float[] inputs) {
		for(int i=0; i<nodes.length; i++)
			nodes[i].value = inputs[i];
	}
	
	void activate() {
		active = true;
		for(int i=0; i<nodes.length; i++)
			nodes[i].activate();
		active = false;
	}
	
	void train() {
		training = true;
		for(int i=0; i<nodes.length; i++)
			nodes[i].train();
		training = false;
	}
	
	void render(Graphics g) {
		for(int i=0; i<nodes.length; i++)
			nodes[i].render(g);
		
		if(active) g.setColor(Color.red);
		else if(training) g.setColor(Color.green);
		else return;
		g.drawRect(x, y, net.nodePixels, height);
	}
	
	public String toString() {
		String str = "["+nodes[0].value;
		for(int i=1; i<nodes.length; i++) {
			str+=", "+(nodes[i].value+"").substring(0, 3);
		}
		return str+"]";
	}

}
