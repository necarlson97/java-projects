import java.awt.Color;
import java.awt.Graphics;

public abstract class Bullet {
	
	int width = 5;
	int height = 20;
	
	int x;
	int y;
	
	int yVel;
	
	Color color = Color.white;
	
	Ship ship;
	
	boolean killsPlayer;
	boolean killsEnemies;
	
	public Bullet(int startingYVel, Ship s) {
		ship = s;
		x = s.x;
		y = s.y;
		yVel = startingYVel;
		color = s.color;
	}
	
	public void run() {
		y += yVel;
		
		checkCollisions();
	}
	
	public void checkCollisions() {
		if(killsPlayer) checkPlayer();
		if(killsEnemies) checkEnemies();
	}
	
	private void checkPlayer() {
		Player p = ship.shapeInvaders.player;
		if(p != null && x> p.x-p.width/2 && x < p.x+p.width/2 && y > p.y && y < p.y+height) {
			p.kill();
		}
	}

	private void checkEnemies() {
		for(Enemy e : ship.shapeInvaders.enemies) {
			if(e != null && x> e.x-e.width/2 && x < e.x+e.width/2 && y > e.y && y < e.y+height) {
				e.kill();
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.drawRect(x-width/2, y, width, height);
	}

}
