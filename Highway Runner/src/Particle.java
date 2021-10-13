import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class Particle {
	
	Random r = new Random();

	float x;
	float y;
	
	float yVel;
	float xVel;
	
	Color color = Color.white;
	
	int lifeSpan;
	int life;
	
	int size;
	
	public Particle(int x, int y, int lifeSpan, int size) {
		this.x = x;
		this.y = y;
		
		this.lifeSpan = lifeSpan;
		life = lifeSpan;
		
		this.size = size;
	}
	
	public void run() {
		life--;
		x += xVel;
		y += yVel;
		depreciateVelocity();
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
	
	public abstract void render(Graphics g);

}
