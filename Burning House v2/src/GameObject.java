import java.awt.Graphics;
import java.util.Random;

public abstract class GameObject {
	
	Random r = Game.r;
	
	boolean held;
	
	float x;
	float y;
	
	int width;
	int height;
	
	float xVel;
	
	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void run();
	
	public abstract void render(Graphics g);
	
	public abstract void use();

}
