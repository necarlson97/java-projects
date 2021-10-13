import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public static float x;
	public static float y;
	
	static int width = 10;
	static int height = 20;
	
	float xVel;
	
	float speed = (float) .4;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public static int moveY;
	
	public boolean alive = true;
	
	Color fillColor = Color.WHITE;
	Color lineColor = Color.blue;
	
	int stairSpeed = 2;
	
	int maxParticles = 10;
	Particle[] particles = new Particle[maxParticles];
	
	float smokeInhail = 0;
	float charred = 0;
	
	Room room;
	boolean falling = false;
	
	public Player(){
		this.x = Game.windowWidth/2;
		this.y = BurningHouse.groundY;
		moveY = (int)y;
	}
	
	public void run(){
		
		depreciateVelocity();
		
		if(falling) {
			y+=10;
			if(y >= BurningHouse.groundY) {
				y = BurningHouse.groundY;
				falling = false;
			}
			x+=xVel;
			return;
		}
		
		if(Game.count%10 == 0){
			room = BurningHouse.playerIn();
			if(room != null) room.lineColor = lineColor;
			if(room == null && y < BurningHouse.groundY && moveY == y) {
				falling = true;
				moveY = BurningHouse.groundY;
				if(xVel > 0) xVel ++;
				else xVel --;
			}
		}
		
		if(charred > 1 || smokeInhail > 1) alive = false;
		
		if(alive) {
			if(moveY > y) {
				if(Game.count%stairSpeed/2 == 0) y+=stairSpeed;
			}
			else if(moveY < y) {
				if(Game.count%stairSpeed/2 == 0) y-=stairSpeed;
			}
			else addVelocity(speed);
			
			
		}
	
		cleanParticles();
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].run();
		
		
	}

	private void addVelocity(float add){
		
		if(left && xVel > -1.5-add) xVel-= add;
		else if(right && xVel < 1.5+add) xVel+= add;
		
		x+=xVel;
	}
	
	private void depreciateVelocity() {
		double resistance = 1.1;
		if(xVel > 0) xVel/=resistance;
		else if(xVel < 0) xVel/=resistance;

		if(Math.abs(xVel) < .01) xVel = 0;
	}
	
	private void cleanParticles() {
		for(int i=0; i<particles.length; i++) {
			if(particles[i] != null && particles[i].life < 0) particles[i] = null;
		}
	}
	
	public void kill() {
		alive = false;
	}
	
	public void render(Graphics g){
		
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].render(g);
		
		
		int intX = (int)x;
		int intY = (int)y-height;
		
		if(moveY != y && !falling) {
			g.setColor(lineColor);
			g.drawRect(intX-width/2, intY, width, height);
			return;
		}
		
		g.setColor(fillColor);
		g.fillRect(intX-width/2, intY, width, height);
		g.setColor(lineColor);
		g.drawRect(intX-width/2, intY, width, height);
		
		g.setColor(Color.BLACK);
		if(alive) {
			if(down) g.fillRect(intX-2, intY+4, 4, 4);
			else if(right) g.fillRect(intX+(width/2)-4, intY+2, 4, 4);
			else if(left) g.fillRect(intX-width/2, intY+2, 4, 4);
			else if(up) g.fillRect(intX-2, intY, 4, 4);
			else g.fillRect(intX-2, intY+2, 4, 4);
		}
		
		
		
	}

}
