import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Wall extends Engine2D {

	
	// This is the ball sitting on the ground
	int ballx = 900;
	int bally = 650;
	int ballsize = 100;
	
	int wallx = 250;
	int wally = 200;
	
	int wallwidth = 90;
	int wallheight = 1000;
	
	int velocityx = 0;
	int velocityy = 0;
	
	int groundy = 750;

	Image the_knight;
	Image bg;
	Image fireworks;
	Image fire;
	
	int knightx = 50;
	int knighty = 200;
	
	int times_hit = 0;
	
	int balls_used = 0;
	
	@Override
	public void setup() {
		the_knight = new ImageIcon("dude.gif").getImage();
		bg = new ImageIcon("castle.png").getImage();
		fireworks = new ImageIcon("fireworks.jpg").getImage();
		fire = new ImageIcon("fire.jpg").getImage();
	}

	@Override
	public void update() {
		
		ballx = ballx + velocityx;
		bally = bally + velocityy;
		
		// This is the gravity!
		if(bally < 650) velocityy = velocityy + 1;
		
		
		// hitting the wall
		if(hitSomething(wallx, wally, wallwidth, wallheight)) {
			ballx = 900;
			bally = 650;
			
			velocityx = 0;
			velocityy = 0;
			
			balls_used = balls_used + 1;
		}
		
		
		// hitting the knight
		if(hitSomething(knightx, knighty, 200, 200)) {
			ballx = 900;
			bally = 650;
			
			velocityx = 0;
			velocityy = 0;
			
			// move the knight down
			knighty += 100;
			
			times_hit = times_hit + 1;
			balls_used = balls_used + 1;
		}
		
		// reset ball if it hits the ground
		if(bally > 650) {
			ballx = 900;
			bally = 650;
			
			velocityx = 0;
			velocityy = 0;
			
			balls_used = balls_used + 1;
		}
	
	}
	
	boolean hitSomething(int x, int y, int width, int height) {
		return (ballx < x + width && ballx > x &&
				bally < y + height && bally > y);
	}

	@Override
	void render(Graphics2D g) {
		
		// If the knight was hit 5 times, you win
		if(times_hit == 5) {
			g.setColor(Color.green);
			g.setFont(new Font("Press Start 2p", 0, 60));
			
			g.drawImage(fireworks, 0, 0, windowWidth, windowHeight, null);
			g.drawString("YOU WIN", 300, 120);
			
		}
		// If the player used up all their balls, you loose
		else if(balls_used > 8) {
			g.setColor(Color.red);
			g.setFont(new Font("Press Start 2p", 0, 60));
			
			g.drawImage(fire, 0, 0, windowWidth, windowHeight, null);
			g.drawString("YOU LOSE", 300, 120);
			
		} else {
			// This is our default background 
			g.drawImage(bg, 0, 0, windowWidth, windowHeight, null);
		}
		
		// This draws the wall     
		g.setColor(Color.gray);
		g.fillRect(wallx, wally, wallwidth, wallheight);
		
		// This draws the green grass
		g.setColor(Color.green);
		g.fillRect(0, 750, 1000, 100);
		
		// This draws our ball
		g.setColor(Color.red);
		g.fillOval(ballx-ballsize/2, bally-ballsize/2, ballsize, ballsize);
		
		//Varable Name, x,  y, width, height, null
		g.drawImage(the_knight, knightx, knighty, 200, 200, null);
		
		
		
		// This draws the score box     
		g.setColor(Color.red);
		g.fillRect(0, 0, 430, 40);
		// This draws the score counter
		g.setColor(Color.orange);
		g.setFont(new Font("Press Start 2p", 0, 30));
		g.drawString("Balls Left: "+(9 - balls_used), 10, 35);
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		// If the player presses 'space', then our ball jumps up and moves left
		// balls_used
		// 8 or 9ish
		if(keyCode == KeyEvent.VK_SPACE && balls_used < 9) {
			velocityx = -10;
			velocityy = -20;
		}
		
		// If the player presses 'r', then we reset everything
		if(keyCode == KeyEvent.VK_R) {
			// Ball goes back to start, and stops moving
			ballx = 900;
			bally = 650;
			// Put the dude back where he starts
			knightx = 50;
			knighty = 200;
			// Resetting the score to 0
			times_hit = 0;
			// Resetting the balls used to 0
			balls_used = 0;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Wall();
	}
	
}
