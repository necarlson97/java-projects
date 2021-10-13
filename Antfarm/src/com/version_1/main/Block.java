package com.version_1.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Block extends WorldObject{
		
	Random r = new Random();
	Handler handler;
	Handler blockHandler;
	
	int index, type;


	public Block(int x, int y, int index, Handler Handler, Handler blockHandler){
		super(x,y);
		this.handler = handler;
		this.blockHandler = Handler;
		this.index = index;
	}

	public void tick() {
		
		
	}

	public void render(Graphics g) {
		if(type == 0){
			g.setColor(new Color(50,10,10));
		}
		if(type == 1){
			g.setColor(Color.red);
		}
		
		g.fillRect(x, y, 10, 10);
		
	}


}

