import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Stairs {
	
	Random r = new Random();
	
	int width = Player.width + 4;
	int height = Player.height + 4;
	
	int x;
	int upY;
	int downY;
	
	public Stairs(Floor up, Floor down) {
		x = (r.nextInt(up.rooms.length)*up.indexWidth)
				+up.indexWidth/2+BurningHouse.leftOffset;
		upY = up.floorY;
		downY = down.floorY;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x-width/2, downY-height, width, height);
		g.fillRect(x-width/2, upY-height, width, height);
		
		g.setColor(Color.green);
		g.drawRect(x-width/2, downY-height, width, height);
		g.setColor(Color.red);
		g.drawRect(x-width/2, upY-height, width, height);
		
	}

}
