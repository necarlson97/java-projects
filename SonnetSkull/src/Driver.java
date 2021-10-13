import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Driver extends DisplayEngine1 {
	
	boolean recording;
	Recorder recorder;
	Selector selector;
	Recognizer recognizer;
	Speaker speaker;
	
	public void setup() {
		selector = new Selector();		
		recorder = new Recorder();
		recognizer = new Recognizer();
		speaker = new Speaker();
	}

	@Override
	public void update() {
	}

	@Override
	void render(Graphics2D g) {
		if(recording) {
			g.setColor(Color.red);
			g.fillOval(0, 0, windowWidth, windowHeight);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_SPACE) toggleRecord();
	}
	
	private void toggleRecord() {
		if(!recording) {
			System.out.println("Requesting record");
			recording = true;
			recorder.begin();
		} else {
			System.out.println("Requesting finish");
			recording = false;
			recorder.finish();
			String r = recognizer.read(recorder.wavFile);
			r = r.replaceAll("tell me about", "");
			r = r.replaceAll("give me number", "");
			String s = selector.get(r);
			speaker.speak(s);
		}		
	}

	public static void main(String[] args) {
		new Driver();
	}

}
