import java.awt.Color;
import java.awt.Graphics;

public class Bot extends Operator{
	
	Net net;
	
	Bot() {
		net = new Net(5, 2, 20, 4, new Point(0, font.getSize()*5));
	}
	
	Bot(Bot b) {
		net = new Net(b.net);
		success = b.success;
	}
	
	void update() {
		net.activate(whiskerIntersects());
		super.update();
	}
	
	float[] whiskerIntersects() {
		float[] whisk = new float[car.whiskers.length];
		for(int i=0; i<whisk.length; i++) {
			whisk[i] = car.whiskers[i].intersection;
		}
		return whisk;
	}

	@Override
	void getInputs() {
		float[] inputs = net.getOutput();
		left = inputs[0] > .5;
		right = inputs[1] > .5;
		up = inputs[2] > .5;
		down = inputs[3] > .5;
	}

	@Override
	void renderOperator(Graphics g) {
		super.renderOperator(g);
		g.setColor(Color.black);
		g.fillRect(net.x, net.y, net.width, net.height);
		net.render(g);
	}

}
