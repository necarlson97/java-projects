package com.version_2.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	private int x,y;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
			
		if(key == KeyEvent.VK_RIGHT){
			x-=1;
		}
		if(key == KeyEvent.VK_LEFT){
			x+=1;
		}
		if(key == KeyEvent.VK_UP){
			y-=1;
		}
		if(key == KeyEvent.VK_DOWN){
			y+=1;
		}
		if(key == KeyEvent.VK_F){
			for(int y=0; y <200; y++){
				for(int x=0; x<200; x++){
					World.pixels[x+y*200] = 0xFF66FF;	
				}
			}
		}
			
		World.pixels[x+y*100] = 0x00FF00;
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
	}

}

