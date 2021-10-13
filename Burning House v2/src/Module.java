import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Module {
	
	Random r = new Random();
	
	int x;
	int y;
	
	int width;
	int height;
	
	Color fillColor = Color.white;
	Color lineColor;
	
	int maxFires;
	Fire[] fires;
	
	float consumed = 0;
	float onFire = 0;
	
	int index;
	Floor floor;
	
	Room room;
	
	float fireSpeed = (float) .001;
	float consumeSpeed = (float) .0001;
	
	Door[] doors = new Door[2];
	
	public Module(int x, int y, int width, int height, int index, Floor f) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;
		
		this.index = index;
		floor = f;
		
		maxFires = width/20;
		fires = new Fire[maxFires];
	}
	
	public void run() {
		if(onFire > 0 && consumed < 1) {
			room.addSmoke();
			consumed += consumeSpeed;
			addFire();
		}
		else if(consumed > 1) consumed = 1;
		else if(consumed == 1 && onFire > 0) onFire -= fireSpeed;
		
		if(onFire > .7) spreadFire();
		
		for(int i=0; i<fires.length; i++) {
			float indexRatio = (float)i/fires.length;
			
			if(fires[i] == null) {
				if(indexRatio < onFire) 
					fires[i] = new Fire((int)(x+r.nextInt(width)), (int)(y-r.nextInt(height/3)), 10, 5); 
			}
			else {
				fires[i].run();
				if(consumed >= 1 && r.nextInt(10) == 0) fires[i].growing = false;
				if(fires[i].size <= 0) fires[i] = null;
			}
			
		}
		
		float gray = 1 - consumed;
		if(gray < 0) gray = 0;
		fillColor = new Color(gray, gray, gray);
		
		for(Door d : doors)
			if(d != null) d.run();

	}
	
	private void spreadFire() {
		int random = r.nextInt((int) (onFire*11));

		if(random > 9) floor.addFire(index, 1);
		else if(random > 8) {
			floor.addFire(index+1);
			if(doors[1] == null || doors[1].open) floor.addFire(index+1);
		}
		else if(random > 7) {
			floor.addFire(index-1);
			if(doors[0] == null || doors[0].open) floor.addFire(index-1);
		}
		else if(random > 5) floor.addFire(index, -1);
		
	}

	public void addFire() {
		if(consumed < 1 && onFire < 1) onFire += fireSpeed;
	}
	
	public void addSmoke() { 
		room.addSmoke();
	}
	
	public void extinguishFire() {
		onFire = 0;
		for(Fire f : fires)
			if(f != null) f.growing = false;
		
	}
	
	public void render(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(x, y-height, width, height);	
		
		for(Door d : doors)
			if(d != null) d.render(g);
	}
	
	public void renderFires(Graphics g) {
		for(Fire f : fires)
			if(f!=null) f.render(g);
	}
	
	public void renderDebug(Graphics g){
//		g.setColor(Color.green);
//		g.drawString("c: "+consumed+", f: "+onFire, x, y);
//		g.setColor(lineColor);
//		g.drawRect(x, y-height, width, height);
	}

}
