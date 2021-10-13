import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class GunSmoke extends Particle{
	
	private int width;
	private int height;
	
	private boolean dissapated;
	
	private Color smokeColor;
	
	private Random r = new Random();
	
	public GunSmoke(int x, int y, float xVel, float yVel, Color smokeColor){
		super(x, y, xVel, yVel);
		
		this.smokeColor =  smokeColor;
		
		
		 width = (r.nextInt(1)+1)*5;
		 height = (r.nextInt(3)+2)*5;
	}
	
	public void run(){
		if(Math.abs(xVel)+Math.abs(yVel) < .04) {
			dissapated = true;
			
			xVel = 0;
			yVel = 0;
		}
		else super.move(false);
	}
	
	public void render(Graphics g){
		
		if(!dissapated){
			g.setColor(smokeColor);
			g.fillRect((int)x-width/2, (int)y-height/2, width, height);
		}
		
	}

}
