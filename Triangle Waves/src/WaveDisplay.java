
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class WaveDisplay extends Game{
	
	int pointNumb = 9;
	PointSet points;
	
	boolean jostle = false;
	
	public static void main(String[] args) {
		new WaveDisplay();
	}

	@Override
	void handleScreenResize() {
	}

	@Override
	void setup() {
		points = new PointSet(pointNumb, this);
	}

	@Override
	void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D) jostle = true;
	}

	@Override
	void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_D) jostle = false;
	}

	@Override
	void runGame() {
		if(points != null) {
			points.run();
			int power = r.nextInt(10) - 5;
			if(power<0) power -= 5;
			else power += 5;
			if(jostle) points.jostle(power);
		}
	}

	@Override
	void renderGame(Graphics2D g) {
		g.setStroke(new BasicStroke(5));
		if(points != null) points.render(g);
	}

}
