import java.awt.Color;
import java.awt.Graphics;

public class Ship {
	
	int width;
	int height;
	
	int x;
	int y;
	
	Color color = Color.white;
	
	boolean left;
	boolean right;
	
	ShapeInvaders shapeInvaders;
	
	boolean alive = true;
	
	int nextBulletIndex = 0; 
	Bullet[] bullets = new Bullet[3];
	
	public Ship(int w, int h, int startingX, int startingY, Color c, ShapeInvaders s) {
		width = w;
		height = h;
		x = startingX;
		y = startingY;
		color = c;
		shapeInvaders = s;
	}
	
	public void kill() {
		color = Color.gray;
		alive = false;
	}
	
	public void run() {
		if(left) x -= 4;
		if(right) x += 4;
		
		for(int i=0; i<bullets.length; i++) {
			Bullet b = bullets[i]; 
			if(b != null) {
				if(b.y < 0-b.height || b.y > shapeInvaders.windowHeight+b.height) {
					removeBullet(i);
				}
				else b.run();
			}
		}
	}
	
	public void removeBullet(int index) {
		bullets[index] = null;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x-width/2, y, width, height);
		
		for(int i=0; i<bullets.length; i++) {
			if(bullets[i] != null) bullets[i].render(g);
		}
	}	

}
