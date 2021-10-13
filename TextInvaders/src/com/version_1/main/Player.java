package com.version_1.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {
	
	Random r = new Random();
	Handler handler;
	
	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
	}


	public void tick() {
		x += velX;
		
		x = Game.clamp(x, 0, Game.WIDTH-40);
		
		
		collsion();
		
	}
	
	private void collsion(){
		
		//If player gets hit by bullet, looses life and removes bullet
		for(int i = 0; i < handler.object.size(); i++){
			GameObject bulletObject = handler.object.get(i);
			
			if(bulletObject.getId() == ID.EnemyBullet){
				if(getBounds().intersects(bulletObject.getBounds())){
					HUD.LIVES -= 1;
					handler.removeObject(bulletObject);
				}
			}
		}
	}

	public void render(Graphics g) {		
		Font fnt = new Font("press start 2p", 1, 14);
		
		g.setFont(fnt);
		g.setColor(Color.white);	
		g.drawString("aAa",(int)x, (int)y+20);
	}


	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 40, 20);
	}
	
	


}
