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
		if(blaster == null) blaster = new Blaster();
	}

	@Override
	void render(Graphics2D g) {
		PrintBox.render(g);
		blaster.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT) PrintBox.incrementFont();
		if(key == KeyEvent.VK_LEFT) PrintBox.decrementFont();
		if(key == KeyEvent.VK_1) blaster.setAlgorythm("sha1");
		if(key == KeyEvent.VK_2) blaster.setAlgorythm("sha256");
		if(key == KeyEvent.VK_3) blaster.setAlgorythm("md5");

		if(key == KeyEvent.VK_A) blaster.addToHashes("black176(md5)");
		if(key == KeyEvent.VK_S) blaster.addToBatch("john");
		if(key == KeyEvent.VK_D) blaster.bonusDigits(1, 2);

		if(key == KeyEvent.VK_Z) blaster.hashing = !blaster.hashing;
		
		super.keyPressed(e);
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
