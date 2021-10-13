import java.awt.Graphics;

public class Astronaut extends SpritedObject{
	
	float xVel;
	float yVel;
	
	public float xSpeed = (float).2;
	public float ySpeed = (float).08;
	
	private float waver;
	private boolean waverAscending;
	
	private boolean spriteFlipped;
	
	public Astronaut(int x, int y){
		super(x, y, "Astro1.png", 16);
		this.x = x;
		this.y = y;
	}
	
	public void tryToInteract(){
		//WallModule isNear = null;
		for(WallModule wm : SpaceTrails.wallModules){
			if(wm != null)
				if (wm.inHitbox(x+drawWidth/2, y+drawHeight/2) ||
						wm.inHitbox(x+drawWidth/2, y)) wm.interact();
		}
		
		//if(isNear != null) isNear.interact();
	}
	
	public void move(){
		depreciateVelocity();
		
		x+=xVel;
		y+=yVel;
	}
	
	private void depreciateVelocity() {
		double resistance = 1.03;
		if(xVel > 0) xVel/=resistance;
		else if(xVel < 0) xVel/=resistance;
		if(yVel < 0) yVel/=resistance;
		else if(yVel > 0) yVel/=resistance;
		if(Math.abs(xVel) < .01) xVel = 0;
		if(Math.abs(yVel) < .01 ) yVel = 0;
	}


	public void drawBody(Graphics g) {
		
		if(waver < 0) waverAscending = true;
		else if(waver > 5) waverAscending = false;
		
		if(waverAscending) waver += .02;
		else waver -= .02;
		
		int intX = (int)x;
		int intY = (int)(y + waver);
		int tempWidth = drawWidth;
		
		float xMovement = Math.abs(xVel);
		
		if(xVel<0) spriteFlipped = true;
		else if(xVel>0) spriteFlipped = false;
		
		if(spriteFlipped) {
			intX += tempWidth;
			tempWidth *= -1;
		}
		
		if(xMovement > xSpeed*20) g.drawImage(sprites[0], intX, intY, tempWidth, drawHeight, null);
		else if(xMovement > xSpeed*10) g.drawImage(sprites[2], intX, intY, tempWidth, drawHeight, null);
		else if(xMovement > xSpeed*5) g.drawImage(sprites[1], intX, intY, tempWidth, drawHeight, null);
		else g.drawImage(sprites[3], intX, intY, tempWidth, drawHeight, null);
	}
	
}
