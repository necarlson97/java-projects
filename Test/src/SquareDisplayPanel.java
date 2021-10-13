import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SquareDisplayPanel extends JPanel implements ActionListener {
	JButton quitBtn = new JButton("Quit");
	JButton changePosBtn = new JButton("Change Pos");
	JLabel xPosLbl = new JLabel("x pos");
	JLabel yPosLbl = new JLabel("y pos");
	JTextField xPosTxt = new JTextField(5);
	JTextField yPosTxt = new JTextField(5);
	int xpos = 200, ypos = 100, sideLen = 50;

	public SquareDisplayPanel() {
		setPreferredSize(new Dimension(475, 325));
		setBackground(Color.LIGHT_GRAY);

		this.add(quitBtn);
		this.add(changePosBtn);
		quitBtn.addActionListener(this);
		changePosBtn.addActionListener(this);

		this.add(xPosLbl);
		this.add(xPosTxt);
		this.add(yPosLbl);
		this.add(yPosTxt);

	} // end constructor

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawRect(xpos, ypos, xpos + sideLen, ypos + sideLen);

	} // end paintComponent

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quitBtn)
			System.exit(0);

		if (e.getSource() == changePosBtn) {
			ypos = Integer.parseInt(yPosTxt.getText());
			xpos = Integer.parseInt(xPosTxt.getText());
		}

	} // end actionPerformed
} // end SquarePosPanel class definition