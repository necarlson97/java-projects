import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class GunSmoke extends Particle{
	
	private int width;
	private int height;
	
	private boolean dissapated;
	
	private Duelist duelist;
	private Color smokeColor;
	
	private Random r = new Random();
	
	public GunSmoke(float xVel, float yVel, Duelist duelist, Color smokeColor){
		super(duelist.gunBarrelX, duelist.gunBarrelY, xVel, yVel);
		this.duelist = duelist;
		this.smokeColor =  smokeColor;
	
		width = (r.nextInt(1)+1)*5;
		height = (r.nextInt(3)+2)*5;
		
		if(duelist.drawingGun) {
			x -= 20;
			y += 40;
		}
	}
	public GunSmoke(float xVel, float yVel, int x, int y, Color smokeColor){
		super(x, y, xVel, yVel);
		this.smokeColor =  smokeColor;
	
		width = (r.nextInt(8)+4)*5;
		height = (r.nextInt(10)+4)*5;
	}
	
	public void run(){
		xVel -= .06;
		if(Math.abs(xVel)+Math.abs(yVel) < .04) {
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
			g.setColor(smokeColor);
			g.fillRect(intX, intY-height, width, height);
		}
		
	}

}
