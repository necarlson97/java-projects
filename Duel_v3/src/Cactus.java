import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Cactus extends BackgroundObject{
	
	static Random r = new Random();
	
	int width;
	int height;
	int shoulderY;
	int shoulderHeight;
	int shoulderWidth;
	int leftArmHeight;
	int rightArmHeight;
	int leftArmWidth;
	int rightArmWidth;
	
	private Color color;

	public Cactus(int x) {
		super(x, Duel.horizonY+50+r.nextInt(20)*10, 0, 0);
		setCactus();
	}
	
	public Cactus() {
		super(Duel.WIDTH, Duel.horizonY+50+r.nextInt(20)*10, 0, 0);
		setCactus();
	}
	
	public void setCactus(){
		width = (r.nextInt(2)+2)*10;
		height = width + (r.nextInt(5)+2)*10;
		
		if(height > 60){
			shoulderHeight = (r.nextInt(2)+2)*5;
			shoulderY = (height/10) * (r.nextInt(3)+5) - height;
			leftArmHeight = (r.nextInt(4)+2)*5;
			leftArmWidth = (r.nextInt(2)+2)*5;
			shoulderWidth = width + leftArmWidth + rightArmWidth + (r.nextInt(5)+2)*5;
		}
		if(height > 70){
			rightArmHeight = (r.nextInt(4)+2)*5;
			rightArmWidth = (r.nextInt(2)+2)*5;
		}
		
		color = new Color(0, (r.nextInt(10)+10)*10, 0);
	}
	
	public void run(){
		super.move();
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		g.fillRect(intX-width/2, intY-height, width, height);
		g.fillRect(intX-shoulderWidth/2, intY+shoulderY, shoulderWidth, shoulderHeight);
		g.fillRect(intX-shoulderWidth/2, intY+shoulderY-leftArmHeight, leftArmWidth, leftArmHeight);
		g.fillRect(intX+shoulderWidth/2-rightArmWidth, intY+shoulderY-rightArmHeight, rightArmWidth, rightArmHeight);
	}

}
