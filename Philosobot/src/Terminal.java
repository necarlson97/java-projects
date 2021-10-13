import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Terminal extends JFrame implements Runnable {

	Random rand = new Random();

	JLabel[] labels = new JLabel[20];

	boolean open = true;
	final String openDisplay = "> ";
	final String closedDisplay = "< ";
	JTextField input;
	JLabel inputLabel;
	int unit;
	Font f;

	Color userColor = Color.white;
	Color botColor = Color.orange;
	Color closedColor = Color.black;
	Color openColor = new Color(30, 30, 30);

	Thread thread;
	
	Driver driver;

	Terminal(Driver d) {
		super("Terminal");
		driver = d;
		thread = new Thread(this);
		thread.start();
	}

	void setup() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		unit = height / 40;
		
		f = new Font("Press Start 2p", 0, unit);
		
		// Set display panels
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(21, 1));
		for(int i = labels.length-1; i>=0; i--) {
			labels[i] = new JLabel();
			labels[i].setFont(f);
			p.add(labels[i]);
		}

		// Set user input field
		JPanel inputPanel = new JPanel();

		input = new JTextField(width / (f.getSize()+1));
		
		input.setBorder(new EmptyBorder(0,unit,0,unit));
		input.setFont(f);
		input.setForeground(userColor);
		input.setBackground(openColor);

		input.setCaretColor(openColor);

		input.requestFocus(false);
		input.addActionListener(inputAction);

		inputLabel = new JLabel(openDisplay);
		inputLabel.setFont(f);
		inputLabel.setForeground(userColor);
		
		inputPanel.setBackground(closedColor);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
		
		inputPanel.add(inputLabel);
		inputPanel.add(input);
		p.add(inputPanel);

		p.setBorder(new EmptyBorder(unit,unit,unit,unit));
		p.setBackground(closedColor);

		this.add(p);
		this.setUndecorated(true);
		
		this.setSize(width, height);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	ActionListener inputAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String t = e.getActionCommand();
			if(t.equals("q")) System.exit(0);
			println(t, userColor);
			input.setText("");
			
			driver.respond(t);
		}

	};


	void print(String s, Color c) {
		if(labels[0].getText().length()+s.length()+1 
				> labels[0].getWidth()/unit)
			println();
		labels[0].setForeground(c);
		labels[0].setText(labels[0].getText()+s);
	}
	
	void println() {
		println("", Color.black);
	}

	void println(String s, Color c) {
		int labelWidth = labels[0].getWidth()/unit;
		if(s.length() > labelWidth) {
			println(s.substring(0, labelWidth), c);
			println(s.substring(labelWidth), c);
			return;
		}
		
		for(int i=labels.length-1; i>0; i--) {
			labels[i].setText(labels[i-1].getText());
			labels[i].setForeground(labels[i-1].getForeground());
		}
		labels[0].setText(s);
		labels[0].setForeground(c);
	}
	
	void printlnSlow(String s, Color c) {
		close();
		println();
		printSlow(s, c);
	}

	void printSlow(String s, Color c) {
		if(s.length() == 0) {
			open();
			return;
		}
		print(s.charAt(0)+"", c);
		new LazyAction(s, c, this);
	}

	void open() {
		input.setEditable(true);
		input.setBackground(openColor);
		inputLabel.setText(openDisplay);
	}

	void close() {
		input.setEditable(false);
		input.setBackground(closedColor);
		inputLabel.setText(closedDisplay);
	}

	@Override
	public void run() {
		setup();
		while(true) {

		}
	}

}
