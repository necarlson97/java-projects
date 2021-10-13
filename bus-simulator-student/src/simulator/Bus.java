package simulator;

public class Bus {
	public final int number;
	private final RoadMap roadMap;
	private int x;
	private int y;
	private int direction;

	public Bus(int number, RoadMap roadMap, int x, int y) {
		this.number = number;
		this.roadMap = roadMap;
		this.x = x;
		this.y = y;
		/* FOR OLIVIA: I changed your 'location' varable to be called 'direction'.
		 * It totally does the same job, I just renamed it to call to attention the fact that
		 * I made 2 east, 3 south, and 4 west, where as your 'location' varable had 2 as south, and 3 as east I think */
		this.direction = 0;
		// 0 is stopped, 1 is north, 2 is east, 3 is south, 4 is west
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

	/**
	 * Move the bus. Buses only move along the cardinal directions
	 * (north/south/east/west), not diagonally.
	 * 
	 * If the bus is stopped (that is, if it was just placed, or if it didn't
	 * move last time move() was called), then it should attempt to move north.
	 * If it cannot (no road, or off the map), then it should attempt south,
	 * then east, then west. If no move is available, it should stay in its
	 * current position.
	 * 
	 * If the bus is moving (that is, if it successfully moved the last time
	 * move() was called), then it should attempt to continue moving in the same
	 * direction.
	 * 
	 * If it cannot (no road, or off the map), then it should attempt to turn
	 * right. For example, if the bus was moving north, but there is no more
	 * road to the north, it should move east if possible.
	 * 
	 * If it cannot turn right, it should turn left. If it cannot turn left, it
	 * should reverse direction (that is, move backward, if possible). 
	 * If it cannot do any of these things, it should stay in its current position.
	 */
	
	/* FOR OLIVIA: So this is good, having these methods to check for avalable roads, but the one problem was that\
	 * you didn't check to see if the bus was gonna run off the map, which I did here by just checking to see if 
	 * x or y would become less than zero, or greater than 'one minus the size of the map' (which the last index).
	 * Oh! And your 'double speed bus' problem is because you were using:
	 *  'roadMap.isRoad(x, y--);' 
	 *  instead of 
	 *  roadMap.isRoad(x, y-1);
	 *  The difference being is that y-1 just checks at the value one less than y, 
	 *  where as y-- actually changes y to be less than one. Therefore, everytime you were calling a 
	 *  'isRoadSomething()' method, you were accedentaly moving the bus in that direction*/
	public boolean isRoadNorth(){
		if(y-1<0) return false; //North is off screen
		return roadMap.isRoad(x, y-1);
	}
	public boolean isRoadSouth(){
		if(y+1>roadMap.ySize-1) return false; //South is off screen
		return roadMap.isRoad(x, y+1);
	
	}
	public boolean isRoadEast(){
		if(x+1>roadMap.xSize-1) return false; //East is off screen
		return roadMap.isRoad(x+1, y);

	}
	public boolean isRoadWest(){
		if(x-1<0) return false; //West is off screen
		return roadMap.isRoad(x-1, y);

	}
	
	/* FOR OLIVIA: These methods for continuing, turning, or reversing are all basicaly the same.
	 * They look at the current direction, and if there is space in the map to go where they want, and then do.
	 * You can see how they are called down below in the 'move' method*/
	public boolean tryToContinueInSameDirection(){
		if(direction == 1 && isRoadNorth()) {
			y--;
			return true;
		}
		else if(direction == 2 && isRoadEast()) {
			x++;
			return true;
		}
		else if(direction == 3 && isRoadSouth()) {
			y++;
			return true;
		}
		else if(direction == 4 && isRoadWest()) {
			x--;
			return true;
		}
		else return false;
	}
	
	public boolean tryTurnRight(){
		if(direction == 1 && isRoadEast()) { //Currently going north, try east
			direction = 2;
			x++;
			return true;
		}
		else if(direction == 2 && isRoadSouth()) { //Currently going east, try south
			direction = 3;
			y++;
			return true;
		}
		else if(direction == 3 && isRoadWest()) { //Currently going south, try west
			direction = 4;
			x--;
			return true;
		}
		else if(direction == 4 && isRoadNorth()) { //Currently going west, try north
			direction = 1;
			y--;
			return true;
		}
		else return false; // Couldn't go right
	}
	
	public boolean tryTurnLeft(){
		if(direction == 1 && isRoadWest()) { //Currently going north, try west
			direction = 4;
			x--;
			return true;
		}
		else if(direction == 2 && isRoadNorth()) { //Currently going east, try north
			direction = 1;
			y--;
			return true;
		}
		else if(direction == 3 && isRoadEast()) { //Currently going south, try east
			direction = 2;
			x++;
			return true;
		}
		else if(direction == 4 && isRoadSouth()) { //Currently going west, try south
			direction = 3;
			y++;
			return true;
		}
		else return false; // Couldn't go left
	}
	
	public boolean tryReverse(){
		if(direction == 1 && isRoadSouth()) { //Currently going north, try south
			direction = 3;
			y++;
			return true;
		}
		else if(direction == 2 && isRoadWest()) { //Currently going east, try west
			direction = 4;
			x--;
			return true;
		}
		else if(direction == 3 && isRoadNorth()) { //Currently going south, try north
			direction = 1;
			y--;
			return true;
		}
		else if(direction == 4 && isRoadEast()) { //Currently going west, try east
			direction = 2;
			x++;
			return true;
		}
		else return false; // Couldn't reverse
	}
	
	
	/* FOR OLIVIA: So here is where the meat of it happens, but you can see by using the methods above, 
	 * it keeps this part looking pretty clean. First, if our bus is stopped, we do the whole 'try north, then south' thing
	 * But if the bus is currently moving (its direction is not equal to zero) then it trys continuing, turning, reversing, etc.
	 * You should play around with it a bit, its actually kinda fun to put a few busses in different places around a track
	 * and see where this takes them.*/
	public void move() {

		if(direction == 0){ // If the bus is currently stopped
			if(isRoadNorth()) {
				y--;
				direction = 1;
			}
			else if(isRoadSouth()) {
				y++;
				direction = 3;
			}
			else if(isRoadEast()) {
				x++;
				direction = 2;
			}
			else if(isRoadWest()) {
				x--;
				direction = 4;
			}
		}
		
		else { // If the bus is moving
			if(tryToContinueInSameDirection()) return;
			else if(tryTurnRight()) return;
			else if(tryTurnLeft()) return;
			else if(tryReverse()) return;
			else direction = 0; //Couldn't go anywhere, so bus is now stopped
			
		}
		
	}
	
	/*FOR OLIVIA: So yeah, thats all. This was a nice distraction from my other hw lol. I hope you are doing well,
	 * and I appoligize again for being bad at checking up on people and whatnot. Just want to let you know 
	 * I miss you and if you ever wanna talk or anything in always free.
	 * Heres hoping you get a good grade! :D*/

	
	
	
	
	
}
