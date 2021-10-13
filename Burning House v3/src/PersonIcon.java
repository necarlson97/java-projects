import java.awt.Color;
import java.awt.Graphics;

public class PersonIcon {
	
	Color fillColor;
	Color lineColor;
	Color eyeColor;
	
	int width;
	int height;
	int eyeSize;
	
	int intX;
	int intY;
	
	public PersonIcon(Person p, int x, int y) {
		fillColor = p.fillColor;
		lineColor = p.lineColor;
		eyeColor = p.eyeColor;
		
		width = p.width/2;
		height = p.height/2;
		eyeSize = p.eyeSize/2;
		
		intX = x;
		intY = y;
		
	}
	
	public void render(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(intX-width/2, intY-height, width, height);
		g.setColor(lineColor);
		g.drawRect(intX-width/2, intY-height, width, height);
		g.setColor(eyeColor);
		g.fillRect(intX-eyeSize/2, intY-height+eyeSize/2, eyeSize, eyeSize);
		
	}

}
