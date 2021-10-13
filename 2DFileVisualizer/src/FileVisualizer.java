import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class FileVisualizer extends Canvas implements Runnable{
	
	public static final int WIDTH = 1000, HEIGHT = 800;
	private Random r = new Random();
	
	public FileVisual fileVisuals;
	
	Thread fileVisualizerThread;
	boolean running = true;
	
	public static File rootFile;
	public FolderVisual rootVisual;
	public ArrayList<FileVisual> files = new ArrayList<FileVisual>();
	public ArrayList<FolderVisual> folders = new ArrayList<FolderVisual>();
	
	public static Player player;
	
	public static void main(String[] args){
		new FileVisualizer();
	}
	
	public FileVisualizer(){
		this.addKeyListener(new KeyInput());
		new Window(WIDTH, HEIGHT, "File Visualizer", this);
		
		File[] roots = File.listRoots();
		rootFile = roots[0];
		rootVisual = new FolderVisual(rootFile);
		
		fileVisualizerThread = new Thread(this);
		fileVisualizerThread.start();
	}

	@Override
	public void run() {
		player = new Player(500, 500, rootVisual);
		while(running){
			
			player.currentRoom.run();
			player.run();
			
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
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		player.currentRoom.render(g);
		player.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
