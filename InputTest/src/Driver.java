import java.awt.Component;

public class Driver extends Component implements Runnable {

	Thread thread;
	
	Driver() {
		this.addKeyListener(new KeyInput(this));
		thread = new Thread(this);
		
		thread.start();
	}
	
	public static void main(String[] args) {
		new Driver();
	}

	@Override
	public void run() {
		System.out.println("Running...");
		while(true) {
			
		}
	}
	
	void print(String s) {
		System.out.println(s);
	}

}
