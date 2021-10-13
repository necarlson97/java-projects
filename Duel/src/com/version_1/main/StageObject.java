package com.version_1.main;

import java.awt.Color;
import java.awt.Graphics;

public abstract class StageObject {

	protected int x, y;
	
	public  StageObject(int x, int y){
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
	
	
}