 import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class Door extends ManedgerVisual {
	
	public float x;
	public float y;
	protected Color typeColor;
	
	public int width = 20;
	public int height = 30;
	
	public Door(File file, float x, float y, Color color){
		super(file);
		this.x = x;
		this.y = y;
		typeColor = new Color(color.getRGB()/4);
	}
	
	
	public void run(){
		System.out.println(typeColor.toString());
	}
	
	public boolean canEnter(Player player){
		return(player.x > x && player.x < x+width && player.y < y+height+10);
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
//		g.setColor(Color.black);
//		g.fillRect(intX, intY, width, height+5);
		
		g.setColor(nameColor);
		g.drawRect(intX, intY, width, height);
		
		g.setColor(typeColor);
		g.fillRect(intX, intY, width, height);
		g.fillOval(intX, intY-10, width, 20);
	}
	

}
