import java.awt.Graphics;

public class Particle {
	
	public float x;
	public float y;
	public float xVel;
	public float yVel;
	
	public Particle(int x, int y, float xVel, float yVel){
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public void move(boolean falling){
	
		if(xVel>0) xVel -= .01;
		else if(xVel<0) xVel += .01;
		
		if(falling) yVel += .03;
		else {
			if(yVel>0) yVel -= .01;
			else if(yVel<0) yVel += .01;
		}
		
		x+=xVel;
		y+=yVel;
	}

	public void run() {
		
	}

	public void render(Graphics g) {
		
	}

}
