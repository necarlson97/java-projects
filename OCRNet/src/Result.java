import java.awt.Color;
import java.awt.Graphics;

public class Result {
	
	int digit;
	float conf;
	
	Result(float[] output) {
		for(int i=0; i<10; i++) {
			if(output[i] > conf) {
				digit = i;
				conf = output[i];
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Guessing "+digit+" with "+conf+" assuredness", 700, 500);
	}

}
