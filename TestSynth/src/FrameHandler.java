import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FrameHandler extends JPanel implements ActionListener, ChangeListener{
	
	
	
	// Inputs
	JButton quitBtn = new JButton("Quit");
	JSlider oscType = new JSlider(JSlider.VERTICAL,0,4,1);
	
	
	// Text Labels
	JLabel oscLabel = new JLabel("Oscelator",0);
	
	
	private int osc = 0;
	
	public FrameHandler(){
		
		JFrame frame = new JFrame("Synth");
		this.setFont(new Font("Helvetica",Font.PLAIN,12));		
		
		// Frame settings
		frame.setVisible(true);
	    frame.setFocusable(true);
		frame.setResizable(false);
		frame.setSize(1024, 768);
	    frame.setBackground(Color.BLACK);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    // Slider settings
	    oscType.setFont(new Font("Helvetica",Font.PLAIN,12));
	    oscType.setMajorTickSpacing(1);
		oscType.setMinorTickSpacing(1);
		oscType.setPaintTicks(true);
		oscType.setSnapToTicks(true);
		oscType.setPaintLabels(true);
	    
	    // Add components
		frame.add(quitBtn);
		frame.add(oscLabel);
		frame.add(oscType);
	
		// Add input listeners
		quitBtn.addActionListener(this);
		oscType.addChangeListener(this);
	}
	
	public void paintComponent(Graphics g){
	    super.paintComponent(g);

	    //g.drawLine(10,10,100,75);
	}
	

	
	 public void actionPerformed(ActionEvent e){
		 if (e.getSource() == quitBtn) System.exit(0);
	 }

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();

	    int osc = (int)source.getValue();
	    
	    System.out.println(osc);
		
	}

}
