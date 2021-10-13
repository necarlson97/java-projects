import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NeuralNet extends Canvas implements Runnable {
	
	Random r = new Random();

	private boolean isFullScreen = false;
	static boolean running = true;
	Thread netThread;
	int count;

	static int windowWidth;
	static int windowHeight;
	
	
	Layer[] layers = new Layer[7];
	int layerDepth = 14;
	int nodeDrawWidth= 15;
	int nodeDrawHeight = 10;
	
	float lastLayerSum = 0;

	public static void main(String[] args) {
		new NeuralNet();
	}

	public NeuralNet() {
		this.addKeyListener(new KeyInput());
		createWindow();

		netThread = new Thread(this);
		netThread.start();
	}

	public void createWindow() {
		if (isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this);
		} else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Neural Net", this);
		}
		nodeDrawWidth = (int) ((windowWidth * .8) / layers.length / 2);
		nodeDrawHeight = (windowHeight / layerDepth / 2);
	}

	@Override
	public void run() {
		for(int i=0; i<layers.length; i++){
			layers[i] = new Layer(layerDepth, i, nodeDrawWidth, nodeDrawHeight);
		}
		
		float[] inputs = zeroInputs();
		
		int hundredCount=0;
		while (running) {
			
			if(count % 100 == 0) hundredCount++;
			if(hundredCount <= layers.length) stepPropegateForward(inputs, hundredCount-1);
			
			render();
			count++;
			try {
				netThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private float[] zeroInputs(){
		float[] inputs = new float[layers[0].size];
		return inputs;
	}
	
	private float[] patternInputs(){
		float[] inputs = new float[layers[0].size];
		for(int i=0; i<inputs.length; i++){
			if(i%2==0) inputs[i] = 1;
			if(i%2==1) inputs[i] = -1;
			if(i%3==0) inputs[i] = 0;
		}
		return inputs;
	}
	
	private void stepPropegateForward(float[] inputs, int step) {
		
		layers[step].flash = true;
		if(step == 0) layers[0].values = inputs;
		else layers[step].feedForward(layers[step-1].values);
		if(step == layers.length-1) lastLayerSum = layers[layers.length-1].sum();
		
		//System.out.println(layers[step]);
		
	}

	private void fullPropegateForward(float[] inputs) {

		layers[0].values = inputs;
		System.out.println(layers[0]);
		for(int i=1; i<layers.length; i++){
			layers[i].feedForward(layers[i-1].values);
			System.out.println(layers[i]);
		}
		lastLayerSum = layers[layers.length-1].sum();
		
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		renderBackground(g);
		renerLayers(g);
		
		g.dispose();
		bs.show();

	}

	private void renerLayers(Graphics g) {
		for(Layer l : layers)
			l.render(g);
	}

	private void renderBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
		g.setColor(Color.GRAY);
		g.drawRect((int)(windowWidth*.8), 10, (int)(windowWidth*.2)-10, windowHeight-50);
		String printSum = "0";
		if(String.valueOf(lastLayerSum).length() > 5) printSum = String.valueOf(lastLayerSum).substring(0, 5);
		g.drawString("Last layer sum: "+printSum, (int)(windowWidth*.8)+10, 40);
		g.drawString("Hundreds: "+count/100, (int)(windowWidth*.8)+10, 30);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

}
