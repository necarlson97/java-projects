package com.version_2.main;

import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Handler {

	LinkedList<WorldObject> object = new LinkedList<WorldObject>();
	
	
	public void tick(){
		for(int i = 0; i < object.size(); i++){
			WorldObject tempObject = object.get(i);
			tempObject.tick();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < object.size(); i++){
			WorldObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(WorldObject object){
		this.object.add(object);
	}
	
	public void removeObject(WorldObject object){
		this.object.remove(object);
	}
	
}
