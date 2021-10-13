package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject{
	
	private Handler handler;
	private GameObject player;

	public SmartEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		for( int i = 0; i < handler.object.size(); i++){
			if(handler.object.get(i).getId() == ID.Player) player = handler.object.get(i);
		}
		
	}

	public void tick() {
		x += velX;
		y += velY;
		
		float difX = x - player.getX() - 8;
		float difY = y - player.getY() - 8;
		float distance = (float) Math.sqrt( (x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()) );
		
		velX = (float) ((-1.0/distance) * difX);
		velY = (float) ((-1.0/distance) * difY);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.03f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
