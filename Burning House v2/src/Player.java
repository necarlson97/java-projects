import java.awt.Color;
import java.awt.Graphics;

public class Player extends Person{
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	GameObject heldObject;

	public Player(){
		super((int) (Game.windowWidth*.9), BurningHouse.groundY, new Color(128, 0, 255), -1);
	}
	
	public void run(){
		
		if (alive) {
			super.up = this.up;
			super.down = this.down;
			super.left = this.left;
			super.right = this.right;
			
			if(heldObject != null) {
				heldObject.x = x;
				heldObject.y = y-height;
				if(down || crippled) {
					if(heldObject instanceof Person) heldObject.y = y-width;
					else if(pointLeft) heldObject.x-=width;
				}
		
			}
		}
		
		if(heldObject != null && !alive) releaseObject();
		
		super.run();
	}
	
	public void spacePressed() {
		boolean completed = false;
		if(heldObject == null) {
			completed = grabPerson();
			if(!completed) grabObject();
		}
		else useObject(heldObject);
	}
	
	public void dPressed() {
		openNear();
	}
	
	public Person nearPerson() {
		for(Person p : BurningHouse.people) {
			if(p != null && p.x-p.width < x && p.x+width > x &&
					p.y-height/2 < y && p.y+height > y) {
				return p;
			}
		}
		return null;
	}
	
	private boolean grabPerson() {
		Person near = nearPerson();
		if(near != null) {
			near.down = true;
			heldObject = near;
			heldObject.held = true;
			return true;
		}
		return false;
	}
	
	private GameObject nearObject() {
		for(GameObject o : BurningHouse.objects) {
			if(o != null && o.x-o.width < x && o.x+width > x &&
					o.y-height/2 < y && o.y+height > y) {
				return o;
			}
		}
		return null;
	}
	
	private boolean grabObject() {
		GameObject near = nearObject();
		if(near != null) {
			heldObject = near;
			heldObject.held = true;
			return true;
		}
		return false;
	}
	
	private Door nearEnoughDoor() {
		if(module != null) {
			for(Door d : module.doors) {
				if(d != null && d.x-d.width-width < x && d.x+d.width+width > x
						&& d.y - height/2 < y && d.y + height/2 > y) {
					return d;
				}
			}
		}
		
		for(Floor f : BurningHouse.floors) {
			for(Module m : f.modules) {
				for(Door d : m.doors) {
					if(d != null && d.x-d.width-width < x && d.x+d.width+width > x
							&& d.y - height/2 < y && d.y + height/2 > y) {
						return d;
					}
				}
			}
		}
		return null;
	}
	
	private Window nearEnoughWindow() {
		if(module != null) {
			Window w = module.room.window;
			if(w != null && w.x-w.width-width < x && w.x+w.width+width > x
					&& w.y - height/2 < y && w.y + height/2 > y) {
				return w;
			}
		}
		return null;
	}

	private boolean openNear() {
		Door nearDoor = nearEnoughDoor();
		if(nearDoor != null) {
			nearDoor.changeState();
			return true;
		}
		Window nearWindow = nearEnoughWindow();
		if(nearWindow != null && heldObject != null && !(heldObject instanceof Person)) {
			nearWindow.use();
			return true;
		}
		return false;
	}
	
	private void useObject(GameObject object) {
		if(object == null) return;
		if(object instanceof Person) releaseObject();
		else if (down) releaseObject();
		else object.use();
	}

	private void releaseObject() {
		if(state == CLIMBING || state == FALLING) return;
		heldObject.held = false;
		if(!down) {
			if(pointLeft) heldObject.xVel -= 10;
			else heldObject.xVel += 10;
		}
		heldObject.y = y;
		heldObject = null;
	}

	@Override
	public void use() {
		
	}

}
