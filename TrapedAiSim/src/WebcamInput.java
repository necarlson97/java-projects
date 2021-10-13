
import java.io.IOException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;


public class WebcamInput implements WebcamMotionListener {

	public WebcamInput() {
	}

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		TrappedAiSim.watched = true;
	}

	public static void main(String[] args) throws IOException {
		new WebcamInput();
		System.in.read(); // keep program open
	}
}
