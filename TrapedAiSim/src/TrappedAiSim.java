import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.Timer;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;


public class TrappedAiSim extends Canvas implements Runnable{
	public static final int WIDTH = 1440, HEIGHT = 900;
	private Random r = new Random();
	
	private Thought blank = new BlankThought();
	
	public static boolean watched;
	public static boolean keyboardTouched;
	
	//MidiOutput sound = new MidiOutput();
	WavOutput sound = new WavOutput();
	
	Thread AiThread;
	
	public static void main(String[] args){
		new TrappedAiSim();
	}
	
	public TrappedAiSim() {
		this.addKeyListener(new KeyInput());
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(500); // one check per 500 ms
		detector.addMotionListener(new WebcamInput());
		detector.start();
		new Window(WIDTH, HEIGHT, "Hello World", this);
		AiThread=new Thread(this);
        AiThread.start();
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }


	public void render(Thought t) {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			this.render(t);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
	
		g.setColor(t.getBGColor());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Show rough estimate of the ranges possible 
//		g.setColor(Color.green);
//		g.drawRect(WIDTH/2 - 100, HEIGHT/2 - 100, 200, 200);
//		g.setColor(Color.yellow);
//		g.drawRect(WIDTH/2 - 300, HEIGHT/2 - 300, 600, 600);
//		g.setColor(Color.red);
//		g.drawRect(WIDTH/2 - 500, HEIGHT/2 - 500, 1000, 1000);
		
		g.setFont(t.getFont());
		g.setColor(t.getFontColor()); 
		int vibrateX = (r.nextInt(1+t.getVibrate())-t.getVibrate()/2);
		int vibrateY = (r.nextInt(1+t.getVibrate())-t.getVibrate()/2);
		int sizeCompensation = (g.getFont().getSize()/4) *t.getThoughtString().length();
		int x = WIDTH/2 + t.getWanderX() + vibrateX - sizeCompensation;
		int y = HEIGHT/2 + t.getWanderY()  + vibrateY;
		g.drawString(t.getThoughtString(),x,y);
		bs.show();

		
		g.dispose();
		bs.show();
		
	}
	
	public void run() {
		Thought i = new IdleThought();
		Thought w = new WatchedThought();
		Thought t = new TouchedThought();
		Thought c = new ConfusedThought();
		boolean confused = false;
		watched = false;
		
		Thought temp = i;
		for(int j=0; j<1000; j++){
			
			if(keyboardTouched) {
				temp = t;
				confused = true;
			}
			else if(confused) temp = c;
			else if(watched) temp = w;
			else temp = i;
			
			
			if(j%temp.getDelay()==0) {
				temp.generateThoughtString();
				temp.generateWander();
				sound.speak(temp);
			}
			if(j%temp.getDelay() < temp.getPause()) render(temp);
			
			else render(blank);
			
			//if(temp.getVibrate()!= 0) sound.speak(temp);
			
			try {
				AiThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
		}

		run();
	}

}