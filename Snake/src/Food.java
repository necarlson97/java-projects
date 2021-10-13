import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food {
	
	Random r = new Random();
	
	int row;
	int col;
	
	float grn = 1;
	boolean brighter;
	
	String effect = "none";
	
	public Food(){
		int scale = SnakeGame.boxSize;
		row = r.nextInt(SnakeGame.boxRows)+1;
		col = r.nextInt(SnakeGame.boxCols)+1;
	}
	
	public void run(){
		
	}
	
	public void render(Graphics g){
		
		if(brighter){
			grn+=.05;
			if(grn>.9) brighter = false;
		}
		else {
			grn-=.05;
			if(grn<.1) brighter = true;
		}
		
		int scale = SnakeGame.boxSize;
		g.setColor(new Color(0, grn, 0));
		g.fillRect(col*scale, row*scale, scale, scale);
		g.setColor(new Color(0, 1-grn, 0));
		g.drawRect(col*scale, row*scale, scale, scale);
		
	}

}
