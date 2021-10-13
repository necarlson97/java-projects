import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Ship{

	int index;
	int delay = 20;
	
	public Enemy(int i, int startingX, int startingY, ShapeInvaders s) {
		super(30, 30, startingX, startingY, Color.red, s);
		
		index = i;
	}
	
	public void run() {
		if(x+width > Game.windowWidth) shapeInvaders.enemiesRight = false;
		if(x-width < 0) shapeInvaders.enemiesRight = true;
		
		if(Game.count % delay == 0) {
			if(shapeInvaders.enemiesRight) right = true;
			else left = true;
		}
		else {
			right = false;
			left = false;
		}
		super.run();
	}

}
