import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player extends Operator {
	

	@Override
	void getInputs() {
	}
	
	@Override
	void renderOperator(Graphics g) {
		super.renderOperator(g);
		if(car == null) return;
		g.setColor(Color.black);
		int y = 100;
		g.fillRect(0, y, Net.nodePixels, Net.nodePixels*car.whiskers.length);
		for(int i=0; i<car.whiskers.length; i++) {
			g.setColor(Node.valueColor(car.whiskers[i].intersection));
			g.fillOval(0, y, Net.nodePixels, Net.nodePixels);
			g.setColor(Color.white);
			g.drawOval(0, y, Net.nodePixels, Net.nodePixels);
			y += Net.nodePixels;
		}
	}

}
