import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PrintBox {
	
	static Color c = Color.white;
	static private Font f = new Font("Helvetica", 0, 14);
	static private String s = "";
	static private int x;
	static private int y;
	static private int width;
	static private int height;
	
	static void setLoc(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	static void setSize(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;
	}
	
	static void setFontSize(int fontSize) {
		f = new Font(f.getName(), 0, fontSize);
	}
	
	static void incrementFont() {
		setFontSize(f.getSize() + 1);
	}
	
	static void decrementFont() {
		setFontSize(f.getSize() - 1);
	}
	
	static void setString(String string) {
		s = string;
	}
	
	static void addString(String string) {
		s += string+"\n";
	}
	
	static void render(Graphics g) {
		g.setColor(c);
		g.setFont(f);
		g.drawRect(x, y, width, height);
		
		int strY = y;
		for(String str : s.split("\\n")) {
			strY += f.getSize();
			g.drawString(str, x, strY);
		}
		
	}

}
