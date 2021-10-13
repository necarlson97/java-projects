import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FullTableButton {
	
	Set set;
	
	int x;
	int y;
	int width;
	int height;
	
	Color color = Color.red;
	
	Font font;
	
	String buttonString = "More Cards";
	
	public FullTableButton(Set s) {
		set = s;
		font = set.font.deriveFont(10);
		
		x = s.xMargin;
		y = s.yMargin - height - 100;
		
		width = buttonString.length() * font.getSize();
		height = s.cardHeight/2;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("More Cards", x, y+height/4+font.getSize());
		
	}

}
