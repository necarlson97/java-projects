import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class BestBarber extends Game {
	
	Follicle[] follicles = new Follicle[100];;

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		
		follicles = new Follicle[100];
		
		int x = -250;
		int xInterval = 5;

		int y = 0;
		
		for(int i=0; i<follicles.length; i++) {
			y = onArc(x, 100, 100);
			follicles[i] = new Follicle(new Point(x+windowWidth/2, y), this);
			x += xInterval;
		}
	}
	
	int onArc(int x, int width, int height) {
		
		double y = Math.sqrt( (((x*x)/(width*width))+1) )*height;
		System.out.println(y+", w: "+width+", h: "+height+", x: "+x);
		return (int) y;	
	}

	@Override
	void click(MouseEvent e) {
		
	}

	@Override
	void keyPressed(KeyEvent e) {
		
	}

	@Override
	void keyReleased(KeyEvent e) {
		
	}

	@Override
	void runGame() {
		
		for(Follicle f : follicles) {
			if(f != null) f.run();
		}
	}

	@Override
	void renderGame(Graphics2D g) {
		
		for(Follicle f : follicles) {
			if(f != null) f.render(g);
		}
		
	}
	
	public static void main(String[] args) {
		new BestBarber();
	}

}
