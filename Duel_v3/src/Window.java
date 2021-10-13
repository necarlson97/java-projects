

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	public Window(int width, int height, String title, Duel d){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setUndecorated(true);
		frame.add(d);
		frame.setVisible(true);
		frame.setFocusable(true);
	}

}
