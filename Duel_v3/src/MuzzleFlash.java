import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class MuzzleFlash extends Particle{
	
	private int width;
	private int height;
	
	private boolean dissapated;
	
	private Duelist duelist;
	private Color flashColor;
	
	private Random r = new Random();
	
	public MuzzleFlash(float xVel, float yVel, Duelist duelist, Color flashColor){
		super(duelist.gunBarrelX, duelist.gunBarrelY, xVel, yVel);
		this.duelist = duelist;
		this.flashColor =  flashColor;
		
		width = (r.nextInt(3)+2)*3;
		height = (r.nextInt(1)+1)*3;
		
		if(duelist == Duel.enemy) this.xVel *= -1;
		if(duelist.drawingGun) {
			this.xVel = yVel;
			this.yVel = xVel;
			int tempWidth = width;
			this.width = height;
			this.height = tempWidth;
			x -= 20;
			y += 40;
		}
		
	}
	
	public void run(){
		if(Math.abs(xVel)+Math.abs(yVel) < 2.6) {
			dissapated = true;
			
			xVel = 0;
			yVel = 0;
		}
		else super.move(false);
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		if(!dissapated){
			g.setColor(flashColor);
			g.fillRect(intX, intY, width, height);
		}
		
	}

}
