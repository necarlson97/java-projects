import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class SpaceTrails extends Canvas implements Runnable {
	
	private boolean isFullScreen = false;
	private boolean running = true;
	Thread spaceTrailsThread;
	
	private int windowWidth;
	private int windowHeight;
	
	static LabModule[] shipModules = new LabModule[5]; 
	static Player player;
	
	static WallModule[] wallModules = new WallModule[52]; 
	
	public static void main(String[] args){
		new SpaceTrails();
	}
	
	public SpaceTrails(){
		this.addKeyListener(new KeyInput());
		createWindow();
		
		spaceTrailsThread = new Thread(this);
		spaceTrailsThread.start();
	}
	
	public void createWindow(){
		if(isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "Space Trails", this); 
		}
		else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Space Trails", this); 
		}
	}

	@Override
	public void run() {
		player = new Player(100, 100);
		shipModules[0] = new LabModule(0, 0);
		wallModules[0] = new WallMushrooms(shipModules[0], 0, 0);
		wallModules[1] = new WallMushrooms(shipModules[0], 0, 1);
		
		int count = 0;
		while(running){
			player.run();
			for(LabModule sm : shipModules){
				if(sm != null) sm.run();
			}
			for(WallModule wm : wallModules){
				if(wm != null) wm.run(count);
			}
			render();
			count++;
			try {
				spaceTrailsThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		for(LabModule sm : shipModules){
			if(sm != null) sm.render(g);
		}
		for(WallModule wm : wallModules){
			if(wm != null) wm.render(g);
		}
		player.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
