import java.awt.Color;
import java.awt.Graphics;

public class DiscoFood extends Food{
	
	float red = 1;
	float grn = 1;
	float blu = 1;
	
	public DiscoFood(){
		effect = "disco";
	}
	
public void render(Graphics g){
	
	grn = (grn+(float).03)%1;
	red = (red+(float).05)%1;
	blu = (blu+(float).01)%1;
		
	int scale = SnakeGame.boxSize;
	g.setColor(new Color(red, grn, blu));
	g.fillRect(col*scale, row*scale, scale, scale);
	g.setColor(new Color(1-red, 1-grn, 1-blu));
	g.drawRect(col*scale, row*scale, scale, scale);
		
	}

}
