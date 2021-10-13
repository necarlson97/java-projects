

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	
	public Window(int width, int height, String title, NodeNetVisualizer n){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setUndecorated(false);
		frame.add(n);
		frame.setVisible(true);
		frame.setFocusable(true);
		n.run();
	}

}
