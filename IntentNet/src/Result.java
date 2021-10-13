import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Result {
	
	String type;
	float conf = 0;
	
	Font font = new Font("Arial", 0, 20);
	
	Result(float[] output) {
		
		int confI = 0;
		for(int i=0; i<output.length; i++) {
			if(output[i] > conf) {
				conf = output[i];
				confI = i;
			}
		}
		
		type = Stripper.outBag[confI];
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(font);
		
		g.drawString(toString(), 500, 50);
	
	}
	
	public String toString() {
		String confStr = conf+"";
		if(confStr.length() > 5) confStr = confStr.substring(0, 5);
		String str = "Type: "+type+" Confadence: "+confStr; 
		return str;
	}

}
