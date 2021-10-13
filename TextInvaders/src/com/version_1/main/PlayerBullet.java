package com.version_1.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class PlayerBullet extends GameObject {
	
	Random r = new Random();
	Handler handler;
	
	public PlayerBullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velY = -5;
	}


	public void tick() {
		y += velY;
		
		// Removes bullet when it reaches top of screen
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.PlayerBullet){
				if(tempObject.getY() == 55){
					handler.removeObject(tempObject);
				}
			}
		}
		
		
		
	}

	public void render(Graphics g) {		
		Font fnt = new Font("press start 2p", 1, 14);
		
		g.setFont(fnt);
		g.setColor(Color.blue);	
		g.drawString("|",(int)x-4, (int)y+16);
		
		//g.drawRect((int)x, (int)y, 5, 15);
	}


	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 5, 15);
	}
	
	


}
