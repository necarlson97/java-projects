import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;

public class Test extends DisplayEngine1 {
	
	JFileChooser fc;
	
	File file;

	@Override
	public void setup() {
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_O) {
			fc = new JFileChooser();
			fc.showOpenDialog(this);
			file = fc.getSelectedFile();
		}
		super.keyPressed(e);
	}

	@Override
	void render(Graphics2D g) {
		g.setColor(Color.white);
		if(file != null) g.drawString(file.getName(), 100, 100);
		
	}
	
	public static void main(String[] args) {
		new Test();
	}

}
