import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BurningHouse extends Game{
	
	Player player;
	
	static int roomsPerFloor = 8;
	int maxFloors = 5;
	static Floor[] floors;
	static Stairs[] stairs;
	
	static int groundY;
	
	Font hudFont;
	static String fastForward = "";
	int margin;
	static int leftOffset;
	
	Color groundColor = new Color(0, 128, 0);
	Color skyColor = new Color(200, 255, 255);
	
	static float skySmoke = 0;  
	
	public static void main(String[] args) {
		new BurningHouse();
	}
	

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		hudFont = new Font("Press Start 2p", Font.PLAIN, (int) (windowWidth*.03));
		margin = Game.windowWidth/100;
		leftOffset = margin*5;
		
		groundY = (int) (Game.windowHeight * .9);
		player = new Player();
		
		setupFloors();
	
	}
	
	static void sparkFire() {
		startFire(r.nextInt(floors.length), r.nextInt(roomsPerFloor));
	}
	
	void setupFloors() {
		floors = new Floor[maxFloors];
		stairs = new Stairs[maxFloors-1]; 
				
		int intY = groundY;
		
		for(int i=0; i<floors.length; i++) {
			floors[i] = new Floor(intY, i, this);
			intY -= floors[i].height;
		}
		
		for(int i=0; i<floors.length-1; i++) {
			stairs[i] = new Stairs(floors[i], floors[i+1]);
		}
	}
	
	public static void startFire(int floorIndex, int roomIndex) {
		if(floorIndex < 0 || floorIndex >= floors.length) return;
		floors[floorIndex].startFire(roomIndex);
	}

	@Override
	void runGame() {
		player.run();
		for(Floor f : floors)
			if(r != null) f.run();
		
	}
	
	public static void changeFireSpeed(int change) {
		if(fastForward.length() + change < 0) return;
		if(change > 0) {
			fastForward += ">";
			
			for(Floor f : floors) {
				for(Room r : f.rooms) {
					r.consumeSpeed *= 2;
					r.fireSpeed *= 2;
				}
			}
		}
		else {
			fastForward = fastForward.substring(0, fastForward.length()-1);
			
			for(Floor f : floors) {
				for(Room r : f.rooms) {
					r.consumeSpeed /= 2;
					r.fireSpeed /= 2;
				}
			}
		}
		
		
	}
	
	public static Room playerIn() {
		for(Floor f : floors) {
			if(Player.y == f.floorY) {
				for(Room r : f.rooms) {
					if(Player.x > r.x && Player.x < r.x+r.width) return r;
				}
			}
		}
		return null;
	}
	
	public static void checkStairs(boolean up) {
		for(Stairs s : stairs) {
			if(s.x-s.width/2 < Player.x && s.x+s.width/2 > Player.x) {
				if(up && s.upY == (int)Player.y) {
					Player.moveY = s.downY;
					Player.x = s.x;
				}
				else if(!up && s.downY == (int)Player.y) {
					Player.moveY = s.upY;
					Player.x = s.x;
				}
			}		
		}
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
		player.render(g);
		renderHUD(g);
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(skyColor);
		g.fillRect(0, 0, Game.windowWidth, groundY);
		g.setColor(groundColor);
		g.fillRect(0, groundY-20, Game.windowWidth, Game.windowHeight-groundY);
		
	}


	void renderHUD(Graphics g){
		g.setColor(Color.white);
		g.setFont(hudFont);
		g.drawString(fastForward, margin, hudFont.getSize()+margin);
	}



}
