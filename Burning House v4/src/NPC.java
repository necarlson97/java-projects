import java.awt.Graphics;

public class NPC extends Person {
	
	Stair toClimb;

	public NPC(Module randModule, int i) {
		super(randModule, i);
	}
	
	public void run() {
		if(module == null && alive) leaveArea();
		if(toClimb != null) {
			climbStairs();
		}
		
		super.run();
	}
	
	private void leaveArea() {
		down = false;
		if(x < 0-width || x > Game.windowWidth+width) save(); 
		if(x < Game.windowWidth/2) goLeft();
		else goRight();
	}
	
	@Override
	public GameObject checkBounds() {
		GameObject o = super.checkBounds();
		if(!down && o instanceof Door) ((Door) o).changeState();
		return o;	
	}

	public void render(Graphics g) {
		super.render(g);
	}
	
	public void burn() {
		super.burn();
		if(Driver.player.follower == this) return;
		if(r.nextInt(2) == 0) goLeft();
		else goRight();
	}
	
	public void save() {
		Driver.people[index] = null;
		Driver.save(this);
	}
	
	public void kill() {
		super.kill();
		Driver.killedNpc++;
	}
	
	public void climbStairs() {
		if(toClimb != null) {
			if(toClimb.x < x - width/2) goLeft();
			else if(toClimb.x > x + width/2) goRight();
			else if(toClimb.y > y && toClimb.stairAbove != null) {
				x = toClimb.x;
				y = toClimb.y;
				xVel = 0;
				toClimb = null;
			}
			else if(toClimb.y < y && toClimb.stairBelow != null) {
				x = toClimb.x;
				y = toClimb.y;
				xVel = 0;
				toClimb = null;
			}
		}
	}
	
	public void release() {
		if(!holder.down || holder.left || holder.right) {
			down = true;
			if(holder.left) goLeft();
			else if(holder.right) goRight();
			else stop();
			if(Driver.player.follower == this) 
				Driver.player.setFollower(null);
		}
		else if(holder == Driver.player) Driver.player.setFollower(this);
		
		super.release();
	}
	
	public void goLeft() {
		left = true;
		right = false;
	}
	
	public void goRight() {
		left = false;
		right = true;
	}
	
	public void stop() {
		left = false;
		right = false;
	}

}
