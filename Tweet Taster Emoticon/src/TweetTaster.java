import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TweetTaster extends Engine2D{

	static Face face;
	static TwitterState state;
	
	@Override
	public void setup() {
		state = new TwitterState();
		face = new Face(state.getEmotion());
	}

	@Override
	public void update() {
		if(state != null) state.update();
	}
	
	public static void setEmotion(Emotion e) {
		if(face != null) face.currEmotion = e;
	}

	@Override
	void render(Graphics2D g) {
		if(face != null) face.render(g);
		if(state != null) state.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_RIGHT) face.currEmotion.incrementStage();
		if(key == KeyEvent.VK_LEFT) face.currEmotion.decrementStage();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		new TweetTaster();
	}

}
