import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	
	// Some floats for the balls position on screen
	float x;
	float y;
	
	// Some floats for how the ball will move each frame
	float xVel = -5;
	float yVel = -5;
	
	// The number of pixels wide and high to draw the ball
	int size = 30; 
	
	Pong pong;
	
	public Ball(Pong p) {
		pong = p;
		x = pong.windowWidth/2;
		y = pong.windowHeight/2;
	}
	
	public void update() {
		
		checkWalls();
		
		x+=xVel;
		y+=yVel;
	}
	
	public void checkWalls() {
		float nextFrameX = x+xVel;
		float nextFrameY = y+yVel;
		
		// If the next frame, the ball would go out of the window left
		// then give the left player a point
		// and if it leaves right, give right player a point
		if(nextFrameX < 0) pong.pointFor(true);
		else if(nextFrameX+size > pong.windowWidth+size) pong.pointFor(false);
		
		// If the next frame, the ball would hit the ceiling or floor,
		// simply reverse the y velocity
		if(nextFrameY < 0 || nextFrameY+size > pong.windowHeight) yVel *= -1;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x-size/2, (int)y-size/2, size, size);
	}

}
