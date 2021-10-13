import java.awt.Color;
import java.awt.Graphics;

public class Wall {
	
	int x;
	int y;
	
	int width;
	int height;
	
	Color color = Color.LIGHT_GRAY;
	
	public Wall(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.drawRect(x, y, width, height);
	}

}
