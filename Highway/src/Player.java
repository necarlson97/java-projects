import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	
	float x;
	float y;
	
	float xVel;
	float yVel;
	
	int width = 10;
	int height = 20;
	
	float speed = (float) 1;
	
	boolean alive = true;
	
	Color color = Color.orange;
	
	public Player(int startingX, int startingY) {
		x = startingX;
		y = startingY;
	}
	
	public void run() {
		if(alive) {
			addVelocity(speed);
		}
		depreciateVelocity();
		
		x+=xVel;
		y+=yVel;
	}
	
	private void addVelocity(float add){
		if(up && yVel > -1.5-add) yVel-= add;
		else if(down && yVel < 1.5+add) yVel+= add;
		if(left && xVel > -1.5-add) xVel-= add;
		else if(right && xVel < 1.5+add) xVel+= add;
	}
	
	private void depreciateVelocity() {
		double resistance = 1.1;
		if(xVel > 0) xVel/=resistance;
		else if(xVel < 0) xVel/=resistance;
		if(yVel < 0) yVel/=resistance;
		else if(yVel > 0) yVel/=resistance;
		if(Math.abs(xVel) < .01) xVel = 0;
		if(Math.abs(yVel) < .01 ) yVel = 0;
	}
	
	public void render(Graphics g) {
		
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX, intY, width, height);
	}

}
