import java.awt.Graphics;

public abstract class BackgroundObject {
	
	public float x;
	public float y;
	public float xVel;
	public float yVel;
	
	float backgroundSpeed;
	
	public BackgroundObject(int x, int y, float xVel, float yVel, float backgroundSpeed){
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
		this.backgroundSpeed = backgroundSpeed;
	}
	
	public BackgroundObject(int x, int y, float xVel, float yVel){
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
		this.backgroundSpeed = -1 - 40*( (float)(y-Duel.horizonY) / (Duel.HEIGHT-Duel.horizonY) );
	}
	
	public void move(){
		if(xVel>0) xVel -= .01;
		else if(xVel<0) xVel += .01;
		
		if(Math.abs(xVel)<.01) xVel = 0;
		if(Math.abs(yVel)<.01) yVel = 0;
		
		x+=xVel+backgroundSpeed;
		y+=yVel;
	}

	public void run() {
		
	}

	public void render(Graphics g) {
		
	}
	
	
	
}
