import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class DuelistBit extends Particle{
	
	public int width = 10;
	public int height = 10;
	
	public boolean landed;
	public int landingY;
	
	public Color bitColor;
	
	private Random r = new Random();
	
	public DuelistBit(int x, int y, float xVel, float yVel, Color duelistColor){
		super(x, y, xVel, yVel);
		this.landingY = 510 + (r.nextInt(10)*10);
		
		this.bitColor =  duelistColor;
	}
	
	public void run(){
		if((!landed && y > landingY) || landed) {
			landed = true;
			
			xVel = 0;
			yVel = 0;
		}
		else super.move(true);
	}
	
	public void render(Graphics g){
		
		g.setColor(bitColor);
		g.fillRect((int)x-width/2, (int)y-height/2, width, height);
		
	}

}
