 import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class HiddenDoor extends Door {
	
	
	public HiddenDoor(Door door, float x, float y, Color nameColor){
		super(door.file, x, y, new Color(nameColor.getRGB()/8));
	}
	
	public void run(){
		
	}
	
	@Override
	public boolean canEnter(Player player){
		return(player.y > y && player.y < y+width && player.x < x+height+20);
	}
	
	@Override
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(nameColor);
		g.drawRect(intX, intY, height, width);
		
		g.setColor(typeColor);
		g.fillRect(intX, intY, height, width);
		g.fillOval(intX-10, intY, 20, width);
	}

}
