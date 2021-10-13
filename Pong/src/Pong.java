import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Pong extends Engine2D{
	
	Ball ball;
	Player leftPlayer;
	Player rightPlayer;
	
	int leftScore;
	int rightScore;
	
	Font scoreFont;
	
	public static void main(String[] args) {
		new Pong();
	}

	@Override
	public void setup() {
		ball = new Ball(this);
		leftPlayer = new Player(true, this);
		rightPlayer = new Player(false, this);
		
		scoreFont = new Font("Press Start 2p", 0, 60);
	}

	@Override
	public void update() {
		ball.update();
		leftPlayer.update();
		rightPlayer.update();
	}

	@Override
	void render(Graphics2D g) {
		renderScore(g);
		
		ball.render(g);
		leftPlayer.render(g);
		rightPlayer.render(g);
	}
	
	void renderScore(Graphics g) {
		int scoreY = (int) (windowHeight * .1);
		int leftScoreX = (int) (windowHeight * .4);
		int rightScoreX = (int) (windowHeight * .7 );
		
		g.setColor(Color.white);
		g.setFont(scoreFont);
		g.drawString(""+leftScore, leftScoreX, scoreY);
		g.drawString(""+rightScore, rightScoreX, scoreY);
	}
	
	void pointFor(boolean leftPlayer) {
		ball = new Ball(this);
		if(leftPlayer) leftScore++;
		else rightScore++;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		// If they press escape, we can use System.exit(0);
		// to kill the program and quit everything
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		
		
		// And here we tell our players if they need to move up and down
		// based on the W/S keys and Up/Down arrows
		if(key == KeyEvent.VK_W) leftPlayer.up = true;
		if(key == KeyEvent.VK_S) leftPlayer.down = true;
		
		if(key == KeyEvent.VK_UP) rightPlayer.up = true;
		if(key == KeyEvent.VK_DOWN) rightPlayer.down = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		// We need to remember to tell the players to stop moving
		// when someone lets go of a key
		
		if(key == KeyEvent.VK_W) leftPlayer.up = false;
		if(key == KeyEvent.VK_S) leftPlayer.down = false;
		
		if(key == KeyEvent.VK_UP) rightPlayer.up = false;
		if(key == KeyEvent.VK_DOWN) rightPlayer.down = false;
	}

}
