import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class GameObject {
	
	static Random r = Game.r;
	static int margin = Driver.margin;
	int groundY;
	
	Particle[] particles;
	Person holder;
	Module module;
	
	float x;
	float y;
	float xVel;
	float yVel;
	double resistance = 1 + ((double)margin/30);
	
	int width;
	int height;
	
	int index;
	
	Color color;

	boolean pointLeft = false;
	
	public GameObject(float x, float y, int width, int height, int i) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		index = i;
		groundY = Driver.groundY;
	}
	
	public void run() {
		module = moduleIn();
		if(holder != null) {
			x = holder.x;
			y = holder.y;
			pointLeft = holder.pointLeft;
		}
		else {
			depreciateVelocity();
			if(!checkFalling()) {
				move(0);
			}
			
		}
		
		if(particles != null) runParticles();
	}
	
	public Module moduleIn() {
		for(Story s : Driver.house.stories) {
			if(s.y <= y && s.y+s.height > y) {
				for(Module m : s.modules) {
					if(m.x <= x && m.x+m.width > x) return m;
				}
			}
		}
		return null;
	}
	
	public Door nearDoor(int tolerance) {
		Story checkStory;
		if(module == null) checkStory = Driver.house.stories[0];
		else checkStory = module.room.story;
		for(Door d : checkStory.doors) {
			if(d != null && d.x-width-tolerance < x && d.x+width+tolerance > x) {
				return d;
			}
		}
		return null;
	}
	
	public Window nearWindow(int tolerance) {
		if(module == null || module.room.story.index == 0) return null;
		for(Window w : module.room.story.windows) {
			if(w != null && w.x-width-tolerance < x && w.x+width+tolerance > x
					&& w.y - height/2 < y && w.y + height/2 > y) {
				return w;
			}
		}
		return null;
	}
	
	public Stair nearStair(int tolerance) {
		Story checkStory;
		if(module == null) checkStory = Driver.house.stories[0];
		else checkStory = module.room.story;
		for(Stair s : checkStory.stairs) {
			if(s != null && s.x-width-tolerance < x && s.x+width+tolerance > x
					&& s.y - height/2 < y && s.y + height/2 > y) {
				return s;
			}
		}
		return null;
	}
	
	public GameObject nearObject(int tolerance) {
		for(NPC n : Driver.people) {
			if(n != null && n.x-width-tolerance < x && n.x+width+tolerance > x
					&& n.y - height/2 < y && n.y + height/2 > y) {
				return n;
			}
		}
		for(GameObject o : Driver.objects) {
			if(o != null && o.x-width-tolerance < x && o.x+width+tolerance > x
					&& o.y - height/2 < y && o.y + height/2 > y) {
				return o;
			}
		}
		return null;
	}
	
	public GameObject checkBounds() {
		Door nearDoor = nearDoor(0);
		if(nearDoor != null && !nearDoor.open) {
			if(nearDoor.x <= x) x=nearDoor.x+width;
			else x = nearDoor.x-width;
			xVel = 0;
			return nearDoor;
		}
		Window nearWindow = nearWindow(0);
		if(nearWindow != null && !nearWindow.broken) {
			if(nearWindow.x < x) x=nearWindow.x+width;
			else x = nearWindow.x-width;
			xVel = 0;
			return nearWindow;
		}
		return null;
	}
	
	public void move(float change) {
		xVel += change;
		x+=xVel;
		checkBounds();
	}
	
	private boolean checkFalling() {
		if(module == null && y < groundY) {
			yVel += margin;
			y += yVel;
			if(pointLeft) xVel -= margin/5;
			else xVel += margin/5;
			x+=xVel;
			if(y > groundY) {
				y = groundY; 
				yVel = 0;
			}
			return true;
		}
		return false;
	}
	
	public void runParticles() {
		for(Particle p : particles)
			if(p != null) p.run();
	}

	private void depreciateVelocity() {
		if(xVel > 0) xVel/=resistance;
		else if(xVel < 0) xVel/=resistance;
		if(yVel < 0) yVel/=resistance;
		else if(yVel > 0) yVel/=resistance;
		if(Math.abs(xVel) < .01) xVel = 0;
		if(Math.abs(yVel) < .01 ) yVel = 0;
	}
	
	public abstract void render(Graphics g);
	
	public void renderParticles(Graphics g) {
		if(particles !=  null) {
			for(Particle p : particles) {
				if(p != null) p.render(g);
			}
		}	
	}
	
	public GameObject grab(Person grabber) {
		holder = grabber;
		return this;
	}
	
	public void release() {
		holder = null;
	}
	
	public abstract void use();

}
