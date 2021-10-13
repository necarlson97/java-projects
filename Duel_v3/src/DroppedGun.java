import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class DroppedGun extends Particle{
	
	public boolean landed;
	public int landingY;
	
	static private Random r = new Random();
	
	private static Duelist duelist;
	private Color color = new Color(40, 40, 40);
	
	static float xVel = r.nextFloat();
	static float yVel = r.nextFloat();
	
	boolean fromPlayer;
	
	public DroppedGun(Duelist duelist){
		super(duelist.gunBarrelX, duelist.gunBarrelY, xVel, yVel);
		this.duelist = duelist;
		fromPlayer = duelist == Duel.player;
		this.landingY = duelist.y + 130 + (r.nextInt(10)*2);
		if(!fromPlayer) this.xVel *= -1;
		
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
		int intX = (int)x;
		int intY = (int)y;
		
		if(fromPlayer) {
			g.setColor(color);
			g.fillRect(intX, intY, 20, 5);
			g.setColor(color);
			g.fillRect(intX+5, intY, 5, 10);
		}
		else {
			g.setColor(color);
			g.fillRect(intX-10, intY, 20, 5);
			g.setColor(color);
			g.fillRect(intX, intY, 5, 10);
		}
		
	}

}
