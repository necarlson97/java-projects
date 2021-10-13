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
		blaster = new Blaster();
		
	}

	@Override
	public void update() {
		
	}

	@Override
	void render(Graphics2D g) {
		blaster.render(g);
		PrintBox.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		System.out.println("Pressed "+key);
		
		if(key == KeyEvent.VK_RIGHT) PrintBox.incrementFont();
		if(key == KeyEvent.VK_LEFT) PrintBox.decrementFont();
		if(key == KeyEvent.VK_1) blaster.check("sha1");
		if(key == KeyEvent.VK_2) blaster.check("sha256");
		if(key == KeyEvent.VK_3) blaster.check("md5");
		if(key == KeyEvent.VK_A) blaster.addBonusDigits(1, 2);
		if(key == KeyEvent.VK_B) blaster.modifyBase();
		super.keyPressed(e);
	}
	
	public static void main(String[] args) {
		new Display();
	}

}
