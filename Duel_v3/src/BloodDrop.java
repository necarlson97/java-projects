import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class BloodDrop extends Particle{
	
	public int width = 5;
	public int height = 5;

	public int landingY;
	
	public Color dropColor;
	
	private Random r = new Random();
	
	Duelist duelist;
	
	public BloodDrop(float xVel, float yVel, Duelist duelist){
		super(duelist.torsoX, duelist.torsoY, xVel, yVel);
		this.duelist = duelist;
		this.landingY = duelist.y + 120 + (r.nextInt(10)*5);
		
		int rand = r.nextInt(3);
		if(rand == 2) this.dropColor =  new Color(150, 0, 0);
		else this.dropColor = Color.red;
		
	}
	
	public void run(){
		boolean hitCar = false;
		if(y > landingY) {
			for(TrainCar trainCar : Duel.trainCars){
				if(trainCar.hitTrainCar(x, y)) hitCar = true;
			}
		}
		if(hitCar){
			width = 20;
			height = 10;
			xVel = 0;
			yVel = 0;
		}
		else super.move(true);
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(dropColor);
		g.fillRect(intX-width/2, intY-height/2, width, height);
		
	}

}
