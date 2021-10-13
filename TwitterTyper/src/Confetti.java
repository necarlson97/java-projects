import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Confetti {
	
	Random r = new Random();
	
	float x;
	float y;
	
	float xVel;
	float yVel;
	
	float gravity = (float).2;
	
	int size = 5;
	
	int number;
	int count;
	final int LIFETIME = 100;
	
	
	Color color;
	
	
	public Confetti(int number, int x, int y){
		this.x = x;
		this.y = y;
		this.number = number;
		
		xVel = (r.nextFloat()*2)-1;
		yVel = (r.nextFloat()*2)-1;
		
		float red = (float) (.5+(r.nextFloat()/2));
		float blu = (float) (.5+(r.nextFloat()/2));
		float grn = (float) (.5+(r.nextFloat()/2));
		
		color = new Color(red, blu, grn);
	}
	
	public void run(){
		if(count >= LIFETIME) return;
		count++;
		yVel+=gravity;
		
		x+=xVel;
		y+=yVel;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillRect((int) x, (int) y, size, size);
	}

}
