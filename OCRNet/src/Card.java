import java.awt.Color;
import java.awt.Graphics;

public class Card {
	
	float[] inputs;
	float[] outputs;
	int lable;
	
	int startingX = 20;
	int startingY = 80;
	
	Card(float[] inputs, int lable) {
		this.inputs = inputs;
		this.lable = lable;
		outputs = new float[10];
		outputs[lable] = 1;
	}
	
	void render(Graphics g) {
		int r = 0;
		int c = 0;
		int i=0;
		while(c < Driver.cardSize) {
			r = 0;
			while(r < Driver.cardSize) {
				int x = startingX + c*Driver.pixelSize;
				int y = startingY + r*Driver.pixelSize;
				renderNeuron(g, x, y, inputs[i]);
				r++;
				i++;
			}
			c++;
		}
	}
	
	void renderNeuron(Graphics g, int x, int y, float c) {
		g.setColor(new Color(c, c, c));
		g.fillOval(x, y, Driver.pixelSize, Driver.pixelSize);
		g.setColor(Color.white);
		g.drawOval(x, y, Driver.pixelSize, Driver.pixelSize);
	}
	

}
