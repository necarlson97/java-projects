import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	
	int x;
	int y;
	
	int size;
	
	Color keyColor;
	Color channelColor;
	
	public Particle(Note n) {
		x = n.x;
		y = n.y;
		size = n.width;
		keyColor = n.keyColor;
		channelColor = n.channelColor;
	}
	
	public void render(Graphics g) {
		
		g.setColor(keyColor);
		g.fillOval(x, y, size, size);
		
		g.setColor(channelColor);
		g.drawOval(x, y, size, size);
		
		if(y > -size) y--;
	}

}
