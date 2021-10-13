import java.awt.Color;
import java.awt.Graphics;

public class Setting {
		
	Color groundColor = new Color(200, 200, 200);
	Color skyColor = new Color(20, 20, 20);
	
	Setting() {
	}
	
	void update() {
		
	}
	
	void render(Graphics g) {
		int groundY = (int) (Display.windowHeight/2 + Display.cameraLocation().y);
		g.setColor(groundColor);
		g.fillRect(0, groundY, Display.windowWidth, Display.windowHeight);
		g.setColor(skyColor);
		g.fillRect(0, 0, Display.windowWidth, groundY);
		
		g.setColor(Color.black);
		for(float w=-Display.cameraLocation().x%Display.pixelPerMeter; w<Display.windowWidth; w+=Display.pixelPerMeter) {
			g.drawLine((int)w, 0, (int)w, Display.windowHeight);
		}
		g.setColor(Color.black);
		for(float h=-Display.cameraLocation().y%Display.pixelPerMeter; h<Display.windowHeight; h+=Display.pixelPerMeter) {
			g.drawLine(0, (int)h, Display.windowWidth, (int)h);
		}
	}

}
