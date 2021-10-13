import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Duelist implements Runnable {
	
	Random r = new Random();

	Thread duelistThread;
	
	public int x;
	public int y;
	
	public int width = 20;
	public int height = 100;
	
	public boolean alive = true;
	public boolean gun;
	public Color duelistColor;
	
	public int killCount;
	
	public Duelist(int x, int y){
		this.x = x;
		this.y = y;
		
		duelistThread = new Thread(this);
		duelistThread.start();
	}
	
	public void run(){
		
	}
	
	public void render(Graphics g, Color duelistColor){
		if(!alive){
			g.setColor(duelistColor);
			g.fillRect(x, y+60, width, height-60);
			g.setColor(new Color(150, 0, 0)); // A deep, blood red
			g.fillRect(x, y+40, width, 30);
			g.setColor(Color.red);
			g.fillRect(x, y+30, width, 10);
			
		}
		else {
			g.setColor(duelistColor);
			g.fillRect(x, y, width, height);
		}
		
	}

}
