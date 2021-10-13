package com.version_1.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class WorkerAnt extends WorldObject{
	
	Random r = new Random();
	Handler handler;
	Handler blockHandler;
	Color antGray = new Color(210,210,210);
	

	public WorkerAnt(int x, int y, Handler handler, Handler blockHandler) {
		super(x, y);
		this.handler = handler;
		this.blockHandler = blockHandler;
			
	}

	public void tick() {
		
		// Gravity
		for(int i=0; i < blockHandler.object.size(); i++){
			if( this.getBounds().intersects(blockHandler.object.get(i).getBounds()) ) {
				y+=10;
			}
			else y-=1;
			
		}
		
		
		
	}

	public void render(Graphics g) {
			
		g.setColor(Color.black);
		g.fillRect(x, y+1, 5, 3);
		
	}


}
