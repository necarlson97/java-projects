package com.kinselection.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	public KeyInput(Handler handler){
		this.handler = handler;
	
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
			
		if(key == KeyEvent.VK_RIGHT){
			World.yearHes +=50;
			World.yearHes = (int) World.clamp(World.yearHes,0,500);
		}
		if(key == KeyEvent.VK_LEFT){
			World.yearHes -=50;
			World.yearHes = (int) World.clamp(World.yearHes,0,500);
		}
			
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}

