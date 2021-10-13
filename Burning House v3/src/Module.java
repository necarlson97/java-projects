import java.awt.Color;
import java.awt.Graphics;

public class Module extends HousePart{
	
	Room room;
	Color color;
	
	float consumed = 0;
	float onFire = 0;
	
	int maxFires;
	Fire[] fires;
	
	int maxFoam = 8;
	
	float penetrate = 1;
	
	public Module(Room room, int x, int width, int index) {
		super(x, room.y, width, room.height, index);
		this.room = room;
		maxFires = (width / Fire.maxSize) * 3;
		fires = new Fire[maxFires];
		color = new Color(0, 0, 0, consumed);
		particles = new Particle[maxFoam];
	}
	
	public void addFire() {
		if(r.nextFloat() < penetrate) burn();
	}

	@Override
	public void run() {
		if(onFire > 0) {
			burn();
			if(onFire > .9) spreadFire();
			color = new Color(0, 0, 0, consumed);
			
			int fireRatio = (int) (onFire * fires.length);
			for(int i=0; i<fires.length; i++) 
				if(fires[i] == null && i <= fireRatio) fires[i] = newFire();
		}
		if(consumed > 1) {
			onFire-=.01;
			for(int i=0; i<fires.length; i++) 
				if(fires[i] != null) {
					if(fires[i].flaming )fires[i].flaming = false;
					if(fires[i].width < Fire.startingSize) fires[i] = null;
				}
		}
		for(Fire f : fires)
			if(f != null) f.run();
		
		runParticles();
	}
	
	private void spreadFire() {
		int rand = (int) (r.nextInt(20) * onFire);
		
		if(rand > 18) room.story.house.addFire(room.story.index-1, index);
		else if(rand > 17) room.story.house.addFire(room.story.index+1, index);
		else if(rand > 13) room.story.addFire(index+r.nextInt(3)-1);
		
	}
	
	private Fire newFire() {
		int randX = r.nextInt(3*(width/4))+x+width/8;
		int randY = r.nextInt(3*(height/4))+y-height+height/8;
		return new Fire(randX, randY);
	}

	public void burn() {
		if(consumed < 1) {
			consumed += .0002;
			if(onFire < 1) onFire += .001;
			if(r.nextInt(10) * onFire > 5) room.addSmoke();
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y-height, width, height);
	
		// Could find some way to draw burn marks as circles, but whatever
	}
	
	public void renderFire(Graphics g) {
		for(Fire f : fires)
			if(f != null) f.render(g);
		renderParticles(g);
	}

	public void extinguish() {
		onFire = 0;
		penetrate-=.1;
		for(int i=0; i<particles.length; i++) {
			particles[i] = new Foam(this, r.nextInt(margin*4), i);
		}
		
		for(int i=0; i<fires.length; i++) {
			if(fires[i] != null) {
				if(fires[i].flaming )fires[i].flaming = false;
			}
		}
	}
	
}
