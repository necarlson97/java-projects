import java.awt.Color;
import java.awt.Graphics;

public abstract class Person extends GameObject{
	
	int maxParticles = 0;
	Particle[] particles = new Particle[maxParticles];
	
	float smokeInhail = 0;
	float charred = 0;
	
	float inhailSpeed = (float) .001;
	float charSpeed = (float) .002;
	
	float walkSpeed = (float) .4;
	float crawlSpeed = (float) .2;
	
	int groundY;
	int climbY;
	int stairSpeed = 2;
	int crawlStairSpeed = 1;
	
	int fallDist = 0;
	
	boolean alive = true;
	boolean crippled = false;
	
	final int WALKING = 0;
	final int FALLING = 1;
	final int CLIMBING = 2;
	final int CRAWLING = 3;
	int state = WALKING;
	
	boolean pointLeft = false; 
	
	Color fillColor = Color.WHITE;
	Color lineColor = Color.BLACK;
	Color eyeColor;
	
	Module module;
	
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	
	int index;
	
	public Person(int x, int y, Color color, int i) {
		super(x, y, 10, 20);
		eyeColor = color;
		groundY = BurningHouse.groundY;
		this.index = i;
	}
	
	public void run() {
		depreciateVelocity();
		
		if(state == FALLING) {
			fall();
			return;
		}
		
		if(alive) {
			if(state == WALKING && (down || crippled)) state = CRAWLING;
			else if(!down && !crippled && state == CRAWLING) state = WALKING;
			
			if((state == WALKING || state == CRAWLING)){
				module = personIn();
				if(module == null && y < groundY && !held){
					state = FALLING;
					fallDist = (int) (groundY - y);
					if(pointLeft) xVel --;
					else xVel ++;
				}
			}
			
			if(state == CLIMBING) climb();
			else if(state == CRAWLING) crawl();
			else if(state == WALKING) walk();
			
			
			if(module != null) {
				if(state == CRAWLING) {
					if(module.room.smoke > .7) smokeInhail += inhailSpeed;
				}
				else if(module.room.smoke > .3) smokeInhail += inhailSpeed;
				
				if(module.onFire > .2) charred += charSpeed;
				float gray = 1-smokeInhail;
				fillColor = new Color(gray, gray, gray);
				lineColor = new Color(charred, charred, charred);
			}
			
			if(charred > .95 || smokeInhail > .99) kill();
			else if(smokeInhail > .8) cripple();
		}
		else state = CRAWLING;
	
		cleanParticles();
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].run();
	}
	
	public Door nearDoor() {
		if(module != null) {
			for(Door d : module.doors) {
				if(d != null && d.x-width < x && d.x+width > x
						&& d.y - height/2 < y && d.y + height/2 > y) {
					return d;
				}
			}
		}
		
		for(Floor f : BurningHouse.floors) {
			for(Module m : f.modules) {
				for(Door d : m.doors) {
					if(d != null && d.x-width < x && d.x+width > x
							&& d.y - height/2 < y && d.y + height/2 > y) {
						return d;
					}
				}
			}
		}
		return null;
	}
	
	public Window nearWindow() {
		if(module != null) {
			Window w = module.room.window;
			if(w != null && w.x-width < x && w.x+width > x
					&& w.y - height/2 < y && w.y + height/2 > y) {
				return w;
			}
		}
		return null;
	}
	
	private boolean checkBounds() {
		Door nearDoor = nearDoor();
		if(nearDoor != null && !nearDoor.open) {
			if(nearDoor.x < x) x=nearDoor.x+width;
			else x = nearDoor.x-width;
			xVel = 0;
			return true;
		}
		Window nearWindow = nearWindow();
		if(nearWindow != null && !nearWindow.broken) {
			if(nearWindow.x < x) x=nearWindow.x+width;
			else x = nearWindow.x-width;
			xVel = 0;
			return true;
		}
		return false;
		
	}

	private void climb() {
		if(crippled) {
			if(climbY > y) y+=crawlStairSpeed;
			else if (climbY < y) y-=crawlStairSpeed;
			else state = CRAWLING;
			return;
		}
		
		if(climbY > y) y+=stairSpeed;
		else if (climbY < y) y-=stairSpeed;
		else state = WALKING;
	}

	private void walk() {
		if(left && xVel > -1.5) move(-walkSpeed);
		else if(right && xVel < 1.5) move(walkSpeed);
		else move(0);
	}
	
	private void crawl() {
		if(left && xVel > -.8) move(-crawlSpeed);
		else if(right && xVel < .8) move(crawlSpeed);
		else move(0);
	}
	
	private void move(float change) {
		xVel += change;
		x+=xVel;
		checkBounds();
	}
	
	private void fall() {
		y+=10;
		if(y >= groundY) {
			y = groundY;
			state = WALKING;
			if(fallDist > 10 * height || (crippled && fallDist > 5 * height)) {
				lineColor = Color.red;
				kill();
			}
			else if(fallDist > 5 * height) cripple();
			fallDist = 0;
		}
		x+=xVel;
	}
	
	public void climbTo(int toY, int toX) {
		climbY = toY;
		x = toX;
		state = CLIMBING;
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
		BurningHouse.killed(index);
		alive = false;
	}
	
	public void cripple() {
		BurningHouse.crippled(index);
		crippled = true;
	}
	
	public Module personIn() {
		for(Floor f : BurningHouse.floors) {
			if(y <= f.floorY && y > f.floorY-f.height) {
				for(Module m : f.modules) {
					if(x > m.x && x < m.x+m.width) return m;
				}
			}
		}
		return null;
	}
	
	public void checkStairs(boolean up) {
		for(Stairs s : BurningHouse.stairs) {
			if(s.x-s.width/2 < x && s.x+s.width/2 > x) {
				if(up && s.upY == (int)y) climbTo(s.downY, s.x);
				else if(!up && s.downY == (int)y) climbTo(s.upY, s.x);
			}		
		}
	}
	
	public void render(Graphics g){
		
		for(int i=0; i<particles.length; i++)
			if(particles[i] != null) particles[i].render(g);
		
		
		int intX = (int)x;
		int intY = (int)y-height;
		
		if(alive && left) pointLeft = true;
		else if(alive && right) pointLeft = false;
		
		if(state == CLIMBING) {
			g.setColor(lineColor);
			g.drawRect(intX-width/2, intY, width, height);
			return;
		}
		
		if(state == CRAWLING) {
			g.setColor(fillColor);
			g.fillRect(intX-height/2, intY+width, height, width);
			g.setColor(lineColor);
			g.drawRect(intX-height/2, intY+width, height, width);
			g.setColor(eyeColor);
			if(pointLeft) g.fillRect(intX-(height/2), intY+(width)+2, 4, 4);
			else g.fillRect(intX+(height/2)-4, intY+(width)+2, 4, 4);
			
			return;
		}

		g.setColor(fillColor);
		g.fillRect(intX-width/2, intY, width, height);
		g.setColor(lineColor);
		g.drawRect(intX-width/2, intY, width, height);
		
		g.setColor(eyeColor);
		
		if(up) g.fillRect(intX-2, intY, 4, 4);
		else if(right) g.fillRect(intX+(width/2)-4, intY+2, 4, 4);
		else if(left) g.fillRect(intX-width/2, intY+2, 4, 4);
		else g.fillRect(intX-2, intY+2, 4, 4);
		
		if(Game.debug) g.drawString(index+"", intX, intY);
		
	}
	

}
