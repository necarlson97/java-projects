import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class Message {
	
	String drawString = "";
	String fullString;
	
	float red=1;
	float grn=1;
	float blu=1;
	int pointSize = 18;
	int screenPosition;
	
	public Message(String inputString){
		drawString = inputString;	
		fullString = inputString;	
	}
	
	public void run(int count){
		drawString = fullString; 
	}
	
	public void render(Graphics g, int y){
		g.setColor(new Color(red, grn, blu));
		Font font = new Font("Courier", Font.PLAIN, pointSize);
		g.setFont(font);
		g.drawString(drawString, 5, y);	
	}
	
	

}
