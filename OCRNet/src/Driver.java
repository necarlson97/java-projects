import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Driver extends Engine2D{
	
	ImageStripper imageStripper;
	Network network;

	int stage = 0;
	static int pixelSize = 18;
	static int cardSize = 28;
	static int hiddenSize = 7;
	
	static float learningRate = (float) 0.01;
	
	Result result;
	
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
			imageStripper = new ImageStripper(this);
			imageStripper.loadTrainingData();
			imageStripper.loadTestData();
			imageStripper.loadEricData();
			imageStripper.done = true;
			break;
		case 2:
			if(network == null) network = new Network(cardSize*cardSize, 
					hiddenSize*hiddenSize, this);
			network.randCard();
			break;
		case 3:
			for(int i=0; i<network.training.length; i++) {
				network.activate(network.training[i]);
				network.train(network.training[i].outputs);
			}
			result = new Result(network.tryCard(true));
			break;
		case 4:
			result = new Result(network.tryCard(false));
			break;
		case 5:
			break;
		}
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
			if(imageStripper != null) imageStripper.render(g);
			break;
		default:
			if(network != null) network.render(g);
			if(result != null) result.render(g);
			g.setColor(Color.WHITE);
			if(stage == 3) g.drawString("", 500, 20); // Eric Test
			else if(stage == 4) g.drawString("Generic Test", 500, 20);
			break;
		}
		g.setColor(Color.WHITE);
		g.drawString("Stage ["+stage+"] Press space", 50, 20);
	}
	
	public Card[] getTrainingCards() {
		if(imageStripper == null) return null;
		LinkedList<Card> cards = imageStripper.trainingCards;
		return cards.toArray(new Card[cards.size()]);
	}
	
	public Card[] getTestCards() {
		if(imageStripper == null) return null;
		LinkedList<Card> cards = imageStripper.testCards;
		return cards.toArray(new Card[cards.size()]);
	}
	
	public Card[] getEricCards() {
		if(imageStripper == null) return null;
		LinkedList<Card> cards = imageStripper.ericCards;
		return cards.toArray(new Card[cards.size()]);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE) advance();
		if(key == KeyEvent.VK_Z) {
			switch(stage) {
			case 2:
				if(network != null) network.randCard();
				break;
			case 3:
				if(network.done) result = new Result(network.tryCard(true));
				break;
			case 4:
				if(network.done) result = new Result(network.tryCard(false));
				break;
			}
		}
	}

}