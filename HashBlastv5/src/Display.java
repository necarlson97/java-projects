import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Display implements KeyListener {
	
	JFrame frame = new JFrame();
	static int width = 500;
	static int height = 400;
	static int unit = width/10;
	
	Blaster blaster = new Blaster(this);
	
	JFileChooser fc;
	
	Display() {
		setupFrame();
		String home = System.getProperty("user.home");
		fc = new JFileChooser(new File(home+"/Downloads/"));
		fc.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
	}
	
	JTextField ammoField;
	JButton[] butts = {new JButton("get hashes"), new JButton("get dict"), 
			new JButton("blast em"), new JButton("close")};
	JProgressBar bar = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
	private void setupFrame() {
		frame.addKeyListener(this);
		
		Panel p = new Panel();
		p.setLayout(null);
		
		// Text Input
		ammoField = new JTextField(20);
		p.add(ammoField);
		ammoField.setSize(unit*5, unit/2);
		ammoField.setLocation(unit, 2*unit);
		ammoField.addActionListener(addAmmo);
		
		
		// Progress Bag
		p.add(bar);
		bar.setSize(width, unit);
		bar.setLocation(0, height-unit);
		
		// Buttons
		butts[0].addActionListener(loadHash);
		butts[1].addActionListener(loadDict);
		butts[2].addActionListener(blast);
		butts[3].addActionListener(e -> System.exit(0));
		for(JButton b : butts) {
			b.setSize(3*unit, 1*unit);
			p.add(b);
		}
		
		butts[0].setLocation(unit, 5*unit);
		butts[1].setLocation(6*unit, 5*unit);
		butts[2].setLocation(unit, 6*unit);
		butts[3].setLocation(6*unit, 6*unit);
		
		
		frame.add(p);

		
		frame.setSize(width, height);
		frame.setResizable(false);
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		frame.requestFocus();
	}

	ActionListener blast = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			blaster.startStage();
		}
	};
	
	ActionListener loadHash = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			fc.setDialogTitle("Open Hash");
			fc.showOpenDialog(frame);
			File file = fc.getSelectedFile();
		}
	};
	
	ActionListener loadDict = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			fc.setDialogTitle("Open Password Dictionary");
			fc.showOpenDialog(frame);
			File file = fc.getSelectedFile();
		}
	};
	
	ActionListener addAmmo = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Ammo: "+ammoField.getText());
			blaster.setAmmo(ammoField.getText());
			blaster.startStage(0);
		}
	};
	
	

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SPACE) blaster.startStage();
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
	}
	@Override
	public void keyReleased(KeyEvent e) { }
	@Override
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		new Display();
	}

}
