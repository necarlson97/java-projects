import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Room {
	
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
	
	float fireSpeed = (float) .001;
	float consumeSpeed = (float) .0001;
	
	public Room(int x, int y, int width, int height, int index, Floor f) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;
		
		this.index = index;
		floor = f;
		
		lineColor = randomBrightColor();
		
		maxFires = width/20;
		fires = new Fire[maxFires];
	}
	
	public Color randomBrightColor() {
		float red = (r.nextFloat()+1)/2;
		float grn = (r.nextFloat()+1)/2;
		float blu = (r.nextFloat()+1)/2;
		return new Color(red, grn, blu);
	}
	
	public void run() {
		if(onFire > 0 && consumed < 1) {
			consumed += consumeSpeed;
			if(onFire < 1) onFire += fireSpeed;
		}
		else if(consumed > 1) consumed = 1;
		else if(consumed == 1 && onFire > 0) onFire -= fireSpeed;
		
		if(onFire > .7) spread();
		
		for(int i=0; i<fires.length; i++) {
			float indexRatio = (float)i/fires.length;
			
			if(fires[i] == null) {
				if(indexRatio < onFire) 
					fires[i] = new Fire((int)(x+r.nextInt(width)), (int)(y-r.nextInt(height)), 10, 5); 
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
	}
	
	private void spread() {
		int random = r.nextInt((int) (onFire*11));

		if(random > 9) floor.startFire(index, 1);
		else if(random > 8) floor.startFire(index+1);
		else if(random > 7) floor.startFire(index-1);
		else if(random > 5) floor.startFire(index, -1);
		
	}

	public void startFire() {
		if(consumed < 1 && onFire < 1) onFire += fireSpeed;
	}
	
	public void render(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(x, y-height, width, height);	
	}
	
	public void renderFires(Graphics g) {
		for(Fire f : fires)
			if(f!=null) f.render(g);
	}
	
	public void renderDebug(Graphics g){
//		g.setColor(Color.green);
//		g.drawString("c: "+consumed+", f: "+onFire, x, y);
		g.setColor(lineColor);
		g.drawRect(x, y-height, width, height);
	}

}
