import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.LinkedList;

public class BurningHouse extends Game{
	
	static Player player;
	
	static int modulesPerFloor = 16;
	int maxFloors = 6;
	static Floor[] floors;
	static Stairs[] stairs;
	
	int maxPeople = 10;
	static NPC[] people;
	
	int maxExtinguishers = 3;
	static GameObject[] objects;
	int maxObjects = maxExtinguishers;
	
	static int groundY;
	
	Font headerFont;
	Font bodyFont;
	static String fastForward = "";
	int margin;
	static int leftOffset;
	
	Color groundColor = Color.black;//new Color(0, 75, 0);
	Color skyColor;
	
	static float skySmoke = 0; 
	
	public static int saved = 0;
	public static int crippled = 0;
	public static int killed = 0;
	
	public static void main(String[] args) {
		new BurningHouse();
	}
	

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		headerFont = new Font("Press Start 2p", Font.PLAIN, (int) (windowWidth*.05));
		bodyFont = new Font("Press Start 2p", Font.PLAIN, (int) (windowWidth*.02));
		margin = Game.windowWidth/100;
		leftOffset = margin*5;
		
		groundY = (int) (Game.windowHeight * .9);
		player = new Player();
		
		setupFloors();
		setupPeople();
		setupObjects();
		
		//sparkFire();
	}
	
	void setupFloors() {
		floors = new Floor[maxFloors];
		stairs = new Stairs[(maxFloors-1) * 2]; 
				
		int intY = groundY;
		
		for(int i=0; i<floors.length; i++) {
			floors[i] = new Floor(intY, i, this);
			intY -= floors[i].height;
		}
		
		for(int i=0; i<floors.length-1; i++) {
			stairs[i] = new Stairs(floors[i], floors[i+1]);
		}
		
		int emergencyStairsIndex = r.nextInt(floors[0].modules.length);
		for(int i=0; i<floors.length-1; i++) {
			stairs[i+floors.length-1] = new Stairs(floors[i], floors[i+1], emergencyStairsIndex);
		}
	}
	
	void setupPeople() {
		people = new NPC[maxPeople];
		LinkedList<Integer> takenSpots = new LinkedList<Integer>();
		for(int i=0; i<people.length; i++) {
			Floor randFloor = floors[r.nextInt(floors.length)];
			Module randModule = randFloor.modules[r.nextInt(randFloor.modules.length)];
			if(!takenSpots.contains(i)) {
				takenSpots.add(i);
				people[i] = new NPC(randModule, i);
			}
			else i--;
		}
	}
	
	void setupObjects() {
		objects = new GameObject[maxObjects];
		LinkedList<Integer> takenSpots = new LinkedList<Integer>();
		int i=0;
		
		while(i<maxExtinguishers) {
			Floor randFloor = floors[r.nextInt(floors.length)];
			Module randModule = randFloor.modules[r.nextInt(randFloor.modules.length)];
			if(!takenSpots.contains(i)) {
				takenSpots.add(i);
				objects[i] = new Extinguisher(randModule, i);
			}
			i++;
		}
		
	}
	
	static void sparkFire() {
		addFire(r.nextInt(floors.length), r.nextInt(modulesPerFloor));
	}
	
	public static void addFire(int floorIndex, int moduleIndex) {
		if(floorIndex < 0 || floorIndex >= floors.length) return;
		floors[floorIndex].addFire(moduleIndex);
	}
	
	public static void addSmoke(int floorIndex, int moduleIndex) {
		if(floorIndex < 0 || floorIndex >= floors.length) {
			addSkySmoke();
			return;
		}
		floors[floorIndex].addSmoke(moduleIndex);
	}

	@Override
	void runGame() {
		
		player.run();
		for(Floor f : floors)
			if(f != null) f.run();
		for(NPC p : people)
			if(p != null) p.run();
		for(GameObject o : objects)
			if(o != null) o.run();
		
		setSkyColor();
	}
	
	public void setSkyColor() {
		if(skySmoke < .22) {
			float gray = (float) (.78+(.22 - skySmoke));
			skyColor = new Color((float).78, gray, gray);
		}
		else {
			float gray = 1 - skySmoke;
			skyColor = new Color(gray, gray, gray);
		}
	}
	
	public static void addSkySmoke() {
		if(skySmoke < .93) skySmoke += .001;
	}
	
	public static void changeFireSpeed(int change) {
		if(fastForward.length() + change < 0) return;
		if(change > 0) fastForward += ">";
		else fastForward = fastForward.substring(0, fastForward.length()-1);
		
		for(Floor f : floors) {
			for(Module m : f.modules) {
				if(change > 0) {
					m.consumeSpeed *= 2;
					m.fireSpeed *= 2;
				}
				else {
					m.consumeSpeed /= 2;
					m.fireSpeed /= 2;
				}
			}
			for(Room m : f.rooms) {
				if(m != null) {
					if(change > 0) m.smokeSpeed *= 2;
					else  m.smokeSpeed /= 2;
				}
			}
		}	
	}
	
	public static void saved(int personIndex) {
		saved++;
		people[personIndex] = null;
	}
	
	public static void crippled(int personIndex) {
		crippled++;
	}
	
	public static void killed(int personIndex) {
		killed++;
	}

	@Override
	void renderGame(Graphics2D g) {
		renderBackground(g);
		for(Floor f : floors)
			if(f != null) f.render(g);
		for(Stairs s : stairs)
			if(s != null) s.render(g);
		for(Floor f : floors)
			if(f != null) f.renderFires(g);
		for(NPC p : people)
			if(p != null) p.render(g);
		
		player.render(g);
		
		for(GameObject o : objects)
			if(o != null) o.render(g);
		
		renderHUD(g);
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(skyColor);
		g.fillRect(0, 0, Game.windowWidth, groundY);
		g.setColor(groundColor);
		g.fillRect(0, groundY-20, Game.windowWidth, Game.windowHeight-groundY);
		
	}


	void renderHUD(Graphics g){
		g.setFont(headerFont);
		int intY = headerFont.getSize()+margin;
		
		g.setColor(Color.white);
		g.drawString(fastForward, margin, intY);
		
		g.setColor(Color.red);
		
		if(!player.alive) g.drawString("DEAD", Game.windowWidth/3, intY);
		else if(killed + saved >= maxPeople) g.drawString("NO ONE LEFT", Game.windowWidth/4, intY);
		else if(player.crippled) g.drawString("CRIPPLED", Game.windowWidth/3, intY);
		
		g.setFont(bodyFont);
		g.setColor(Color.orange);
		g.drawString("nothing", (int) (Game.windowWidth*.8), intY);
		intY += bodyFont.getSize()+margin;
		g.drawString("saved: "+saved, (int) (Game.windowWidth*.7), intY);
		intY += bodyFont.getSize()+margin;
		g.drawString("crippled: "+crippled, (int) (Game.windowWidth*.7), intY);
		intY += bodyFont.getSize()+margin;
		g.drawString("killed: "+killed, (int) (Game.windowWidth*.7), intY);
	}



}
