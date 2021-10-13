import java.awt.Color;
import java.awt.Graphics;

public class Axe extends GameObject {
	
	Color handleColor = new Color(128, 100, 0);
	Color bladeColor = new Color(180, 180, 180);
	
	static int handleWidth = margin/2;
	static int handleHeight = margin*2;

	static int bladeWidth = margin;
	static int bladeHeight = margin/2;
	
	public Axe(Player p, int i) {
		super(p.x, p.y, handleHeight, margin, i);
	}

	@Override
	public void render(Graphics g) {	
		if(holder != null && !holder.down) renderHeld(g);
		else renderDropped(g);
	}

	private void renderHeld(Graphics g) {
		int intX = (int)x;
		int intY = (int)y-holder.height;
		
		if(pointLeft) intX-=bladeWidth+bladeWidth/2;
		else intX+=bladeWidth;
		
		g.setColor(handleColor);
		g.fillRect(intX, intY, handleWidth, handleHeight);
		
		if(pointLeft) intX-=bladeWidth/2;
		
		g.setColor(bladeColor);
		g.fillRect(intX, intY, bladeWidth, bladeHeight);
	}

	private void renderDropped(Graphics g) {
		int intX = (int)x-width/2;
		int intY = (int)y-handleWidth;
		
		g.setColor(handleColor);
		g.fillRect(intX, intY, handleHeight, handleWidth);
		
		if(!pointLeft) intX+=handleHeight-bladeHeight;
		intY -= bladeWidth - handleWidth;
		
		g.setColor(bladeColor);
		g.fillRect(intX, intY, bladeHeight, bladeWidth);
	}

	@Override
	public void use() {
		
	}

	
}
