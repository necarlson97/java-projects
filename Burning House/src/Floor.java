import java.awt.Color;
import java.awt.Graphics;

public class Floor {
	
	int indexWidth = 50;
	
	int height = 80;
	int floorY;
	
	int maxRooms;
	Room[] rooms;
	
	BurningHouse house;
	
	int index;
	
	int leftOffset = BurningHouse.leftOffset;
	
	public Floor(int floorY, int index, BurningHouse house) {
		this.floorY = floorY;
		this.house = house;
		this.index = index;
		maxRooms = house.roomsPerFloor;
		setupRooms();
		
	}
	
	private void setupRooms() {
		rooms = new Room[maxRooms];
		int intX = leftOffset;
		for(int i=0; i<rooms.length; i++) {
			rooms[i] = new Room( intX, floorY, indexWidth, height, i, this);
			intX += rooms[i].width;
		}
		
	}

	public void run() {
		Room prevRoom = null;
		for(Room r : rooms) {
			if(r != null && r != prevRoom) r.run();
			prevRoom = r;
		}
	}
	
	public void startFire(int roomIndex) {
		if(roomIndex < 0 || roomIndex >= rooms.length) return;
		rooms[roomIndex].startFire();
	}
	
	public void startFire(int roomIndex, int floorChange) {
		int floorIndex = index+floorChange;
		house.startFire(floorIndex, roomIndex);
	}
	
	public void render(Graphics g) {
		Room prevRoom = null;
		for(Room r : rooms) {
			if(r != null && r != prevRoom) r.render(g);
			prevRoom = r;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(leftOffset, floorY, maxRooms * indexWidth, 2);
	}
	
	public void renderFires(Graphics g) {
		Room prevRoom = null;
		for(Room r : rooms) {
			if(r != null && r != prevRoom) {
				r.renderFires(g);
				if(Game.debug) r.renderDebug(g);
			}
			prevRoom = r;
		}
	}
	

}
