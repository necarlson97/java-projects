import java.awt.Color;
import java.awt.Graphics;

public class FastFood extends Food{
	
	float red = 1;
	
	
	public FastFood(){
		effect = "speed -2";
	}
	
	public void render(Graphics g){
		
		if(brighter){
			red+=.1;
			if(red>.8) brighter = false;
		}
		else {
			red-=.1;
			if(red<.2) brighter = true;
		}
		
		int scale = SnakeGame.boxSize;
		g.setColor(new Color(red, 0, 0));
		g.fillRect(col*scale, row*scale, scale, scale);
		g.setColor(new Color(1-red, 0, 0));
		g.drawRect(col*scale, row*scale, scale, scale);
		
	}

}
