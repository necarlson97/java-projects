import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Follicle {
	
	Random r = new Random();
	
	int x;
	int y;
	
	int length = 0;
	int width = 2;
	
	BestBarber bestBarber;
	
	Color color = Color.black;
	
	String region = "not yet";
	
	int growthDelay = 5;
	int growthOffset = r.nextInt(growthDelay);
	int maxLength = 200 + growthOffset;
	
	public Follicle(Point p, BestBarber b) {
		x = p.x;
		y = p.y;
		bestBarber = b;
	}
	
	public void run() {
		growthOffset = r.nextInt(growthDelay);
		if(length < maxLength && bestBarber.count % growthDelay == growthOffset) length++;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.drawRect(x, y, width, length);
	}

}
