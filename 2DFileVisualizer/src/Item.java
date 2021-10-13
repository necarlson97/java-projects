import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class Item extends ManedgerVisual{
	
	private float x;
	private float y;
	
	private Color typeColor;
	
	private int width = 10;
	private int height = 20;
	
	public Item(File file, float x, float y, Color color){
		super(file);
		this.x = x;
		this.y = y;
		typeColor = new Color(color.getRGB()/2);
		
	}
	
	public void run(){
		
	}
	
	public void render(Graphics g){
		int intX = (int)x;
		int intY = (int)y;
		g.setColor(nameColor);
		g.fillRect(intX, intY, width, height);
		
		g.setColor(typeColor);
		g.drawRect(intX, intY, width, height);
	}

	public boolean isNearby(Player player) {
		return (player.x > x && player.x < x+width && player.y > y && player.y < y+height);
	}

}
