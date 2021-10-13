import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class TestFrame extends JFrame{
	public TestFrame() {
		setTitle("Test Frame");
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {60, 60, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JButton btnNewDevice = new JButton("New Device");
		btnNewDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewDevice = new GridBagConstraints();
		gbc_btnNewDevice.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewDevice.anchor = GridBagConstraints.WEST;
		gbc_btnNewDevice.gridx = 3;
		gbc_btnNewDevice.gridy = 6;
		getContentPane().add(btnNewDevice, gbc_btnNewDevice);
		
		JButton btnSearchDevices = new JButton("Search Devices");
		GridBagConstraints gbc_btnSearchDevices = new GridBagConstraints();
		gbc_btnSearchDevices.gridx = 4;
		gbc_btnSearchDevices.gridy = 6;
		getContentPane().add(btnSearchDevices, gbc_btnSearchDevices);
	}

}
