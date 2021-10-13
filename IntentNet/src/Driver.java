import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Driver extends DisplayEngine1 {
	
	Stripper stripper;
	Network network;
	Console console = new Console();

	int stage = 0;
	static int pixelSize = 20;
	
	static final int hiddenCount = 2;
	static final int hiddenSize = 20;
	
	static float learningRate = (float) 0.05;
	
	Result result;
	
	static boolean showLines = false;
	
	public static void main(String[] args) {
		new Driver();
	}

	@Override
	public void setup() {
	}
	
	private void advance() {
		if(stage < 5) stage++;
		
		switch(stage) {
		case 1:
			stripper = new Stripper(this);
			break;
		case 2:
			if(network == null) network = new Network(Stripper.inBag.length, 
					hiddenCount, hiddenSize, Stripper.outBag.length, this);
			network.randCard();
			break;
		case 3:
			network.thread.start();
			break;
		default:
			if(network.done) tryResult();
			break;
		}
	}

	private void tryResult() {
		result = network.tryCard();
	}
	
	private void tryResult(String s) {
		result = network.tryCard(new Card(s));
		System.out.println(result);
	}

	@Override
	public void update() {
		
	}

	@Override
	void render(Graphics2D g) {
		switch(stage) {
		case 0:
			break;
		case 1:
			if(stripper != null) stripper.render(g);
			break;
		default:
			if(network != null) network.render(g);
			if(result != null) result.render(g);
			g.setColor(Color.WHITE);
			if(stage > 3) g.drawString("Test", 500, 20);
			break;
		}
		
		if(console != null) console.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		else if(key == KeyEvent.VK_1) advance();
		
		else if(network != null) {
			if(key == KeyEvent.VK_ENTER) {
				if(!network.done) network.done = true;
				tryResult(console.submit());
			}
			else if(key == KeyEvent.VK_2) network.save();
			else if(key == KeyEvent.VK_3) network.load();
			else if(key == KeyEvent.VK_BACK_SPACE) console.backspace();
			else if(key > 30 && key < 128) console.add(e.getKeyChar());
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
	}

}