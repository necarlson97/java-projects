import java.awt.Color;
import java.awt.Graphics;

public class Sky {
	
	float smoke = 0;
	Color color;
	
	int groundY;
	int height;
	
	public Sky(int groundY) {
		this.groundY = groundY;
		height = groundY - (Driver.margin * 2);
	}
	
	public void run() {
		if(smoke < .22) {
			float gray = (float) (.78+(.22 - smoke));
			color = new Color((float).78, gray, gray);
		}
		else {
			float gray = 1 - smoke;
			color = new Color(gray, gray, gray);
		}
	}
	
	public void addSmoke() {
		if(smoke < 1) {
			smoke += .0001;
			if(smoke > 1) smoke = 1;
		}
		
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillRect(0, 0, Game.windowWidth, height);
	}

}
