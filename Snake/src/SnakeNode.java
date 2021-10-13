import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class SnakeNode {
	
	SnakeNode next = null;
	
	Random r = new Random();

	float x;
	float y;
	
	float nextX;
	float nextY;
	
	int number;
	
	Color color = Color.white;
	float[] reds = {(float).5, 0, 0, 1, 1, 1};
	float[] greens = {0, 0, 1, 1, (float).2, 0};
	float[] blues = {1, 1, 0, 0, 0, 0};
	
	public SnakeNode(int x, int y, int n){
		this.x = x;
		this.y = y;
		this.number = n;
		
		n = n%6;	
		float r = reds[n];
		float g = greens[n];
		float b = blues[n];
		
		
		color = new Color(r, g, b).brighter().brighter();
	}
	
	
	public void move(float newX, float newY){
		if(next != null && (x!=newX || y!=newY)) next.move(x, y);
		x = newX;
		y = newY;
	}
	
	
	public void run(){
		
	}
	
	public void render(Graphics g){
		int scale = SnakeGame.boxSize;
		
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX, intY, scale, scale);
		g.setColor(Color.white);
		g.drawRect(intX, intY, scale, scale);
	}
	
	public String toString(){
		return number+": ("+x+","+y+")";
	}
}
