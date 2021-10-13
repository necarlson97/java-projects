import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Birds extends Canvas implements Runnable {
		
	private boolean isFullScreen = false;
	static boolean running = true;
	Thread birdsThread;
	
	static int windowWidth;
	static int windowHeight;
	
	static int horizonY;
	static int midgroundY;
	
	public static Bird player;
	public static Tree[] trees = new Tree[50];
	public static StageTile[] stageTiles = new StageTile[3];
	public static Sun sun;
	
	public static void main(String[] args){
		new Birds();
	}
	
	public Birds(){
		this.addKeyListener(new KeyInput());
		createWindow();
		
		horizonY = windowHeight - 180;
		midgroundY = windowHeight - 300;
		
		birdsThread = new Thread(this);
		birdsThread.start();
	}
	
	public void createWindow(){
		if(isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "Birds", this); 
		}
		else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Birds", this); 
		}
	}

	@Override
	public void run() {
		player = new Pidgeon(100, 100);
		stageTiles[0] = new TreeBackground();
		stageTiles[1] = new TreeMidground();
		stageTiles[2] = new SandForeground();
		sun = new Sun();
		
		int count = 0;
		while(running){
			
			player.run();
		
			sun.run(count);
			
			runBackground();
			
			render();
			count++;
			try {
				birdsThread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		running = true;
		run();
	}
	
	private void runBackground() {
		for(int i=0; i<trees.length; i++){
			if(trees[i]==null || trees[i].x < -trees[i].drawWidth) 
				trees[i] = new Tree(i);
			else trees[i].run();
		}
		
		for(StageTile s : stageTiles){
			if(s!=null) s.run();
		}
		
	}

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		
		player.render(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderBackground(Graphics g) {
		
		sun.render(g);
		
		for(StageTile s : stageTiles){
			if(s!=null) s.render(g);
		}
		
		for(Tree t : trees){
			if(t!=null) t.render(g);
		}
		
	}

	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
