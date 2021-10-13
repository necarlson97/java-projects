import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class BloodDrop extends Particle{
	
	public int width = 5;
	public int height = 5;
	
	public boolean landed;
	public int landingY;
	
	public Color dropColor;
	
	private Random r = new Random();
	
	public BloodDrop(int x, int y, float xVel, float yVel){
		super(x, y, xVel, yVel);
		this.landingY = 510 + (r.nextInt(10)*10);
		
		int rand = r.nextInt(3);
		if(rand == 2) this.dropColor =  new Color(150, 0, 0);
		else this.dropColor = Color.red;
		
	}
	
	public void run(){
		if((!landed && y > landingY) || landed) {
			landed = true;
			width = 20;
			height = 10;
			
			xVel = 0;
			yVel = 0;
		}
		else super.move(true);
	}
	
	public void render(Graphics g){
		
		
		
		g.setColor(dropColor);
		g.fillRect((int)x-width/2, (int)y-height/2, width, height);
		
	}

}
