import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class LazyAction implements Runnable { 
	static final Random rand = new Random();
	static final int delay = 100;

	public LazyAction(String s, Color c, Terminal t) {
		char ch = s.charAt(0);
		int tempDelay = 0;
		if(ch == ' ') tempDelay = 0;
		else if(ch == '.') tempDelay = delay * 5;
		else if(ch == '?') tempDelay = delay * 2;
		else tempDelay = (int) (delay * rand.nextFloat());

		Timer timer = new Timer(tempDelay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				t.printSlow(s.substring(1), c);
			}
			
		});

		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void run() {
	}
}