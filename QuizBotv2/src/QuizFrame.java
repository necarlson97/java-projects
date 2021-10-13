import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class QuizFrame extends JFrame implements KeyListener {
	
	JLabel[] labels = new JLabel[20];
	
	boolean open = true;
	final String openDisplay = ">";
	final String closedDisplay = "<";
	String display;
	String input = ""; 
	
	QuizFrame() {
		super("Philosobot");
		
		this.addKeyListener(this);
		this.setFont(new Font("Monaco", 0, 12));
		
		Panel p = new Panel();
		p.setLayout(new GridLayout(20, 0));
		for(int i = labels.length-1; i>=0; i--) {
			labels[i] = new JLabel();
			labels[i].setBorder(new EmptyBorder(0,10,0,0));
			p.add(labels[i]);
		}
		
		this.add(p);
		
		this.setSize(400, 300);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		this.requestFocus();
		
		close();
	}
	
	void print(String s) {
		labels[1].setText(labels[1].getText()+s);
	}
	
	void println(String s) {
		for(int i=labels.length-1; i>1; i--) {
			labels[i].setText(labels[i-1].getText());
		}
		labels[1].setText(s);
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		if(!open) return;
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER) {
			send();
		} else if(key == KeyEvent.VK_BACK_SPACE) {
			if(display.length() > 1) display = display.substring(0, display.length()-1);
		} else if(key >31 && key < 127) display += e.getKeyChar();
		
		labels[0].setText(display);
	}
	
	void send() {
		println(display);
		input = display.substring(1);
	}
	
	void open() {
		input = "";
		open = true;
		display = openDisplay;
		labels[0].setText(display);
	}
	
	void close() {
		input = "";
		open = false;
		display = closedDisplay;
		labels[0].setText(display);
	}

	@Override
	public void keyReleased(KeyEvent e) { }

}
