
public class NPC extends Person{
	
	int index;

	public NPC(Module startingModule, int i) {
		super(startingModule.x+startingModule.width/2, startingModule.y, 
				startingModule.room.roomColor, i);
		index = i;
	}
	
	@Override
	public void use() {
		
	}
	
	public void run() {
		super.run();
		if(module == null && !held) {
			down = false;
			if(x < Game.windowWidth/2) left = true;
			else right = true;
		}
		if((x+width < 0 || x-width > Game.windowWidth) && state != FALLING && alive) {
			BurningHouse.saved(index);
		}
	}

}
