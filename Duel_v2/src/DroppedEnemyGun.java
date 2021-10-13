import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class DroppedEnemyGun extends Particle{
	
	
	public boolean landed;
	public int landingY;
	
	Color armColor;
	
	private Random r = new Random();
	
	public DroppedEnemyGun(int x, int y, float xVel, float yVel, Color duelistColor){
		super(x, y, xVel, yVel);
		this.landingY = 510 + (r.nextInt(10)*10);
		
		this.armColor =  duelistColor;
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
		
		g.setColor(armColor);
		g.fillRect((int)x-15, (int)y+35, 20, 5);
		
		g.setColor(Color.gray);
		g.fillRect((int)x-30, (int)y+30, 25, 5);
		g.setColor(Color.gray);
		g.fillRect((int)x-10, (int)y+35, 5, 10);
		
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y+35, 5, 5);
		
	}

}
