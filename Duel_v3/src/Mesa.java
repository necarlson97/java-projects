import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Mesa extends BackgroundObject{
	
	private static Random r = new Random();
	private Color color;
	
	int[] blockHeight = {r.nextInt(5)*10, r.nextInt(5)*10, r.nextInt(5)*10};
	int[] blockWidth = {r.nextInt(5)*10, r.nextInt(5)*10, r.nextInt(5)*10};
	
	int secondXAdd;
	int thirdXAdd;
	
	public Mesa(){
		super(Duel.WIDTH, Duel.horizonY+r.nextInt(2)*10, 0, 0);
		color = new Color(230, 210-r.nextInt(5)*5, 180-r.nextInt(5)*5);
		
		secondXAdd = blockWidth[0] + r.nextInt(3)*5;
		thirdXAdd = blockWidth[1] +  r.nextInt(3)*5;
	}
	
	public Mesa(int x){
		super(x, Duel.horizonY, 0, 0, -1);
		color = new Color(250 + r.nextInt(1)*5, 220+r.nextInt(2)*5, 190+r.nextInt(2)*5);
		
		secondXAdd = blockWidth[0] + r.nextInt(3)*5;
		thirdXAdd = blockWidth[1] +  r.nextInt(3)*5;
	}
	
	public void run(){
		super.move();
	}
	
	public void render(Graphics g){
		int intY = (int)y;
		
		int firstX = (int)x;
		int secondX = firstX + secondXAdd;
		int thirdX = secondX + thirdXAdd;
		
		g.setColor(color);
		g.fillRect(firstX, intY-blockHeight[0], blockWidth[0], blockHeight[0]);
		g.fillRect(secondX, intY-blockHeight[1], blockWidth[1], blockHeight[1]);
		g.fillRect(thirdX, intY-blockHeight[2], blockWidth[2], blockHeight[2]);
	}

}
