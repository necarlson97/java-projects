import java.awt.Graphics;

public class Shape2 extends Shape{

	public Shape2(Card c) {
		super(c);
	}

	@Override
	public void render(Graphics g, int x, int y) {
		
		y+=yOffset;
		
		for(int i=0; i<number; i++) {
			x+=xOffset;
			
			g.setColor(card.color);
			
			if(card.fill == Card.FILL) {
				g.setColor(card.color);
				g.fillRect(x, y, width, height);
			}
			else if(card.fill == Card.HALF) {
				g.setColor(card.color.darker().darker());
				g.fillRect(x, y, width, height);
			}
			
			g.setColor(card.color);
			g.drawRect(x, y, width, height);
			
			x+=width;
			
		}
		
	}

}
