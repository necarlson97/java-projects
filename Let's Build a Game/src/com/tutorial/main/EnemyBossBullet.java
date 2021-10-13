package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBossBullet extends GameObject{
	
	private Handler handler;
	Random r = new Random();

	public EnemyBossBullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = (r.nextInt(5 - -5) + -5);
		velY = 5;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT-32) handler.removeObject(this);;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.orange, 16, 16, 0.03f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
