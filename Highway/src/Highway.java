import java.awt.Graphics2D;

public class Highway extends Game{

	static Player player;
	
	public static void main(String[] args){
		new Highway();
	}

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		player = new Player(windowWidth/2, windowHeight/2);
		
	}

	@Override
	void runGame() {
		player.run();
		
	}

	@Override
	void renderGame(Graphics2D g) {
		player.render(g);
		
	}
}
