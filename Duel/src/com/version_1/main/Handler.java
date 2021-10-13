package com.version_1.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<StageObject> object = new LinkedList<StageObject>();
	
	public void tick(){
		for(int i = 0; i < object.size(); i++){
			StageObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < object.size(); i++){
			StageObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(StageObject object){
		this.object.add(object);
	}
	
	public void removeObject(StageObject object){
		this.object.remove(object);
	}
	
}
