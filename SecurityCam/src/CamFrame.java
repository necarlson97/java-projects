import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class CamFrame extends JFrame implements WindowListener, Runnable {

	Webcam cam;
	WebcamPanel panel;

	Thread thread;
	CamFrame(Webcam _cam) {
		cam = _cam;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		addWindowListener(this);
		setTitle("Security");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cam.setViewSize(WebcamResolution.VGA.getSize());
		cam.open();

		panel = new WebcamPanel(cam);
		panel.setFPSDisplayed(true);
		panel.setMirrored(true);
		this.add(panel);
		
//		ImageIcon img = new ImageIcon(cam.getImage());
//		JLabel label = new JLabel("", img, JLabel.CENTER);
//		this.add(label);
		

		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		cam.close();

	}

	@Override
	public void windowIconified(WindowEvent e) {
		panel.pause();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		panel.resume();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
