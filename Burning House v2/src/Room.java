import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class Room {
	
	Random r = Game.r;
	
	Floor floor;
	
	float smoke = 0;
	
	int x;
	int y;
	
	float smokeSpeed = (float) .0001;
	
	int smokeHeight = 0;
	int height;
	int width;
	
	int beginIndex;
	int endIndex;
	int index;
	
	Color roomColor;
	
	Door leftDoor;
	Door rightDoor;
	
	Window window;
	
	public Room(Floor floor, int beginIndex, int endIndex, int roomIndex) {
		this.floor = floor;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.index = roomIndex;
		
		if(endIndex >= floor.modules.length) endIndex = floor.modules.length;
		
		x = floor.modules[beginIndex].x;
		y = floor.floorY;
		width = (endIndex - beginIndex) * floor.indexWidth;
		height = floor.height;
		
		roomColor = randomBrightColor();
		
		if(floor.index > 0) {
			if(beginIndex == 0) window = new Window(x, y);
			if(endIndex == floor.modules.length) window = new Window(x+width, y);
		}
		
	}
	
	public Color randomBrightColor() {
		float hue = r.nextFloat();
		return new Color(Color.HSBtoRGB(hue, (float) .4, (float).6));
	}
	
	public void run() {
		smokeHeight = (int) (smoke*height);
		if(smoke > .1) spreadSmoke();
		if(smoke > .5) spreadSmoke();
		if(smoke > .9) spreadSmoke();
		if(window != null) window.run();
	}
	
	private void spreadSmoke() {
		int random = r.nextInt((int) (smoke*10));
		smoke -= smokeSpeed;
		if(random > 3) {
			int randomModule = r.nextInt(endIndex) - beginIndex;
			floor.addSmoke(randomModule, 1);
		}
		else if(random > 2) {
			floor.addSmoke(endIndex+1);
			if(rightDoor != null && rightDoor.open) floor.addSmoke(endIndex+1);
		}
		else if(random > 1) {
			floor.addSmoke(beginIndex-1);
			if(leftDoor != null && leftDoor.open) floor.addSmoke(endIndex+1);
		}
	}

	public void addSmoke() { 
		if(smoke < 1) smoke += smokeSpeed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(x, y-height, width, smokeHeight);
		g.setColor(Color.gray);
		g.fillRect(x, y-height+smokeHeight/2, width, (smokeHeight)/2);
		
		g.setColor(roomColor);
		g.drawRect(x, y-height, width, height);
		if(window != null) window.render(g);
	}

}
