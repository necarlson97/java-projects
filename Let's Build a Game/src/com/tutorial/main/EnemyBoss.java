package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBoss extends GameObject{
	
	private Handler handler;
	Random r = new Random();
	
	private int timer[] = new int[2];
	

	public EnemyBoss(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 0;
		velY = 1;
		
		timer[0]= 50;
		timer[1]= 50;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(timer[0] <= 0) velY = 0;
		else timer[0] --;
		
		if(timer[0] <=0) timer[1] --;
		if(timer[1] <=0){
			if(velX ==0) velX = 2;
			int spawn = r.nextInt(20);
			if(spawn == 0) handler.addObject(new EnemyBossBullet(x+64, y+64, ID.BasicEnemy, handler));
		}
		
		
		if(x <= 0 || x >= Game.WIDTH-128) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 128, 128, 0.08f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 128, 128);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 128, 128);
	}

}
