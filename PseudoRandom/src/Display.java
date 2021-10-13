import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Display extends Game {
	
	boolean[] bits = new boolean[1];

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		
	}

	@Override
	void click(MouseEvent e) {
		
	}

	@Override
	void keyPressed(KeyEvent e) {
		next();
	}

	@Override
	void keyReleased(KeyEvent e) {
		
	}

	@Override
	void runGame() {
		
	}
	
	void next() {
		newBits = new boolean[bits.length*4];
		for(int i=0; i<bits.length; i++) {
			bits[i] = r.nextBoolean();
		}
	}

	@Override
	void renderGame(Graphics2D g) {
		int squareRoot = (int) Math.sqrt(bits.length);
		
		int squareHeight = windowHeight / squareRoot;
		int squareWidth = windowWidth / squareRoot;
		
		
		
		for(int i=0; i<squareRoot; i++) {
			for(int j=0; j<squareRoot; j++) {
				if(bits[(squareRoot*i)+j]) g.setColor(Color.red);
				else g.setColor(Color.white);
				g.fillRect(j*squareWidth, i*squareHeight, squareWidth, squareHeight);
			}
		}
		
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
