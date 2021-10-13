import java.awt.Color;
import java.awt.Graphics;

public class Stair extends HousePart {
	
	Color color;
	
	Color signColor = Color.WHITE;
	int signWidth;
	int signHeight;
	
	Stair stairAbove;
	Stair stairBelow;

	public Stair(Module m, int index) {
		super(m.x+m.width/2, m.y, m.width/2, m.height/2, index);
		color = m.room.fillColor.darker();
		signWidth = margin;
		signHeight = margin/2;
	}
	
	public Stair(Stair s, Story story, int index) {
		super(s.x, story.y, s.width, s.height, index);
		color = s.color;
		signWidth = margin;
		signHeight = margin/2;
		setStairAbove(s);
		s.setStairBelow(this);
	}
	
	public void setStairAbove(Stair above) {
		stairAbove = above;
		if(stairBelow == null) signColor = Color.red;
	}
	
	public void setStairBelow(Stair below) {
		stairBelow = below;
		signColor = Color.green;
	}

	@Override
	public void run() {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x-width/2, y-height, width, height);
		
		g.setColor(signColor);
		g.fillRect(x-signWidth/2, y-height-signHeight/2, signWidth, signHeight);
		
//		if(stairBelow != null) {
//			g.drawLine(x-width/2, y, stairBelow.x-width/2, stairBelow.y);
//		}
//		
//		if(stairAbove != null) {
//			g.drawLine(x+width/2, y, stairAbove.x+width/2, stairAbove.y);
//		}
		
	}

}
