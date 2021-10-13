package com.version_2.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class WorldObject {

	protected float x, y;
	
	public  WorldObject(float x, float y){
		this.x = x;
		this.y = y;

	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(int x){
		this.x = x;
	}
	
	public float getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public float getY(){
		return y;
	}

	
	public Rectangle getBounds(){
		
		return new Rectangle((int) x,(int) y,5,5);
	}
	
	
}