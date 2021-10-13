import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class House extends HousePart{
	
	Story[] stories;
	
	public House(int y, int width, int height, int storyCount) {
		super((Game.windowWidth-width)/2, y, width, height, 0);
		
		stories = setupStories(storyCount);
		
	}
	
	private Story[] setupStories(int storyCount) {
		stories = new Story[storyCount];
		
		int storyHeight = height/stories.length;
		int storyY = y;
		for(int i=0; i<stories.length; i++) {
			stories[i] = new Story(this, x, storyY, width, storyHeight, i);
			storyY-=storyHeight;
		}
		return stories;
	}

	public void sparkFire() {
		Story randStory = randStory();
		addFire(randStory.index, randStory.randModule().index);
	}
	
	public void addFire(int storyIndex, int moduleIndex) {
		if(storyIndex < 0 || storyIndex >= stories.length) {
			return;
		}
		stories[storyIndex].addFire(moduleIndex);
	}
	
	public void addSmoke(int storyIndex, int moduleIndex) {
		if(storyIndex < 0) return;
		if(storyIndex >= stories.length) {
			Driver.sky.addSmoke();
			return;
		}
		int selectedModule = stories[storyIndex].modules[moduleIndex].index;
		stories[storyIndex].addSmoke(selectedModule);
	}
	
	public void run() {
		for(Story s : stories)
			if(s!=null) s.run();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(x, y - height, width, height);
		for(Story s : stories)
			if(s!=null) s.render(g);
	}
	
	public void renderFire(Graphics g) {
		for(Story s : stories) 
			s.renderFire(g);
	}
	
	public Story randStory() {
		return stories[r.nextInt(stories.length)];
	}

}
