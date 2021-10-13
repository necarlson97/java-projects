import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Story extends HousePart{
	
	int stairCount = 2;
	int moduleCount = 16;
	Module[] modules;
	Room[] rooms;
	Door[] doors;
	
	House house;
	
	Stair[] stairs;
	
	Window[] windows;
	
	public Story(House house, int x, int y, int width, int height, int i) {
		super(x, y, width, height, i);
		this.house = house;		
		
		rooms = setupRooms();
		doors = setupDoors();
		stairs = setupStairs();
		windows = setupWindows();
	}

	private Room[] setupRooms() {
		LinkedList<Room> newRooms = new LinkedList<Room>();
		modules = new Module[moduleCount];
		
		int moduleWidth = width/modules.length;
		int roomX = house.x;
		
		int maxRoomModules = 6;
		int minRoomModules = 3;
		
		int beginIndex = 0;
		int endIndex;
		int roomIndex = 0;
		while(beginIndex < modules.length) {
			endIndex = beginIndex + r.nextInt(maxRoomModules-minRoomModules)+minRoomModules;
			if(endIndex > modules.length) endIndex = modules.length;
			
			int roomWidth = (endIndex - beginIndex) * moduleWidth;
			Room newRoom = new Room(this, roomX, roomWidth, roomIndex, beginIndex, endIndex);
			newRooms.add(newRoom);
			int moduleX = newRoom.x;
			for(int i=beginIndex; i<endIndex; i++) {
				modules[i] = new Module(newRoom, moduleX, moduleWidth, i);
				newRoom.modules.add(modules[i]);
				moduleX += moduleWidth;
			}
			roomX += roomWidth;
			beginIndex = endIndex;
			roomIndex++;
			
		}
		return newRooms.toArray(new Room[newRooms.size()]);
	}
	
	public Door[] setupDoors() {
		LinkedList<Door> newDoors = new LinkedList<Door>();
		
		Door newDoor;
		int i=0;
		if(index == 0) { //If first floor, make left door
			newDoor = new Door(rooms[0], 0);
			rooms[0].doors[0] = newDoor;
			newDoors.add(newDoor);
		}
		while(i < rooms.length-1) { // set all the middle right doors, and middle left doors
			newDoor = new Door(rooms[i], 1);
			rooms[i].doors[1] = newDoor;
			rooms[i+1].doors[0] = newDoor;
			newDoors.add(newDoor);
			i++;
		}
		if(index == 0 && rooms[i].modules.size() > 1) { //If first floor, and the last room is one module, not make right door
			newDoor = new Door(rooms[i], 1);
			rooms[i].doors[1] = newDoor;
			newDoors.add(newDoor);
		}
		
		return newDoors.toArray(new Door[newDoors.size()]);
	}
	
	public Stair[] setupStairs() {
		stairs = new Stair[stairCount];
		
		if(index % 2 == 1 && index > 0) {
			Module randModule = stairModule();
			stairs[0] = new Stair(randModule, 0);
			house.stories[index-1].stairs[0] = new Stair(stairs[0], house.stories[index-1], 0);
			if(index > 1 && house.stories[index-2].stairs[0].x == stairs[0].x) {
				house.stories[index-2].stairs[0].setStairAbove(house.stories[index-1].stairs[0]);
				house.stories[index-1].stairs[0].setStairBelow(house.stories[index-2].stairs[0]);
			}
		}
		
		if(index == house.stories.length-1) {
			Module randModule = stairModule(); 
			Stair currStair = new Stair(randModule, 1); 
			stairs[1] = currStair;
			for(int i=index-1; i>=0; i--) {
				if(currStair.x == house.stories[i].stairs[0].x) {
					currStair.setStairBelow(house.stories[i].stairs[0]);
					house.stories[i].stairs[0].setStairAbove(currStair);
					currStair = house.stories[i].stairs[0];
				}
				else {
					house.stories[i].stairs[1] = new Stair(currStair, house.stories[i], 1);
					currStair = house.stories[i].stairs[1];
				}
			}
		}
		
		return stairs;
	}
	
	public Window[] setupWindows() {
		windows = new Window[2];
		
		if(index > 0) {
			windows[0] = new Window(this, 0);
			windows[1] = new Window(this, 1);
		}
		
		return windows;
	}
	
	public Module stairModule() {
		Room randRoom = rooms[r.nextInt(rooms.length)];
		if(randRoom.modules.size() <= 2) return stairModule();
		return randRoom.modules.get(r.nextInt(randRoom.modules.size()-1)+1);
	}
	
	public void addFire(int moduleIndex) {
		if(moduleIndex < 0 || moduleIndex >= modules.length) {
			return;
		}
		modules[moduleIndex].addFire();
	}
	
	public float addSmoke(int roomIndex) {
		if(roomIndex < 0 || roomIndex >= rooms.length) {
			Driver.sky.addSmoke();
			return -1;
		}
		return rooms[roomIndex].addSmoke();
	}

	public void run() {
		for(Room rm : rooms)
			if(rm != null) rm.run();
		for(Door d : doors)
			if(d != null)d.run();
		for(Stair s : stairs)
			if(s != null) s.run();
		for(Window w : windows)
			if(w != null) w.run();
	}
	
	public void render(Graphics g) {
		for(Room rm : rooms)
			if(rm != null) rm.render(g);
		for(Door d : doors)
			if(d != null) d.render(g);
		for(Stair s : stairs)
			if(s != null) s.render(g);
		for(Window w : windows)
			if(w != null) w.render(g);
	}
	
	public void renderFire(Graphics g) {
		for(Room rm : rooms)
			if(rm != null) rm.renderFire(g);
	}
	
	public Module randModule() {
		return modules[r.nextInt(modules.length)];
	}

}
