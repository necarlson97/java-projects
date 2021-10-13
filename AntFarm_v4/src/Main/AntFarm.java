package Main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.Timer;

import Boxes.Ant;
import Boxes.Box;
import Boxes.Destination;
import Boxes.DirtBox;
import Boxes.EmptyBox;
import Boxes.Food;
import Boxes.WorkerAnt;

public class AntFarm extends Canvas implements Runnable{
	
	public static int DELAY = 1000 / 24;
	
	private boolean isFullScreen = false;
	static boolean running = true;
	Thread antFarmThread;
	
	static boolean debug = false;
	
	static int windowWidth;
	static int windowHeight;
	
	public static int boxSize;
	
	public static int boxRows;
	public static int boxCols = 100;
	
	public static Box[][] bgBoxes;
	ArrayList<Ant> ants = new ArrayList<Ant>(); 
	
	public static Destination[] destinations = new Destination[boxCols/10];
	
	static int count = 0;
	
	public static void main(String[] args){
		new AntFarm();
	}
	
	public AntFarm(){
		this.addKeyListener(new KeyInput());
		
		createWindow();
		createBGBoxes();
		
		
		antFarmThread = new Thread(this);
		antFarmThread.start();
	}

	private void createBGBoxes() {
		bgBoxes = new Box[boxRows][boxCols];
		for(int i=0; i<boxRows; i++){
			for(int j=0; j<boxCols; j++){
				if( i < (boxRows*.1)) bgBoxes[i][j] = new EmptyBox(i, j);
				else bgBoxes[i][j] = new DirtBox(i, j);
			}
		}
		
	}

	public void createWindow(){
		if(isFullScreen) {
			windowWidth = 1440;
			windowHeight = 900;
			new FullScreen(windowWidth, windowHeight, "", this); 
		}
		else {
			windowWidth = 1000;
			windowHeight = 800;
			new Windowed(windowWidth, windowHeight, "Ant Farm", this); 
		}

		boxSize = windowWidth / boxCols; 
		boxRows = windowHeight / boxSize;
	}

	@Override
	public void run() {
		
		destinations[0] = new Food(60, 40);
		
		ants.add(new WorkerAnt(1, 50));
		
		while(running){
			
			for(Ant a : ants)
				a.run();
			
			render();
			count++;
			waitForNextFrame();
		}
		try {
			antFarmThread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		run();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		renderBackground(g);
		for(Ant a : ants){
			a.render(g);
			if(debug) a.renderDebug(g);
		}
			
		
		g.dispose();
		bs.show();
		
	}

	private void renderBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, windowWidth, windowHeight);
		
		for(Box[] boxRow: bgBoxes){
			for(Box b : boxRow){
				if(b!=null) {
					b.render(g);
					if(debug) b.renderDebug(g);
				}	
			}
		}
		
		for(Destination d : destinations){
			if(d!=null) d.render(g);
		}
		
	}
	

	private void waitForNextFrame() {
		long tillNextFrame = DELAY - System.currentTimeMillis()%DELAY;
		if(tillNextFrame<0) return;
		try {
			antFarmThread.sleep(tillNextFrame);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

}
