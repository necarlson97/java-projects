import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Car {
	
	int width = 15;
	int length = 45;
	
	float x;
	float y;
	
	float xVel;
	float yVel;
	
	boolean horizontal;
	
	Car next = null;
	
	Random r = new Random();
	
	float speed;
	
	Color color;
	int index;
	
	Road road;
	
	static int minSpeed = 10;
	
	int maxParticles = 10;
	Particle[] particles = new Particle[maxParticles];
	
	boolean killedPlayer = false;
	int killX;
	int killY;
	
	public Car(boolean horiz, int startingLocation, int i, Road road) {
		this.road = road;
		this.index = i;
		horizontal = horiz;
		int roadOffset = (road.width - width) /2;
		
		int locationPart;
		if(horizontal) {
			locationPart = Game.windowWidth/Road.maxCars;
			x = r.nextInt(locationPart)+(locationPart * index);
			y = startingLocation+roadOffset;
		}
		else {
			locationPart = Game.windowHeight/Road.maxCars;
			x = startingLocation+roadOffset;
			x = r.nextInt(locationPart)+(locationPart * index);
		}
		
		float colorPart = (float)1/Road.maxCars;
		color = new Color(r.nextInt());
		speed = newSpeed();
	}
	
	private int newSpeed(){
		return r.nextInt(3)+minSpeed;
	}
	
	public void run() {
		
		int count = Game.count;
		
		if(!killedPlayer) {
			if(next == null) move();
			else if(next.x-(2*length) > x) move(); 
			else speed = next.speed;
		}
		
		
		if(count%2 == 0)addDust();
		cleanParticles();
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].run();
		
		
		if(y > Game.windowHeight || x > Game.windowWidth) resetCar();
	}
	
	private void move(){
		if(horizontal) x+=speed;
		else y+=speed;
	}
	
	private void cleanParticles() {
		for(int i=0; i<particles.length; i++) {
			if(particles[i] != null && particles[i].life < 0) particles[i] = null;
		}
	}
	
	private void addDust() {
	
		for(int i=0; i<particles.length; i++) {
			if( !(particles[i] instanceof Dust) ) {
				particles[i] = new Dust((int)x, (int)y+(width/2));
				return;
			}
		}
		
	}
	
	private void resetCar() {
		if(horizontal) x = -length;
		else y = -length;
		speed = newSpeed();
		killedPlayer = false;
		Car currCar = road.headCar;
		while(currCar.next != this) {
			currCar = currCar.next;
		}
		currCar.next = null;
		next = road.headCar;
		road.headCar = this;
	}
	
	public boolean checkCollisions(float playerX, float playerY) {
		
		if(horizontal && x < playerX && x+length > playerX && y < playerY && y+width > playerY) return true;
		if(!horizontal && x < playerX && x+width > playerX && y < playerY && y+length > playerY) return true;
		
		return false;
	}

	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].render(g);
		
		g.setColor(color);
		if(horizontal) {
			g.fillRect(intX, intY, length, width);
			g.setColor(Color.black);
			g.drawRect(intX, intY, length, width);
		}
		else {
			g.fillRect(intX, intY, width, length);
			g.setColor(Color.black);
			g.drawRect(intX, intY, width, length);
		}
		
		if(killedPlayer) {
			float red = 1 - ((float) (intX - killX) / Game.windowWidth);
			g.setColor(new Color(red,(float).2, (float).2));
			g.drawLine(killX, killY, intX, intY);
			g.drawLine(killX, killY+width, intX, intY+width);
		}
		
		
		if(Game.debug) {
			g.setColor(color.white);
			g.drawString(index+"", intX, intY+width);
		}
		
	}

	public void killedPlayer() {
		killedPlayer = true;
		killX = (int)x;
		killY = (int)y;
		
	}

}
