package com.version_1.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class WorldObject {

	protected int x, y,type;
	
	public  WorldObject(int x, int y){
		this.x = x;
		this.y = y;

	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return y;
	}

	public void setType(int i) {
		this.type = type;
		
	}
	
	public Rectangle getBounds(){
		
		return new Rectangle(x,y,5,5);
	}
	
	
}