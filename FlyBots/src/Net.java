import java.awt.Graphics;

public class Net {

	float learningRate = 0.01f;
	
	static int nodePixels = 10;
	
	int x = 20;
	int y = 20;
	int width;
	int height;
	
	Layer in;
	Layer[] hiddens;
	Layer out;
	
	boolean active;
	boolean training;
	
	Net(int inSize, int hiddenLayers, int hiddenSize, int outSize, Point loc) {
		x = (int) loc.x;
		y = (int) loc.y;
		in = new Layer(x, y, inSize, this);
		width += nodePixels;
		hiddens = new Layer[hiddenLayers];
		hiddens[0] = new Layer(x+width, y, hiddenSize, in, this);
		width += nodePixels;
		for(int i=1; i<hiddens.length; i++) {
			hiddens[i] = new Layer(x+width, y, hiddenSize, hiddens[i-1], this);
			width += nodePixels;
		}
		out = new Layer(x+width, y, outSize, hiddens[hiddens.length-1], this);
		width += nodePixels;
		height = (1 + hiddenSize + 1) * nodePixels;
	}
	
	Net (Net n) {
		x = n.x;
		y = n.y;
		in = new Layer(n.in, this);
		hiddens = new Layer[n.hiddens.length];
		hiddens[0] = new Layer(n.hiddens[0], in, this);
		for(int i=1; i<hiddens.length; i++) {
			hiddens[i] = new Layer(n.hiddens[i], hiddens[i-1], this);
		}
		out = new Layer(n.out, hiddens[hiddens.length-1], this);
		width = n.width;
		height = n.height;
	}
	
	void randomize() {
		in = new Layer(x, y, in.nodes.length, this);
		hiddens[0] = new Layer(x+width, y, hiddens[0].nodes.length, in, this);
		for(int i=1; i<hiddens.length; i++) {
			hiddens[i] = new Layer(x+width, y, hiddens[0].nodes.length, hiddens[i-1], this);
			width += nodePixels;
		}
		out = new Layer(x+width, y, out.nodes.length, hiddens[hiddens.length-1], this);
	}
	
	void activate(float[] inputs) {
		active = true;
		in.recieveInput(inputs);
		for(int i=0; i<hiddens.length; i++) {
			hiddens[i].activate();
		}
		out.activate();
		active = false;
	}
	
	float[] getOutput() {
		float[] output = new float[out.nodes.length];
		for(int i=0; i<output.length; i++) {
			output[i] = out.nodes[i].value;
		}
		return output;
	}
	
	void render(Graphics g) {
		in.render(g);
		for(int i=0; i<hiddens.length;i++ )
			hiddens[i].render(g);
		out.render(g);
	}
	
	public String toString() {
		String str = "in: "+in+"\n";
		for(int i=0; i<hiddens.length; i++)
			str += "hidden: "+hiddens[i]+"\n";
		str += "out: "+out+"\n";
		return str;
	}

	
	
}
