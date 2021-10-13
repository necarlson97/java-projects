import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class BottledShip extends Canvas implements Runnable{
	public static final int WIDTH = 1000, HEIGHT = 800;
	private Random r = new Random();
	
	static Handler handler;
	
	Thread bottledShipThread;
	boolean running = true;
	
	public static void main(String[] args){
		new BottledShip();
	}
	
	public BottledShip(){
		new Window(WIDTH, HEIGHT, "Bottled Ship", this);

		handler = new Handler();
		
		bottledShipThread = new Thread(this);
		bottledShipThread.start();
	}

	@Override
	public void run() {
		while(running){
			handler.run();
			
			render();
		}
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}

}
