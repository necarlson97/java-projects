import java.util.LinkedList;

public class Player extends Person {
	
	GameObject heldObject;
	
	NPC follower;
	
	public void run() {
		super.run();
		
		if(follower != null) {
			if(!follower.alive || follower.x < 0 || follower.x > Game.windowWidth) setFollower(null);
			else moveFollower(follower);
		}
	}

	private void moveFollower(NPC f) {
		f.down = down;
		if(f.toClimb != null) return;
			
		if(f.x + f.width + margin < x) f.goRight();
		else if (f.x - f.width - margin > x) f.goLeft();
		else f.stop();
		
	}

	public void upPressed() {
		
	}
	
	public void downPressed() {
		
	}
	
	public void dPressed() {
		if(down) {
			Stair nearStair = nearStair(0);
			if(nearStair != null && nearStair.stairBelow != null) climbStairs(nearStair.stairBelow);
			return;
		}
		if(up) {
			Stair nearStair = nearStair(0);
			if(nearStair != null && nearStair.stairAbove != null) climbStairs(nearStair.stairAbove);
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
	
	public void climbStairs(Stair s) {
		xVel = 0;
		x = s.x;
		y = s.y;
		if(follower != null) follower.toClimb = s;
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
		if(follower != null) setFollower(null);
	}
	
	public void setFollower(NPC p) {
		if(follower != null) follower.stop();
		follower = p;
	}

}
