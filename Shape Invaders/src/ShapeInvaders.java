import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class ShapeInvaders extends Game {
	
	Player player;
	Enemy[] enemies = new Enemy[84];
	boolean enemiesRight = true;

	@Override
	void handleScreenResize() {
		
	}

	@Override
	void setup() {
		
		player = new Player(this);
		fillEnemies();
		
	}

	private void fillEnemies() {
		
		enemies[0] = new Enemy(0, 0, 0, this);
		int spaceBetween = enemies[0].width/2;
		int intX = spaceBetween;
		
		int intY = (int) (windowHeight * .1);
		
		for(int i=0; i<enemies.length; i++) {
			enemies[i] = new Enemy(i, intX, intY, this);
			intX += spaceBetween + enemies[i].width;
			
			if(intX > windowWidth - spaceBetween - enemies[i].width) {
				intX = spaceBetween;
				intY += spaceBetween + enemies[i].height;
			}
		}
	}

	@Override
	void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.left = true;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.right = true;
		
		if(key == KeyEvent.VK_SPACE) player.shoot();
	}

	@Override
	void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.left = false;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.right = false;
	}

	@Override
	void runGame() {
		
		player.run();
		for(Enemy e : enemies) {
			if(e != null) e.run();
		}
		

		
	}

	@Override
	void renderGame(Graphics2D g) {
		
		player.render(g);
		for(Enemy e : enemies) {
			if(e != null) e.render(g);
		}
	}
	
	public static void main(String[] args) {
		new ShapeInvaders();
	}

}
