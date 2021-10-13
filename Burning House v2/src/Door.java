import java.awt.Color;
import java.awt.Graphics;

public class Door extends GameObject{
	
	boolean open = false;
	boolean locked = false;
	boolean broken = false;
	
	Color color;

	public Door(int x, int y, Color color) {
		super(x, y, 5, 25);
		this.color = color;
		
		if(r.nextInt(5) == 0) changeState();
	}
	
	@Override
	public void use() {
		changeState();
	}
	
	public void forceOpen() {
		if(open) return;
		open = true;
		width = 15;
		locked = false;
		broken = true;
	}
	
	public void changeState() {
		if(broken || locked) return;
		if(open) {
			open = false;
			width = 5;
		}
		else {
			open = true;
			width = 15;
		}
	}

	@Override
	public void run() {
		
	}

	@Override
	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX-width/2, intY-height, width, height);
		
		if(Game.debug) {
			g.setColor(Color.BLACK);
			g.drawRect(intX-width-10, intY-height, 2*(width+10), height);
		}
		
	}

}
