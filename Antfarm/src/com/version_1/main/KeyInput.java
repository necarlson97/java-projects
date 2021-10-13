package com.version_1.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
			
		if(key == KeyEvent.VK_RIGHT){
			
		}
		if(key == KeyEvent.VK_LEFT){
			
		}
			
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}

