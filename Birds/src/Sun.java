import java.awt.Color;
import java.awt.Graphics;

public class Sun {
	
	float x = Birds.windowWidth-200;
	float y;
	
	int width = 100;
	int height = 100;
	
	float daySpeed = (float).3;
//	float daySpeed = 5;
	
	int dayCount;
	
	boolean sunSet = true;
	
	public Sun(){
		
	}
	
	public void run(int c){
		dayCount = c;
		if(y>Birds.windowHeight && sunSet) sunSet = false;
		if(y<-Birds.windowHeight && !sunSet) sunSet = true;
		
		if(sunSet) y += daySpeed;
		if(!sunSet) y -= daySpeed;
	}
	
	public void render(Graphics g){
		if(y/Birds.windowHeight > .5) {
			renderNight(g);
			return;
		}
		if((y/Birds.windowHeight)<-.1){
			renderDay(g);
			return;
		}
		
		int intX = (int)x;
		int intY = (int)y;
		
		float skyR = (float).3;
		float skyG = (float).01;
		float skyB = (float)(.9-(y/Birds.windowHeight));
		
		float sunR = (float) .9;
		float sunG = (float)(.9-(y/Birds.windowHeight));
		float sunB = (float)0;
		
		g.setColor(new Color(skyR, skyG, skyB));
		g.fillRect(0, 0, Birds.windowWidth, Birds.windowHeight);
		
		
		g.setColor(new Color(sunR, sunG, sunB));
		g.fillRect(intX, intY, width, height);
		
	}

	private void renderDay(Graphics g) {
		g.setColor(new Color((float).3, (float).4, (float)1));
		g.fillRect(0, 0, Birds.windowWidth, Birds.windowHeight);
	}

	private void renderNight(Graphics g) {
		g.setColor(new Color((float).1, (float).0, (float).2));
		g.fillRect(0, 0, Birds.windowWidth, Birds.windowHeight);
	}

}
