import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Card {
	
	static final int HOLLOW = 0;
	static final int HALF = 1;
	static final int FILL = 2;
	
	Shape shape;
	Color color;
	
	int fill;
	int number;
	
	int row;
	int col;
	
	int xMargin;
	int yMargin;
	
	int width;
	int height;
	
	Set set;
	
	Font font;
	
	boolean selected = false;
	
	int traits[] = new int[4];
	
	public Card(int shapeNumb, int colorNumb, int fill, int number, Set set) {
		 this.fill = fill;
		 this.number = number;
		 this.set = set;
		 
		 width = set.cardWidth;
		 height = set.cardHeight;
		 font = new Font("Press Start 2p", Font.PLAIN, width/(2*number));
		 
		 if(shapeNumb == 0) shape = new Shape0(this);
		 else if(shapeNumb == 1) shape = new Shape1(this);
		 else shape = new Shape2(this);
		 
		 if(colorNumb == 0) color = Color.pink;
		 else if(colorNumb == 1) color = Color.cyan;
		 else color = Color.orange;
		 
		 traits[0] = shapeNumb;
		 traits[1] = colorNumb;
		 traits[2] = fill;
		 traits[3] = number;
	}
	
	public void render(Graphics g, int x, int y) {
		if(selected) g.setColor(Color.white);
		else g.setColor(Color.darkGray);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setFont(font);
		g.setColor(color);
		
		shape.render(g, x, y);
	}
	
	public String toString() {
		return "s: " + traits[0] + ", c: " + traits[1] + ", f: " + traits[2] +", n: " + traits[3]; 
	}

}
