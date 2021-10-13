package com.version_1.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject{
	
	protected String type;
	
	private Handler handler;
	private HUD hud;
	private Random r;
	

	public Enemy(float x, float y, ID id, Handler handler, HUD hud, String type) {
		super(x, y, id);
		
		this.handler = handler;
		this.hud = hud;
		
		r = new Random();
		
		this.type = type;
		
		
		velX = (float) 0.2;
	}

	public void tick() {
		
		// For enemy swarm bouncing off walls and enemy speed
		int enemyNumb = 0;
		for(int j = 0; j < handler.object.size(); j++){
			if(handler.object.get(j).getId() == ID.Enemy){
				enemyNumb += 1;
			}
		}
		
		if(x > 480 || x < 20){
			this.setVelX(this.getVelX()*-1);
			this.setY(this.getY()+10);
		}
		
//		for(int i = 0; i< handler.object.size(); i++){
//			if(handler.object.get(i).getId() == ID.Enemy){
//				GameObject enemyObject = handler.object.get(i);
//				if(x < 20) enemyObject.setVelX( (100-enemyNumb)/100 );
//				else if(x > 480) enemyObject.setVelX( -1*( (100-enemyNumb)/100 ));
//				if(x < 20){
//					enemyObject.setVelX(enemyObject.getVelX()*-1);
//					enemyObject.setX(enemyObject.getX()-10);
//					enemyObject.setY((int)enemyObject.getY() + 10);
//				}
//				if(x > 480){
//					enemyObject.setVelX(enemyObject.getVelX()*-1);
//					enemyObject.setX(enemyObject.getX()-10);
//					enemyObject.setY((int)enemyObject.getY() + 10);
//				}
//			}
//		} 
		x += velX;
		
		// For enemy shooting bullets
		int bulletNumb = 0;
		for(int j = 0; j < handler.object.size(); j++){
			if(handler.object.get(j).getId() == ID.EnemyBullet) bulletNumb += 1;
		}
		if(bulletNumb < 2 && r.nextInt(2000) == 0) handler.addObject(new EnemyBullet(x,y,ID.EnemyBullet, handler));
		
		collsion();
		
		
	}
	
	private void collsion(){
		
		// If enemy gets hit by bullet, removes both the enemy and the bullet
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.PlayerBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(tempObject);
					handler.removeObject(this);
					
					hud.score(hud.getScore()+1);
					
					
					for(int j = 0; j< handler.object.size(); j++){
						if(handler.object.get(j).getId() == ID.Enemy){
							GameObject enemyObject = handler.object.get(j);
							if(enemyObject.getVelX() > 0) enemyObject.setVelX((float) ((float)enemyObject.getVelX()+.1));
							else enemyObject.setVelX((float) ((float)enemyObject.getVelX()-.1));
						}
					}
					
					
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		Color enemyT = new Color(225, 0, 0);
		Color enemyY = new Color(225, 100, 0);
		Color enemyV = new Color(225, 200, 0);
		
		Color typeColor = null;
		
		if(type == "T") typeColor = enemyT;
		if(type == "Y") typeColor = enemyY;
		if(type == "V") typeColor = enemyV;
		
		g.setColor(typeColor);
		g.drawString(type,(int)x-1, (int)y+16);
		
		//g.drawRect((int)x, (int)y, 10, 15);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 10, 15);
	}

}
