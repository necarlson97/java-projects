import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public float x;
	public float y;
	
	int width = 10;
	int height = 20;
	
	float xVel;
	float yVel;
	
	
	float speed = (float) .4;
	float boostSpeed = (float) .6;
	public boolean boost = false;
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	public boolean alive = true;
	
	int maxParticles = 10;
	Particle[] particles = new Particle[maxParticles];
	
	public Player(){
		this.x = Game.windowWidth/2;
		this.y = 10;
	}
	
	public void run(){
		
		if(alive) {
			if(boost) {
				addVelocity(speed+boostSpeed);
				addDust();
			}
			else addVelocity(speed);
		}
		depreciateVelocity();
	
		cleanParticles();
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].run();
		
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
	
	private void cleanParticles() {
		for(int i=0; i<particles.length; i++) {
			if(particles[i] != null && particles[i].life < 0) particles[i] = null;
		}
	}
	
	private void addDust() {
	
		for(int i=0; i<particles.length; i++) {
			if( !(particles[i] instanceof Dust) ) {
				particles[i] = new Dust((int)x, (int)y+(height/2));
				return;
			}
		}
		
	}
	
	public void kill() {
		alive = false;
		for(int i=0; i<particles.length; i++)
			particles[i] = new Blood((int)x, (int)y);
	}
	
	public void render(Graphics g){
		
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].render(g);
		
		
		int intX = (int)x;
		int intY = (int)y;
		
		if(alive) g.setColor(Color.WHITE);
		else g.setColor(new Color((float).4, 0 ,0));
		g.fillRect(intX-width/2, intY, width, height);
		
		g.setColor(Color.black);
		if(boost) g.setColor(Color.orange);
		
		if(alive) {
			if(down) g.fillRect(intX-2, intY+4, 5, 5);
			else if(right) g.fillRect(intX+2, intY+2, 5, 5);
			else if(left) g.fillRect(intX-7, intY+2, 5, 5);
			else if(!up)g.fillRect(intX-2, intY+2, 5, 5);
			
			if(boost) {
				g.setColor(Color.BLACK);
				if(down) g.drawRect(intX-2, intY+4, 5, 5);
				else if(right) g.drawRect(intX+2, intY+2, 5, 5);
				else if(left) g.drawRect(intX-7, intY+2, 5, 5);
				else if(!up)g.drawRect(intX-2, intY+2, 5, 5);
			}
		}
		
		
		
	}

}
