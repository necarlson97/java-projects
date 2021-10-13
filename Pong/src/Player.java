import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	// Some variables for our paddle positions
	// Our x need only be an int because it will never change!
	float y;
	int x;
	
	
	// Some varables for how wide and tall our paddles are
	int width = 30;
	int height = 120;
	
	Pong pong;
	
	boolean up;
	boolean down;
	
	float speed = 5;
	
	boolean leftPlayer;
	
	public Player(boolean left, Pong p) {
		pong = p;
		y = pong.windowHeight/2;
		
		leftPlayer = left;
		
		if(leftPlayer) x = (int) (pong.windowWidth * .1);
		else x = (int) (pong.windowWidth * .9);
	}
	
	public void update() {
		
		checkBall();
		
		if(up && y-speed > 0) y -= speed;
		else if(down && y+speed < pong.windowHeight) y += speed;
	}
	
	public void checkBall() {
		// First, im setting b to pong.ball so I dont have 
		// to type pong.ball a million times
		Ball b = pong.ball;
		
		// Then I check to see if the balls position is inside our paddle
		// That is, if its x is between my x and x+width
		// and its y is between my y and y+height
		if(b.x > x-width/2 && b.x < x+width/2 && 
				b.y > y-height/2 && b.y < y+height/2) 
			reverseBall();
	}
	
	public void reverseBall() {
		// If it is, we reverse the ball's horizontal velocity
		pong.ball.xVel *= -1;
		
		// And we set its x to the outside border of our paddle
		// We need to remember to account for if it is on the
		// left or right
		if(leftPlayer) pong.ball.x = x+width;
		else pong.ball.x = x-pong.ball.size;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x-width/2, (int)y-height/2, width, height);
	}

}
