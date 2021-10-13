import java.awt.Color;
import java.awt.Graphics;

public class Bar {

	private int x;
	private int y;
	private int width;
	private int height;
	float progress;
	Color c = Color.white;
	
	public Bar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		g.drawString("Hashing "+progressStr, x, y);
	}
}
