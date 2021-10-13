import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Interface {
	
	JFrame f;
	
	public Interface() {
		setupFrame();
	}

	private void setupFrame() {
		f = new TestFrame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setSize(450, 300);
		
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Interface();
	}

}
