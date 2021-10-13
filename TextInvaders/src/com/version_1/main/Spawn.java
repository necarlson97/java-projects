package com.version_1.main;

import java.util.Random;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 10;
	
	public Spawn(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
	}

	public void tick(){
		if(hud.getLevel() == 0){
			GameObject playerObject = handler.object.getFirst();
			playerObject.setX(250);
			playerObject.setY(400);
			
			for(int i = 0; i < 15; i++){
				
				handler.addObject(new Enemy(50+(i*20),50,ID.Enemy, handler,hud,"T"));
			}
			for(int i = 0; i < 15; i++){
				handler.addObject(new Enemy(50+(i*20),70,ID.Enemy, handler,hud,"Y"));
			}
			for(int i = 0; i < 15; i++){
				handler.addObject(new Enemy(50+(i*20),90,ID.Enemy, handler,hud,"Y"));
			}
			for(int i = 0; i < 15; i++){
				handler.addObject(new Enemy(50+(i*20),110,ID.Enemy, handler,hud,"V"));
			}
			for(int i = 0; i < 15; i++){
				handler.addObject(new Enemy(50+(i*20),130,ID.Enemy, handler,hud,"V"));
			}
			
			
			hud.setLevel(1);
		}
			
	}
}
