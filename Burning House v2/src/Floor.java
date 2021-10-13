import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Floor {
	
	Random r = Game.r;
	
	int indexWidth = 50;
	
	int height = 80;
	int floorY;
	
	int maxModules;
	Module[] modules;
	Room[] rooms;
	
	BurningHouse house;
	
	int index;
	
	int leftOffset = BurningHouse.leftOffset;
	
	public Floor(int floorY, int index, BurningHouse house) {
		this.floorY = floorY;
		this.house = house;
		this.index = index;
		maxModules = house.modulesPerFloor;
		setupModules();
		setupRooms();
	}
	
	private void setupModules() {
		modules = new Module[maxModules];
		int intX = leftOffset;
		for(int i=0; i<modules.length; i++) {
			modules[i] = new Module( intX, floorY, indexWidth, height, i, this);
			intX += modules[i].width;
		}
	}
	
	private void setupRooms() {
		rooms = new Room[maxModules];
		int i=0;
		int beginIndex = 0;
		int endIndex = 0;
		while(i<modules.length && endIndex < modules.length) {
			beginIndex = endIndex;
			endIndex+= r.nextInt(5)+1;
			if(endIndex > modules.length) endIndex = modules.length;
			rooms[i] = new Room(this, beginIndex, endIndex, i);
			
			for(int j=beginIndex; j<endIndex; j++) {
				modules[j].room = rooms[i];
			}
			
			int k = beginIndex;
			if(k > 0) {
				modules[k].doors[0] = new Door(modules[k].x, modules[k].y, rooms[i].roomColor);
				modules[k-1].doors[1] = modules[k].doors[0];
			}
			rooms[i].leftDoor = modules[k].doors[0];
			if(i > 1) rooms[i-1].rightDoor = rooms[i].leftDoor;
			
			i++;
		}
		
		if(index == 0) {
			modules[0].doors[0] = new Door(modules[0].x, modules[0].y, modules[0].room.roomColor);
			rooms[0].leftDoor = modules[0].doors[0];
			
			int k = modules.length-1;
			modules[k].doors[1] = new Door(modules[k].x+modules[k].width, modules[k].y, modules[k].room.roomColor);
			rooms[i-1].rightDoor = modules[k].doors[1];
		}
	}

	public void run() {
		Module prevModule = null;
		for(Module m : modules) {
			if(m != null && m != prevModule) m.run();
			prevModule = m;
		}
		for(Room rm : rooms) {
			if(rm != null) rm.run();
		}
	}
	
	public void addFire(int moduleIndex) {
		if(moduleIndex < 0 || moduleIndex >= modules.length) return;
		modules[moduleIndex].addFire();
	}
	
	public void addFire(int moduleIndex, int floorChange) {
		int floorIndex = index+floorChange;
		house.addFire(floorIndex, moduleIndex);
	}
	
	public void addSmoke(int moduleIndex) {
		if(moduleIndex < 0 || moduleIndex >= modules.length) return;
		modules[moduleIndex].addSmoke();
	}
	
	public void addSmoke(int moduleIndex, int floorChange) {
		int floorIndex = index+floorChange;
		house.addSmoke(floorIndex, moduleIndex);
	}
	
	public void render(Graphics g) {
		Module prevModule = null;
		for(Module m : modules) {
			if(m != null && m != prevModule) m.render(g);
			prevModule = m;
		}
		for(Room rm : rooms) {
			if(rm != null) rm.render(g);
		}
		
//		g.setColor(Color.darkGray);
//		g.fillRect(leftOffset, floorY, maxModules * indexWidth, 2);
//		g.drawString(index+"", leftOffset-20, floorY);
	}
	
	public void renderFires(Graphics g) {
		Module prevModule = null;
		for(Module m : modules) {
			if(m != null && m != prevModule) {
				m.renderFires(g);
				if(Game.debug) m.renderDebug(g);
			}
			prevModule = m;
		}
	}
	

}
