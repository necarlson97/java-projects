import java.awt.Color;
import java.awt.Graphics;

public class Door extends HousePart {
	
	Color color;
	
	boolean open = false;
	boolean broken = false;
	boolean locked = false;

	public Door(Room room, int index) {
		super(room.x + (room.width * index), room.y, margin/2, room.height/2, index);
		color = room.lineColor;
		if(r.nextInt(4) == 0) changeState();
		else if(r.nextInt(5) == 0) locked = true;
	}
	
	public void changeState() {
		if(broken) return;
		if(open) {
			open = false;
			width = margin/2;
		}
		else if(!locked) {
			open = true;
			width = 2*margin;
		}
	}
	
	public void forceOpen() {
		if(open) return;
		locked = false;
		changeState();
		broken = true;
	}

	@Override
	public void run() {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x-margin/4, y-height, width, height);
		
		if(broken) g.setColor(Color.red);
		else g.setColor(Color.black);
		if(locked || broken) g.fillRect(x-margin/4, y-height/2, width, margin);
	}

}
