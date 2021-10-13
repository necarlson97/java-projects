import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PrintBox {
	
	static Color c = Color.white;
	static private Font f = new Font("Helvetica", 0, 14);
	static private int x = 0;
	static private int y = DisplayEngine1.windowHeight;
	static private int width = DisplayEngine1.windowWidth;
	static private int height = DisplayEngine1.windowHeight;
	
	static private String[] s;
	
	static void setLoc(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	static void setSize(int newWidth, int newHeight) {
		width = newWidth;
		height = newHeight;
		
		refresh();
	}
	
	static void setFontSize(int fontSize) {
		f = new Font(f.getName(), 0, fontSize);
		refresh();
	}
	
	static void refresh() {
		String[] newS = new String[height/f.getSize()];
		if(s == null) {
			s = newS;
			return;
		}
		for(int i=0; i<s.length; i++) {
			if(i >= newS.length) break;
			newS[i] = s[i];
		}
		s = newS;
	}
	
	static void incrementFont() {
		setFontSize(f.getSize() + 1);
	}
	
	static void decrementFont() {
		setFontSize(f.getSize() - 1);
	}
	
	static void addString(String string) {
		if(s == null) refresh();
		for(String str : string.split("\\n")) {
			for(int i=1; i<s.length; i++) {
				s[i] = s[i-1]; 
			}
			s[0] = str;
		}
		
	}
	
	static void render(Graphics g) {
		g.setColor(c);
		g.setFont(f);
		g.drawRect(x, y, width, height);
		
		int strY = y - s.length * f.getSize();
		for(String str : s) {
			strY += f.getSize();
			if(str != null) g.drawString(str, x, strY);
		}
		
	}

}
