import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Display extends DisplayEngine1 {
	
	Blaster blaster;
	
	@Override
	public void setup() {
		setFrame("Hash Blast");
		
	}

	@Override
	public void update() {
		if(blaster == null) blaster = new Blaster(this);
	}

	@Override
	void render(Graphics2D g) {
		PrintBox.render(g);
		blaster.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
//		if(key == KeyEvent.VK_RIGHT) PrintBox.incrementFont();
//		if(key == KeyEvent.VK_LEFT) PrintBox.decrementFont();
		if(key == KeyEvent.VK_UP) blaster.changeAlg();

		if(key == KeyEvent.VK_H) blaster.loadHashes();
		if(key == KeyEvent.VK_B) blaster.loadBatch();
		
		if(key == KeyEvent.VK_L) blaster.tryHash();

		if(key == KeyEvent.VK_SPACE) blaster.hashing = !blaster.hashing;
		
		super.keyPressed(e);
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
