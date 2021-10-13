import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class Particle extends GameObject{
	
	int lifeSpan;
	int life;
	
	int index;
	GameObject parent;
	
	public Particle(int x, int y, int lifeSpan, int size, int i, GameObject parent) {
		super(x, y, size, size, i);
		this.lifeSpan = lifeSpan;
		life = lifeSpan;
		
		this.index = i;
		this.parent = parent;
	}
	
	public void run() {
		//super.run();
		x += xVel;
		y += yVel;
		if(life > 0) life--;
		else if(parent != null) parent.particles[index] = null;
	}
	
	public abstract void render(Graphics g);
	
	public void use() {
		
	}

}
