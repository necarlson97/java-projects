import java.awt.Graphics2D;

public class EngineTest extends Game{
	
	static Player player;
	Triangle tri1;

	public static void main(String[] args) {
		new EngineTest();

	}

	@Override
	void setup() {
		player = new Player();
		tri1 = new Triangle(windowWidth/3, windowHeight/3, 10);
	}

	@Override
	void runGame() {
		player.run();
		
	}

	@Override
	void renderGame(Graphics2D g) {
		player.render(g);
		tri1.render(g);
	}
	
	void renderWall(){
		
	}

}
