import java.awt.Graphics;
import java.awt.Polygon;

public class Shape1 extends Shape{
	
	int[] xBase = new int[3];
	int[] yBase = new int[3];

	public Shape1(Card c) {
		super(c);
		
		xBase[0] = 0;
		xBase[1] = width/2;
		xBase[2] = width;
		
		yBase[0] = height;
		yBase[1] = 0;
		yBase[2] = height;
		
	}

	@Override
	public void render(Graphics g, int x, int y) {
		
		y+=yOffset;
		
		int xPoints[] = new int[3];
		int yPoints[] = new int[3];
		
		for(int i=0; i<number; i++) {
			x+=xOffset;
			
			for(int j=0; j<3; j++) {
				xPoints[j] = xBase[j] + x;
				yPoints[j] = yBase[j] + y;
			}
			
			g.setColor(card.color);
			
			if(card.fill == Card.FILL) {
				g.setColor(card.color);
				g.fillPolygon(xPoints, yPoints, 3);
			}
			else if(card.fill == Card.HALF) {
				g.setColor(card.color.darker().darker());
				g.fillPolygon(xPoints, yPoints, 3);
			}
			
			g.setColor(card.color);
			g.drawPolygon(xPoints, yPoints, 3);
			
			x+=width;
		}
		
	}

}
