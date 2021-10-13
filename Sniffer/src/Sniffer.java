import java.awt.Canvas;

public class Sniffer extends Canvas implements Runnable{
	
	static { System.setProperty("java.awt.headless", "true"); }
	
	boolean running = true;
	Thread snifferThread;
	
	static String typed = "";
	
	public static void main(String[] args){
		new Sniffer();
	}
	
	public Sniffer(){
		this.addKeyListener(new KeyInput());
		
		snifferThread = new Thread(this);
		snifferThread.start();
	}

	@Override
	public void run() {
		while(running){
			System.out.println("Typed: "+typed);
			
			try {
				snifferThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

}
