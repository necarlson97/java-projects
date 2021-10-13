import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Builder extends Game{
	
	Maze maze;
	int delay = 1;
	int multiples = 10;
	
	public static void main(String[] args) {
		new Builder();
	}

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		maze = new BreadthMaze(100,100);
	}

	@Override
	void keyPressed(KeyEvent e) {
		
	}

	@Override
	void keyReleased(KeyEvent e) {
		
	}

	@Override
	void runGame() {
		if(maze != null && count%delay == 0) {
			for(int i=0; i<multiples; i++) maze.run();
		}
		
	}

	@Override
	void renderGame(Graphics2D g) {
		if(maze != null) maze.render(g);
		
	}

}
