import java.awt.Color;
import java.awt.Graphics;

public abstract class Person extends GameObject {
	
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	
	Color lineColor = Color.black;
	Color fillColor = Color.WHITE;
	Color eyeColor;
	int eyeSize = margin;
	
	float charred = 0;
	float smokeInhail = 0;
	
	boolean alive = true;
	boolean crippled = false;
	
	float walkSpeed = (float) .1 * margin;
	float crawlSpeed = (float) .04 * margin;
	
	int fallDist = 0;

	int maxParticles = 5;
	
	public Person(Module m, int i) {
		super(m.x+m.width/2, m.y, margin*2, margin*4, i);
		module = m;
		eyeColor = module.room.lineColor;
		
		particles = new Particle[maxParticles];
		
	}
	
	public Person() {
		super(Driver.house.x/2, Driver.groundY, margin*2, margin*4, -1);
		eyeColor = Color.black;
		
		particles = new Particle[maxParticles];
	}
	
	public void run() {
		super.run();
		if(alive) {
			if(crippled) down = true;
			
			if(module != null) {
				if(module.onFire > .3) burn();
				if(exposedToSmoke()) inhailSmoke();
				
				lineColor = new Color(charred, charred, charred);
				float gray = 1-smokeInhail;
				fillColor = new Color(gray, gray, gray);
				
			}
			else if(y < groundY && fallDist == 0) {
				fallDist = (int) (groundY-y);
			}
			else if(fallDist > 0 && y == groundY) {
				impact();
				return;
			}
			
			if(left) pointLeft = true;
			else if(right) pointLeft = false;
			
			if(down) crawl();
			else walk();
		}
		
	}

	private void impact() {
		
		if(holder != null) {// or mattressed
			fallDist -= 2*height;
		}
		
		if(r.nextInt(4) == 0) fallDist += (r.nextInt(4) - 2)*2*height;
		
		if(fallDist > 5*height) {
			for(int i=0; i<particles.length; i++)
				particles[i] = new Blood(this, i);
			lineColor = Color.RED;
			fillColor = new Color(128, 0, 0);
			kill();
		}
		else if(fallDist > 3*height) {
			for(int i=0; i<particles.length/2; i++)
				particles[i] = new Blood(this, i);
			lineColor = Color.RED;
			cripple();
		}
		fallDist = 0;
		
	}

	public boolean exposedToSmoke() {
		return module.room.smoke > .8 || 
				(module.room.smoke > .6 && (!down || holder != null)) || 
				(module.room.smoke > .4 && holder != null && !holder.down);
	}
	
	public void burn() {
		charred += .01;
		if(charred > 1) {
			charred = 1;
			kill();
		}
		for(int i=0; i<particles.length; i++)
			if(particles[i] == null) particles[i] = new Flame((int)x, (int)y, margin, i, this);
	}
	
	public void inhailSmoke() {
		smokeInhail += .005;
		if(smokeInhail > 1) {
			smokeInhail = 1;
			kill();
		}
		else if(smokeInhail > .7) cripple();
	}
	
	private void walk() {
		if(yVel != 0) return;
		
		if(left && xVel > -1.5*margin) xVel-=walkSpeed;
		else if(right && xVel < 1.5*margin) xVel+=walkSpeed;
	}
	
	private void crawl() {
		if(yVel != 0) return;
		if(left && xVel > -.8*margin) xVel-=crawlSpeed;
		else if(right && xVel < .8*margin) xVel+=crawlSpeed;
	}
	
	public void kill() {
		if(!alive) return;
		alive = false;
		eyeColor = Color.red;
		Driver.kill(this);
	}
	
	public void cripple() {
		if(crippled) {
			kill();
			return;
		}
		crippled = true;
		eyeColor = Color.orange;
		Driver.cripple(this);
	}

	@Override
	public void render(Graphics g) {
		if(!down && !crippled && alive && holder == null) renderStanding(g);
		else renderLying(g);
		renderParticles(g);
	}

	private void renderStanding(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(fillColor);
		g.fillRect(intX-width/2, intY-height, width, height);
		g.setColor(lineColor);
		g.drawRect(intX-width/2, intY-height, width, height);
		
		g.setColor(eyeColor);
		if(up) g.fillRect(intX-eyeSize/2, intY-height, eyeSize, eyeSize);
		else if(left) g.fillRect(intX-eyeSize, intY-height+eyeSize/2, eyeSize, eyeSize);
		else if(right) g.fillRect(intX, intY-height+eyeSize/2, eyeSize, eyeSize);
		else g.fillRect(intX-eyeSize/2, intY-height+eyeSize/2, eyeSize, eyeSize);
		
	}
	
	private void renderLying(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		if(holder != null) {
			if(holder.down) intY -= holder.width; 
			else intY -= holder.height;
			left = holder.left;
			right = holder.right;
		}
		
		g.setColor(fillColor);
		g.fillRect(intX-height/2, intY-width, height, width);
		g.setColor(lineColor);
		g.drawRect(intX-height/2, intY-width, height, width);
		
		g.setColor(eyeColor);
		
		if(left) intX -= eyeSize/2;
		else if(right) intX += eyeSize/2;
			
		if(pointLeft) g.fillRect(intX-height/2+eyeSize/2, intY-(width/2)-eyeSize/2, eyeSize, eyeSize);
		else g.fillRect(intX+(height/2)-eyeSize-eyeSize/2, intY-(width/2)-eyeSize/2, eyeSize, eyeSize);
		
	}
	
	public GameObject grab(Person grabber) {		
		return super.grab(grabber);
	}
	
	public void use() {
		return;
	}

}
