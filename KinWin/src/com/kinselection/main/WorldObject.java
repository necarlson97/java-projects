package com.kinselection.main;

import java.awt.Color;
import java.awt.Graphics;

public abstract class WorldObject {

	protected int x, y, gen, fert, kind, hpCap, hp, childNumb, age;
	
	public  WorldObject(int x, int y, int gen, int fert, int kind, int hpCap){
		this.x = x;
		this.y = y;
		
		this.gen = gen;
		this.fert = fert;
		this.kind = kind;
		this.hpCap = hpCap;
		
		this.hp = hp;
		this.childNumb = childNumb;
		this.age = age;
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
	
	public void setGen(int gen){
		this.gen = gen;
	}
	
	public int getGen(){
		return gen;
	}
	
	public void setFert(int fert){
		this.fert = fert;
	}
	
	public int getFert(){
		return fert;
	}
	
	public void setKind(int kind){
		this.kind = kind;
	}
	
	public int getKind(){
		return kind;
	}
	
	public void setHpCap(int hpCap){
		this.hpCap = hpCap;
	}
	
	public int getHpCap(){
		return hpCap;
	}
	
	public void setHp(int hp){
		this.hp = hp;
	}
	
	public int getHp(){
		return hp;
	}
	
	public void setChildNumb(int childNumb){
		this.childNumb = childNumb;
	}
	
	public int getChildNumb(){
		return childNumb;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public int getAge(){
		return age;
	}
	
	
}