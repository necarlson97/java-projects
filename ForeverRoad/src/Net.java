import java.awt.Graphics;

public class Net implements Comparable<Net> {

	float learningRate = 0.01f;
	
	int nodePixels = 10;
	
	int layers = 2;
	int layerSize = 40;
	
	int x = 20;
	int y = 20;
	int width;
	
	Layer in;
	Layer[] hiddens;
	Layer out;
	
	boolean active;
	boolean training;
	
	int success;
	
	Net(int inSize, int hiddenLayers, int hiddenSize, int outSize) {
		
		in = new Layer(x, y, inSize, this);
		width += nodePixels;
		hiddens = new Layer[hiddenLayers];
		hiddens[0] = new Layer(x+width, y, hiddenSize, in, this);
		for(int i=1; i<hiddens.length; i++) {
			hiddens[i] = new Layer(x+width, y, hiddenSize, hiddens[i-1], this);
			width += nodePixels;
		}
		out = new Layer(x+width, y, outSize, hiddens[hiddens.length-1], this);
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
	
	void Train() {
		training = true;
		out.train();
		for(int i=hiddens.length-1; i>1; i--)
			hiddens[i].train();
		training = false;
	}
	
	void render(Graphics g) {
		in.render(g);
		for(int i=0; i<hiddens.length;i++ )
			hiddens[i].render(g);
		out.render(g);
	}
	
	void hiddenDrive() {
		ForeverRoad test = new ForeverRoad();
		Car car = test.car;
		test.setup();
		while(test.carDeaths == 0) {
			activate(test.car.whiskerIntersects());
			car.turn = out.nodes[0].value;
			car.accel = out.nodes[1].value;
			success = car.distance;
			test.update();
		}
	}
	
	
	public String toString() {
		String str = "in: "+in+"\n";
		for(int i=0; i<hiddens.length; i++)
			str += "hidden: "+hiddens[i]+"\n";
		str += "out: "+out+"\n";
		str += "Successs: "+success;
		return str;
	}

	@Override
	public int compareTo(Net n) {
		if(this.success > n.success) return 1;
		else if(this.success == n.success) return 0;
		else return -1;
	}
	
}
