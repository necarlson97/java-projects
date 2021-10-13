import java.util.LinkedList;

public class Player extends Person {
	
	GameObject heldObject;
	
	LinkedList<Person> followTrain = new LinkedList<Person>();
	
	public void run() {
		super.run();
		
		handleFollowers();
	}
	
	private void handleFollowers() {
		Person currPerson = this;
		for(Person p : followTrain) {
			if(p.y != currPerson.y) p.climbStairs(currPerson.closestStair());
			
			else if(p.x + p.width + margin < currPerson.x) p.goRight();
			else if (p.x - p.width - margin > currPerson.x) p.goLeft();
			else p.stop();
			p.down = currPerson.down;
			currPerson = p;
		}
	}

	public void upPressed() {
		
	}
	
	public void downPressed() {
		
	}
	
	public void dPressed() {
		if(down) {
			Stair nearStair = nearStair(0);
			if(nearStair != null && nearStair.stairBelow != null) y = nearStair.stairBelow.y;
			return;
		}
		if(up) {
			Stair nearStair = nearStair(0);
			if(nearStair != null && nearStair.stairAbove != null) y = nearStair.stairAbove.y;
			return;
		}
		
		Window nearWindow = nearWindow(width);
		if(nearWindow != null && heldObject != null) {
			if(nearWindow.smash() && heldObject instanceof Person) 
				((Person) heldObject).cripple();
			return;
		}
		Door nearDoor = nearDoor(width);
		if(nearDoor != null) {
			if(nearDoor.locked && (heldObject instanceof Axe)) nearDoor.forceOpen();
			nearDoor.changeState();
			return;
		}
		
	}
	
	public void sPressed() {
		if(heldObject != null) heldObject.use();
	}
	
	public void spacePressed() {
		if(heldObject != null) {
			heldObject.release();
			if(!down && !crippled) {
				if(pointLeft) heldObject.xVel -= 2*margin;
				else heldObject.xVel += 2*margin;
			}
			heldObject = null;
			return;
		}
		GameObject nearObject = nearObject(0);
		if(nearObject != null) heldObject = nearObject.grab(this);
	}
	
	public void kill() {
		super.kill();
		if(heldObject != null) heldObject.release();
	}

}
