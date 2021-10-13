import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class Driver extends Game{
	
	static int margin;
	static int groundY;
	
	static Sky sky;
	static House house;
	static int stories = 6;
	
	static int hundredCount = 100;
	
	static Player player;
	
	static int maxPeople = stories*2;
	static NPC[] people;
	
	Font smallFont;
	Font largeFont;
	
	static int extCount = 3;
	static int bedCount = 0;
	static int sinkCount = 0;
	static int axeCount = 1;
	static int houseObjectCount = extCount + bedCount + sinkCount + sinkCount + axeCount;
	static GameObject[] objects;
	
	static int sparkingModuleCount = 1;
	static Module[] sparkingModules;
	
	static LinkedList<PersonIcon> savedPeople;
	static LinkedList<PersonIcon> killedPeople;
	static int crippled = 0;
	static int killedNpc = 0;
	
	static boolean paused;
	
	public static void main(String[] args) {
		new Driver();
	}

	@Override
	void handleScreenResize() {
		reset();
	}
	
	public static void reset() {
		createHouse(house.y, house.width, house.height, house.stories.length);
	}

	@Override
	void setup() {
		groundY = (int) (Game.windowHeight*.85);
		int houseWidth = (int) (Game.windowWidth *.8);
		int houseHeight = (int) (groundY-(Game.windowHeight*.1));
		int houseY = groundY;
		margin = (houseHeight/stories)/9;
		
		smallFont = new Font("Press Start 2p", Font.PLAIN, (int) (windowWidth*.01));
		largeFont = new Font("Press Start 2p", Font.PLAIN, (int) (windowWidth*.03));
		
		createHouse(houseY, houseWidth, houseHeight, stories);
	}
	
	static void createHouse(int houseY, int houseWidth, int houseHeight, int stories) {
		house = new House(houseY, houseWidth, houseHeight, stories);
		sky = new Sky(groundY);
		player = new Player();
		people = setupPeople();
		objects = setupObjects();
		
		sparkingModules = new Module[sparkingModuleCount];
		for(int i=0; i<sparkingModules.length; i++) {
			sparkingModules[i] = house.randStory().randModule();
		}
		
		savedPeople = new LinkedList<PersonIcon>();
		killedPeople = new LinkedList<PersonIcon>();
	}

	private static NPC[] setupPeople() {
		people = new NPC[maxPeople];
		for(int i=0; i<people.length; i++) {
			Module randModule = house.randStory().randModule();
			people[i] = new NPC(randModule, i);
		}
		return people;
	}

	private static GameObject[] setupObjects() {
		objects = new GameObject[houseObjectCount];
		
		int i=0;
		while(i < extCount) {
			objects[i] = new Ext(house.randStory().randModule(), i);
			i++;
		}
		while(i < extCount + axeCount) {
			objects[i] = new Axe(player, i);
			i++;
		}
		
		return objects;
	}

	@Override
	void runGame() {
		if(paused) return;
		if(sky != null) sky.run();
		if(house != null) house.run();
		for(NPC p : people)
			if(p!=null) p.run();
		player.run();
		for(GameObject o : objects)
			if(o!=null) o.run();
		
		for(Module m : sparkingModules) {
			if(m.onFire <= 0) m.addFire();
		}
		
		
	}
	
	public static void save(Person p) {
		int iconX = margin + ((p.width) * savedPeople.size());
		int iconY  = groundY + margin + p.height;
		savedPeople.add(new PersonIcon(p, iconX, iconY));
	}
	
	public static void cripple(Person p) {
		crippled ++;
	}
	
	public static void kill(Person p) {
		int iconX = Game.windowWidth - (margin + ((p.width) * killedPeople.size()));
		int iconY  = groundY + margin + p.height;
		killedPeople.add(new PersonIcon(p, iconX, iconY));
	}

	@Override
	void renderGame(Graphics2D g) {
		if(sky != null) sky.render(g);
		if(house != null) house.render(g);
		for(NPC p : people)
			if(p!=null) p.render(g);
		player.render(g);
		for(GameObject o : objects)
			if(o!=null) o.render(g);
		if(house != null) house.renderFire(g);
		
		renderPlayerStatus(g);
		renderPeopleStatuses(g);
	}
	
	private void renderPlayerStatus(Graphics g) {
		g.setFont(largeFont);
		int intX = Game.windowWidth/3;
		int intY = groundY + 3*margin + largeFont.getSize(); 
		
		if(savedPeople.size() + killedNpc == people.length) {
			float ratio = 1-(float) killedNpc/people.length;
			g.setColor(new Color(1, ratio, ratio));
			g.drawString("nobody left", intX, intY);
		}
		else if(!player.alive) {
			g.setColor(Color.red);
			g.drawString("dead", intX, intY);
		}
		else if(player.crippled) {
			g.setColor(Color.orange);
			g.drawString("crippled", intX, intY);
		}
		
	}

	private void renderPeopleStatuses(Graphics g) {
		
		g.setFont(smallFont);
		
		g.setColor(Color.white);
		g.drawString("Saved: "+savedPeople.size(), margin, groundY + 2*margin);	
		for(PersonIcon p : savedPeople) {
			p.render(g);
		}
		
		g.setColor(Color.orange);
		g.drawString("Crippled: "+crippled, Game.windowWidth/2, groundY + 2*margin);
		
		g.setColor(Color.red);
		g.drawString("Killed: "+killedPeople.size(), Game.windowWidth - margin - (smallFont.getSize() * 9), groundY + 2*margin);	
		for(PersonIcon p : killedPeople) {
			p.render(g);
		}

			
	}

	static void slower() {
		hundredCount += 10;
	}
	
	static void faster() {
		if(hundredCount > 10) hundredCount -= 10;
	}
	
	public static double oscillateNumber(int numb, int period, double scale) {
	    return Math.sin(numb*2*Math.PI/period)*(scale/2) + (scale/2);
	}

}
