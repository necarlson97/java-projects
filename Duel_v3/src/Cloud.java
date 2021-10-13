import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Cloud extends BackgroundObject{

	private static Random r = new Random();
	private Color color;
	
	int[] blockHeight = {(r.nextInt(10)+5)*10, (r.nextInt(10)+5)*10, (r.nextInt(10)+5)*10};
	int[] blockWidth = new int[3];
	
	
	
	public Cloud() {
		super((r.nextInt(15)*10)-490, r.nextInt(15)*10, 0, 0, (float).05);
		int grayness = 200+r.nextInt(5)*10;
		color = new Color(grayness, grayness, grayness);
		
		for(int i=0; i<blockWidth.length; i++){
			blockWidth[i] += blockHeight[i] + r.nextInt(10)*20;
		}
	}
	
	public Cloud(int x) {
		super(x, r.nextInt(15)*10, 0, 0, (float).05);
		int grayness = 200+r.nextInt(5)*10;
		color = new Color(grayness, grayness, grayness);
		
		for(int i=0; i<blockWidth.length; i++){
			blockWidth[i] += blockHeight[i] + r.nextInt(10)*20;
		}
	}
	
	public void run() {
		if(xVel == 0) xVel = (r.nextFloat()*5) - 1;
		if(yVel == 0) yVel = (r.nextFloat()/10) - (float).05;
		else super.move();
	}

	public void render(Graphics g) {
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(color);
		
		g.fillRect(intX, intY, blockWidth[0], blockHeight[0]);
		g.fillRect(intX-blockWidth[1]/2, intY+blockHeight[0]/2, blockWidth[1], blockHeight[1]);
		g.fillRect(intX+blockWidth[1]/2, intY+blockHeight[1]/2, blockWidth[2], blockHeight[2]);
		
	}

}
