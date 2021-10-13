

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

public class FullScreen extends Canvas{
	
	public FullScreen(int width, int height, String title, SpaceTrails s){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setUndecorated(true);
		frame.add(s);
		frame.setVisible(true);
		frame.setFocusable(true);
	}

}
