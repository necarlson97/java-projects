import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Console {
	
	private String value = "";
	
	Font font = new Font("Arial", 0, 20);

	public Console() {
		
	}
	
	public void backspace() {
		if(value.length() > 0) value = value.substring(0, value.length()-1);
	}
	
	public void add(char c) {
		value += c;
	}
	
	public String submit() {
		String ret = value;
		value = "";
		return ret;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.setFont(font);
		
		g.drawString(">"+value, 500, 500);
	}

}
