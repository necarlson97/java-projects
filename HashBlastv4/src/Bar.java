import java.awt.Color;
import java.awt.Graphics;

public class Bar {

	private int x;
	private int y;
	private int width;
	private int height;
	float progress;
	Color c = Color.white;
	String action = "";
	
	public Bar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Bar() {
		int unit = DisplayEngine1.windowWidth/100;
		
		width = unit * 10;
		height = DisplayEngine1.windowHeight - unit * 10;
		
		x = DisplayEngine1.windowWidth - unit - width;
		y = unit * 2;
	}
	
	public void render(Graphics g) {
		g.setColor(c);
		int barHeight = (int) (progress * height);
		g.fillRect(x, y+(height-barHeight), width, barHeight);
		g.drawRect(x, y, width, height);
		String progressStr = "0%";
		try {
			 progressStr =  (progress+"").substring(2, 4)+"%";
		} catch (StringIndexOutOfBoundsException e) { }
		g.drawString(action+" "+progressStr, x, y);
	}
}
