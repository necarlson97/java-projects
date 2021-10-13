import java.awt.Graphics;

public class Player extends Astronaut{

	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	public Player(int x, int y) {
		super(x, y);
	}
	
	public void run(){
		addSpeed();
		checkInsideModule();
		super.move();
	}
	
	private void checkInsideModule(){
		boolean topLeft = false;
		boolean topRight = false;
		boolean bottomLeft = false;
		boolean bottomRight = false;
		
		for(LabModule m : SpaceTrails.shipModules){
			if(m != null && m.inHitbox(x, y)) topLeft = true;
			if(m != null && m.inHitbox(x+drawWidth, y)) topRight = true;
			if(m != null && m.inHitbox(x, y+drawHeight)) bottomLeft = true;
			if(m != null && m.inHitbox(x+drawWidth, y+drawHeight)) bottomRight = true;
		}
		
		if(!topLeft){ y+=2; x+=2; }
		if(!topRight){ y+=2; x-=2; }
		if(!bottomLeft){ y-=2; x+=2; }
		if(!bottomRight){ y-=2; x-=2; }
	
		if(!topLeft || !topRight || !bottomLeft || !bottomRight){
			xVel=0; yVel=0;
		}

	}
	
	private void addSpeed(){
		if(up) yVel -= ySpeed;
		if(down) yVel += ySpeed;
		if(left) xVel -= xSpeed;
		if(right) xVel += xSpeed;
	}
	
	public void render(Graphics g){
		super.drawBody(g);
	}

}
