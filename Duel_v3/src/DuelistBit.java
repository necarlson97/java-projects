import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class DuelistBit extends Particle{
	
	public int width = 5;
	public int height = 5;
	
	public boolean landed;
	public int landingY;
	
	private Duelist duelist;
	public Color bitColor;
	
	private Random r = new Random();
	
	public DuelistBit(float xVel, float yVel, Duelist duelist){
		super(duelist.torsoX, duelist.torsoY, xVel, yVel);
		this.duelist = duelist;
		this.landingY = duelist.y + 120 + (r.nextInt(10)*5);
		
		int rand = r.nextInt(3);
		if(rand == 2) bitColor =  Color.pink;
		else bitColor =  duelist.duelistColor;
	}
	
	public void run(){
		boolean hitCar = false;
		if(y > landingY) {
			for(TrainCar trainCar : Duel.trainCars){
				if(trainCar.hitTrainCar(x, y)) hitCar = true;
			}
		}
		if(hitCar){
			xVel = 0;
			yVel = 0;
		}
		else super.move(true);
	}
	
	public void render(Graphics g){
		
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(bitColor);
		g.fillRect(intX, intY, width, height);
		
	}

}
