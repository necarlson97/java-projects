import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;

public class Display extends Engine2D{

	Operator op;
	static boolean debug = false;
	
	boolean paused = false;
	
	static Font font = new Font("Helvetica", 0, 20);
	
	int broodSize = 100;
	BotBreeder botBreeder;
	
	@Override
	public void setup() {
		botBreeder = new BotBreeder(broodSize);
		op = new Player();
	}

	@Override
	public void update() {
		if(op != null && !paused) {
			op.update();
			if(op.car.dead) op.car = new Car(op.road, op);
		}
	}

	@Override
	void render(Graphics2D g) {
		g.setFont(font);
		if(op != null) op.render(g);
		if(botBreeder != null) botBreeder.render(g);
		if(paused) renderPaused(g);
	}

	private void renderPaused(Graphics2D g) {
		g.setColor(Color.white);
		int fs = font.getSize();
		int x = (int) (windowWidth/2 - fs * 3);
		int y = (int) (windowHeight/2 - fs * 1.5);
		int width = fs * 6;
		int height = fs * 2;
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString("PAUSED", x+fs, y+fs);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(op != null) op.carKeysPressed(key);
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_F1) debug = true;
		if(key == KeyEvent.VK_P) paused = !paused;
		if(key == KeyEvent.VK_V) op = botBreeder.first();
		if(key == KeyEvent.VK_B) botBreeder.breed();
		if(key == KeyEvent.VK_N) botBreeder.everbreed = !botBreeder.everbreed;
		if(key == KeyEvent.VK_SPACE) op = new Player(); 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(op != null) op.carKeysReleased(key);
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
