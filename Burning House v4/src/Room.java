import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Room extends HousePart{
	
	Color fillColor;
	Color lineColor;
	Color smokeColor;
	
	Story story;
	
	LinkedList<Module> modules; 
	
	float smoke;
	
	int beginIndex;
	int endIndex;
	
	Door doors[] = new Door[2];
	
	public Room(Story story, int x, int width, int i, int beginIndex, int endIndex) {
		super(x, story.y, width, story.height, i);
		this.story = story;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		setColors();
		modules = new LinkedList<Module>();
	}

	public void setColors() {
		float hue = r.nextFloat();
		lineColor = new Color(Color.HSBtoRGB(hue, (float) .8, (float).8));
		fillColor = new Color(Color.HSBtoRGB(hue, (float) .05, (float).99));
	}
	
	public float addSmoke() {
		if(smoke < .9) smoke += .001;
		return smoke;
	}
	
	public void removeSmoke() {
		if(smoke > 0) smoke -= .001;
	}
	
	public void spreadSmoke() {
		int rand = (int)(r.nextInt(11) * smoke);
		
		if(rand > 3) {
			int randModuleIndex = r.nextInt(endIndex - beginIndex) + beginIndex;
			story.house.addSmoke(story.index+1, randModuleIndex);
		}
		else if(rand > 1) {
			if(r.nextInt(2) == 0) story.addSmoke(index+1);
			else story.addSmoke(index-1);
		}
		
		if(leftOpen()) {
			if(story.addSmoke(index-1) < smoke) 
				removeSmoke();
		}
		if(rightOpen()) {
			if(story.addSmoke(index+1) < smoke)
				removeSmoke();
		}
	}
	
	public boolean leftOpen() {
		return (doors[0] == null || doors[0].open) &&
				(index > 0 || story.windows[0] == null || story.windows[0].broken);
	}
	
	public boolean rightOpen() {
		return (doors[1] == null || doors[1].open) &&
				(index < story.rooms.length || story.windows[1] == null || story.windows[1].broken);
	}
	
	public void run() {
		for(Module m : modules)
			m.run();
		if(smoke > .2) spreadSmoke();
	}
	
	public void render(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(x, y-height, width, height);
		g.setColor(lineColor);
		g.drawRect(x, y-height, width, height);
		for(Module m : modules) {
			if(m != null) m.render(g);
		}
		
		smokeColor = new Color(0, 0, 0, smoke);
		g.setColor(smokeColor);
		g.fillRect(x, y-height, width, (int)(height*smoke));
	}
	
	public void renderFire(Graphics g) {
		for(Module m : modules)
			 m.renderFire(g);
	}

}