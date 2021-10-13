import java.awt.Graphics;

public class BottledObject {
	
	Handler handler;
	
	float xVel;
	float yVel;
	
	float x;
	float y;
	
	int width;
	int height;
	
	int mass;
	boolean falling = true;
	
	float restitution;
	
	public BottledObject(Handler handler, int x, int y, int width, int height){
		setKnownVars(handler, x, y, width, height);
	}
	
	public BottledObject(Handler handler, int x, int y, int width, int height, float xVel, float yVel){
		this.xVel = xVel;
		this.yVel = yVel;
		setKnownVars(handler, x, y, width, height);
	}
	
	public void setKnownVars(Handler handler, int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		
		mass = width*height;
		
		restitution = (float)-.5;
	}
	
	public void run() {
	
	}
	
	public void render(Graphics g) {
		
	}
	
	public void move(){
		// Just regular air resistance
		if (xVel > 0) xVel -= .1; 
		else if (Math.abs(xVel) < .1) xVel = 0;
		else xVel += .1;
		if (yVel > 0) yVel -= .1;
		else if (Math.abs(yVel) < .1) yVel = 0;
		else yVel += .1;
		
		if(falling) yVel += .5; // gravity
		
		checkObjectsHitting();
		
		// And the actual moving
		x+=xVel;
		y+=yVel;
	}
	
	public void checkObjectsHitting(){
		for(int i=0; i<handler.allObjects.size(); i++){
			BottledObject obj = handler.allObjects.get(i);
			if(!obj.equals(this) && this.collidesWith(obj)) {
				hit(obj);
			}
		}
			
	}
	
	public void hit(BottledObject other){
		if( Math.signum(this.xVel) + Math.signum(other.xVel) == 0 &&
				Math.signum(this.yVel) + Math.signum(other.yVel) == 0) return;
		float thisMassRatio = (float) this.mass / other.mass;
		float otherMassRatio = (float) other.mass / this.mass;
		
		this.xVel = this.xVel * thisMassRatio * restitution;
		this.yVel = this.yVel * thisMassRatio * restitution;
		
//		other.xVel = other.xVel / otherMassRatio * other.restitution;
//		other.yVel = other.yVel / otherMassRatio * other.restitution;
	}
	
	public boolean collidesWith(BottledObject other) {
		boolean xCollides = ((this.x >= other.x && this.x <= other.x+other.width)
						|| (other.x >= this.x && other.x <= this.x+this.width));
		boolean yCollides = ((this.y >= other.y && this.y <= other.y+other.height)
						|| (other.y > this.y && other.y < this.y+this.height));
		return xCollides && yCollides;
	}
	
	

}
