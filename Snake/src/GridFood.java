import java.awt.Color;
import java.awt.Graphics;

public class GridFood extends Food{

		
	float blu = 1;
	
	public GridFood(){
		effect = "grid";
	}
	
	
	public void render(Graphics g){
		
		if(brighter){
			blu+=.025;
			if(blu>.9) brighter = false;
		}
		else {
			blu-=.025;
			if(blu<.1) brighter = true;
		}
		
		int scale = SnakeGame.boxSize;
		g.setColor(new Color(0, 0, blu));
		g.fillRect(col*scale, row*scale, scale, scale);
		g.setColor(new Color(0, 0, 1-blu));
		g.drawRect(col*scale, row*scale, scale, scale);
		
	}
}
