import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamStreamer;

public class Driver {

	public static void main(String[] args) {
		Webcam cam = Webcam.getDefault();
		CamFrame cf = new CamFrame(cam);
		
	}
}
