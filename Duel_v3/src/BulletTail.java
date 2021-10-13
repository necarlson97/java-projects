import java.awt.Color;
import java.awt.Graphics;

public class BulletTail extends Particle{

	private int width;
	private int height;
	
	private Duelist duelist;
	private Color trailColor;
	
	private boolean drawingGun;
	
	private int xAdd;
	
	public BulletTail(Duelist duelist) {
		super(duelist.gunBarrelX, duelist.gunBarrelY, 0, 0);
		this.duelist = duelist;
		this.trailColor =  Color.white;
		
		width = 970;
		height = 10;
		xAdd = 200;
		
		if(duelist.drawingGun){
			drawingGun = true;
			x -= 20;
			y += 40;
			xAdd = 0;
		}
		if(duelist == Duel.enemy){
			x -= width;
			xAdd *= -1;
		}
		
	}
	
	
	public void run(){
		height--;
		x += xAdd;
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		if(height > 5){
			g.setColor(trailColor);
			if(!drawingGun) g.fillRect(intX, intY, width, height);
			else g.fillRect(intX, intY, height, width);
		}
	}

}
