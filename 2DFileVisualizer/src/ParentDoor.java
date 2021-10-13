import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class ParentDoor extends Door {
	
	public ParentDoor(File file, float x, float y, Color color){
		super(file, x, y, new Color(color.getRGB()/10));
	}
	
	public ParentDoor(float x, float y){
		super(null, x, y, Color.gray);
	}
	
	public void run(){
		
	}
	
	@Override
	public boolean canEnter(Player player){
		return(player.x > x && player.x < x+width && player.y+player.height > y-10);
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		
		g.setColor(nameColor);
		g.drawRect(intX, intY, width, height);
		
		g.setColor(typeColor);
		g.fillRect(intX, intY, width, height);
		g.fillOval(intX, intY+height-10, width, 20);
	}
	

}
