import java.awt.Graphics;

public abstract class Shape {
	
	Card card;
	int width;
	int height;
	int number;
	
	int xOffset;
	int yOffset;
	
	public Shape(Card c) {
		this.card = c;
		this.number = c.number;
		this.width = (int) ((c.width * .8) / number);
		this.height = (int) (c.height * .8);
		
		this.yOffset = (int) (c.height * .1);
		this.xOffset = (int) ((c.width * .1) / number);
	}
		
	abstract public void render(Graphics g, int x, int y);
	
	public String toString() {
		return this.getClass().getName();
	}
}
