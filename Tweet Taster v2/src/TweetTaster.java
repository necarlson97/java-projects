import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TweetTaster extends Engine2D{

	Face face;
	TwitterState state;
	
	@Override
	public void setup() {
		state = new TwitterState();
		face = new Face(state.getEmotion());
	}

	@Override
	public void update() {
		if(state != null) state.update();
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new TweetTaster();
	}

}
