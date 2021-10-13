import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class MuzzleFlash extends Particle{
	
	private int width;
	private int height;
	
	private boolean dissapated;
	
	private Color flashColor;
	
	private Random r = new Random();
	
	public MuzzleFlash(int x, int y, float xVel, float yVel, Color flashColor){
		super(x, y, xVel, yVel);
		
		this.flashColor =  flashColor;
		
		width = (r.nextInt(3)+2)*3;
		height = (r.nextInt(1)+1)*3;
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
		
		if(!dissapated){
			g.setColor(flashColor);
			g.fillRect((int)x-width/2, (int)y-height/2, width, height);
		}
		
	}

}
