import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Network implements Runnable {
	
	static Random rand = new Random();
	
	Card[] training;
	Card[] test;
	Neuron[] inputLayer;
	Card currCard;
	
	Neuron[][] hiddenLayers;
	Neuron[] outputLayer;
	
	Driver driver;
	
	boolean done = false;
	
	Thread thread;
	
	int tried = 0;
	int success = 0;
	
	Network(int inSize, int hiddens, int hiddenSize, int outSize, Driver d) {
		driver = d;
		inputLayer = new Neuron[inSize];
		hiddenLayers = new Neuron[hiddens][hiddenSize];
		outputLayer = new Neuron[outSize];
		
		thread = new Thread(this);
		
		int x = 20;
		createNeurons(inputLayer, null, 2, x, 50);
		x += 150;
		createNeurons(hiddenLayers[0], inputLayer, 1, x, 50);
		for(int i=1; i<hiddenLayers.length; i++) {
			x += Driver.pixelSize*2;
			createNeurons(hiddenLayers[i], hiddenLayers[i-1], 1, x, 50);
		}
		x += 50;
		createNeurons(outputLayer, hiddenLayers[hiddenLayers.length-1], 1, x, 50);

		training = Stripper.trainingCards.toArray(new Card[Stripper.trainingCards.size()]);
		test = Stripper.testCards.toArray(new Card[Stripper.testCards.size()]);
		
		setInput(training[rand.nextInt(training.length)]);
		
	}
	
	Network(Driver _driver, int _tried, int _success, 
			Neuron[] _inputLayer, Neuron[][] _hiddenLayers, Neuron[] _outputLayer) {
		driver = _driver;
		tried = _tried;
		success = _success;
		inputLayer = _inputLayer;
		hiddenLayers = _hiddenLayers;
		outputLayer = _outputLayer;
		
		thread = new Thread(this);
		
		training = Stripper.trainingCards.toArray(new Card[Stripper.trainingCards.size()]);
		test = Stripper.testCards.toArray(new Card[Stripper.testCards.size()]);
		
		setInput(training[rand.nextInt(training.length)]);
	}
	
	
	void createNeurons(Neuron[] layer, Neuron[] pastLayer, int cols, int startingX, int startingY) {
		int i = 0;
		int x = startingX;
		int y = startingY;
		int rows = (layer.length+1) / cols;
		for(int col=0; col<cols; col++) {
			for(int row=0; row<rows; row++) {
				
				if(i >= layer.length) return;
				if(pastLayer == null) layer[i] = new Neuron(x, y);
				else layer[i] = new Neuron(pastLayer, x, y);
				y += Driver.pixelSize;
				i++;
			}
			y = startingY;
			x += Driver.pixelSize;
		}
			
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
	
	void train(float[] outputs) {
		for(int i=0; i<outputLayer.length; i++) {
			outputLayer[i].setError(outputs[i]);
			outputLayer[i].train();
		}
		
		for(int l=0; l<hiddenLayers.length; l++) {
			for(int i=0; i<hiddenLayers[l].length; i++) {
				hiddenLayers[l][i].train();
			}
		}
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
	
	public void render(Graphics g) {
		currCard.render(g);
		
		renderLines(g, inputLayer);
		for(Neuron[] hiddenLayer : hiddenLayers)
			renderLines(g, hiddenLayer);
		renderLines(g, outputLayer);
		
		renderLayer(g, inputLayer);
		for(Neuron[] hiddenLayer : hiddenLayers)
			renderLayer(g, hiddenLayer);
		renderLayer(g, outputLayer);
		
		g.setColor(Color.WHITE);
		if(tried > 0) g.drawString("Success: "+((float)success/tried), 50, 700);
	}
	
	private void renderLines(Graphics g, Neuron[] layer) {
		if(!Driver.showLines) return;
		for(int i=0; i<layer.length; i++) {
			layer[i].renderLines(g);
		}
	}
	
	private void renderLayer(Graphics g, Neuron[] layer) {
		for(int i=0; i<layer.length; i++) {
			layer[i].render(g);
		}
	}

	public void randCard() {
		setInput(training[rand.nextInt(training.length)]);
	}
	
	Result tryCard() {
		Card c;
		if(test.length == 0) c = training[rand.nextInt(training.length)];
		else c = test[rand.nextInt(test.length)];
		
		return tryCard(c);
	}
	
	Result tryCard(Card c) {
		activate(c);
		
		if(c.output != null) train(c.outputs);
		
		float ret[] = new float[outputLayer.length];
		for(int i=0; i<outputLayer.length; i++)
			ret[i] = outputLayer[i].output;
		
		Result r = new Result(ret);
		tried ++;
		if(r.type.equals(c.output)) success ++;
		return r;
	}

	@Override
	public void run() {
		int i=0;
		while(!done) {
			tryCard(training[i]);
			i = (i+1) % training.length;
		}
		done = true;
	}
	
	void save() {
		FileWriter.saveNetwork(this);
	}
	
	void load() {
		driver.network = FileWriter.loadNetwork(this);
	}

}
