

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class WorldObject {

	public int x, y;
	public int w, h;
	
	public  WorldObject(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		//System.out.println("World Object x: "+x+" y: "+y);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);

	
	public Rectangle getBounds(){
		return new Rectangle((int) x,(int) y,w,h);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.lightGray);
		g.fillRect((int) x, (int) y, 8, 8);
	}
	
	
}