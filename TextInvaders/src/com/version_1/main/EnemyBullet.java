package com.version_1.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBullet extends GameObject {
	
	Random r = new Random();
	Handler handler;
	
	public EnemyBullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velY = 5;
	}


	public void tick() {
		y += velY;
		
		// Removes bullet when it reaches bottom of screen
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.EnemyBullet){
				if(tempObject.getY() == 500){
					handler.removeObject(tempObject);
				}
			}
		}
		
		
		
	}

	public void render(Graphics g) {		
		Font fnt = new Font("press start 2p", 1, 14);
		
		g.setFont(fnt);
		g.setColor(new Color(0, 225, 100));	
		g.drawString("o",(int)x-1, (int)y+12);
		
		//g.drawRect((int)x, (int)y, 10, 10);
	}


	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 10, 10);
	}
	
	


}
