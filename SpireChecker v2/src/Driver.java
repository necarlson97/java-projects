import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class Driver {
	
	String user;
	String pass;
	Boolean quiet;
	int cartIndex;
	int repeatInterval;
	
	String baseUser = "";
	String basePass = "";
	
	SpireChecker current = null;
	
	JFrame gui;
	
	Driver thisDriver = this;
	
	public static void main(String[] args) {
		new Driver();
	}
	
	public Driver() {
		setupGui();
	}
	
	JTextField userField;
	JPasswordField passField;
	JComboBox dropDown;
	JCheckBox quietBox;
	JFormattedTextField numbField;

	private void setupGui() {
		
		gui = new JFrame();
		Container pane = gui.getContentPane();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Spire checker");
		gui.setLocationRelativeTo(null);
		
		gui.add(new JLabel("Username: "));
		userField = new JTextField(20);
		userField.setText(baseUser);
		gui.add(userField);
		
		gui.add(new JLabel("Password: "));
		passField = new JPasswordField(20);
		passField.setText(basePass);
		gui.add(passField);
		
		gui.add(new JLabel("Cart Index: "));
		String[] numbs = {"0", "1", "2", "3", "4", "5", "6", 
						"7", "8", "9", "10", "11", "12"};
		dropDown = new JComboBox<String>(numbs);
	    gui.add(dropDown);
		
		
		gui.add(new JLabel("Time Interval (mins): "));
	    NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    numbField = new JFormattedTextField(formatter);
	    numbField.setText("30");
	    gui.add(numbField);
	    
	    quietBox =  new JCheckBox("Invisible (unfinished)");
		//quietBox.setSelected(true);
		gui.add(quietBox);
		quietBox.isSelected();
		
		JButton submitBut = new JButton("Submit?");
		submitBut.addActionListener(submit);
		gui.add(submitBut, BorderLayout.SOUTH);
		
		gui.setVisible(true);
		
		gui.setSize(350, 160);
		gui.setResizable(false);
	}
	
	ActionListener submit = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(current == null) {
				setData();
				schedual();
				changeGui("Connecting...");
			}
		}
		
	};
	
	public void changeGui(String text) {
		gui.getContentPane().removeAll();
		gui.add(new JLabel(text));
		gui.repaint();
		gui.setVisible(true);
	}
	
	private void setData() {
		user = userField.getText();
		pass = new String(passField.getPassword());
		quiet = quietBox.isSelected();
		cartIndex = dropDown.getSelectedIndex();
		repeatInterval = Integer.parseInt(numbField.getText());
	}
	
	private void schedual() {
		
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(runSpireChecker, 0, repeatInterval, TimeUnit.MINUTES);
		
	}

	Runnable runSpireChecker = new Runnable() {

		@Override
		public void run() {
			current = new SpireChecker(user, pass, quiet, cartIndex, thisDriver);
		}
		
	};

}
