import java.awt.Color;
import java.awt.Graphics;

public class Layer {
	
	Net net;
	
	Node[] nodes;
	
	private boolean active;
	
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
	
	Layer(Layer l, Net net) {
		x = l.x;
		y = l.y;
		this.net = net;
		
		nodes = new Node[l.nodes.length];
		for(int i=0; i<nodes.length; i++) {
			nodes[i] = new Node(l.nodes[i], net);
		}
		height = l.height;
	}
	
	Layer(Layer l, Layer prev, Net net) {
		x = l.x;
		y = l.y;
		this.net = net;
		
		nodes = new Node[l.nodes.length];
		for(int i=0; i<nodes.length; i++) {
			nodes[i] = new Node(l.nodes[i], prev);
		}
		height = l.height;
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
	
	void render(Graphics g) {
		for(int i=0; i<nodes.length; i++)
			nodes[i].render(g);
		
		if(active) g.setColor(Color.red);
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
