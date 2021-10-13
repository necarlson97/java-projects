import java.awt.Graphics;

public class NPC extends Person {

	public NPC(Module randModule, int i) {
		super(randModule, i);
	}
	
	public void run() {
		if(module == null && alive) leaveArea();
		
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
		if(o instanceof Door) ((Door) o).changeState();
		return o;	
	}

	public void render(Graphics g) {
		super.render(g);
	}
	
	public void burn() {
		super.burn();
		if(Driver.player.followTrain.contains(this)) return;
		if(r.nextInt(2) == 0) goLeft();
		else goRight();
	}
	
	public void save() {
		Driver.people[index] = null;
		Driver.save(this);
	}

}
