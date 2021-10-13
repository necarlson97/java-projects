

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Windowed extends Canvas{

	public Windowed(int width, int height, String title, SpaceTrails s){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(s);
		frame.setVisible(true);
		frame.setFocusable(true);
	}

}
