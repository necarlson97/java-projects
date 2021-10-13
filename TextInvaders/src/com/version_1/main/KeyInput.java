package com.version_1.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.version_1.main.ID;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	private Game game;
	private boolean[] keyDown = new boolean[2];
	
	public KeyInput(Handler handler, Game game){
		this.handler = handler;
		this.game = game;
		
		keyDown[0]=false;
		keyDown[1]=false;
	}
	
	public void keyPressed(KeyEvent e){
		//For in-game left/right movement
		
		int key = e.getKeyCode();
		if(game.gameState == STATE.Game){
			for(int i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getId() == ID.Player){
					if(key == KeyEvent.VK_A){
						tempObject.setVelX(-5);
						keyDown[0]=true;
					}
					if(key == KeyEvent.VK_D){
						tempObject.setVelX(5);
						keyDown[1]=true;
					}
					if(key == KeyEvent.VK_SPACE){
						//Limits player bullets on screen
						int bulletNumb = 0;
						for(int j = 0; j < handler.object.size(); j++){
							if(handler.object.get(j).getId() == ID.PlayerBullet) bulletNumb += 1;
						}
						if(bulletNumb < 2) handler.addObject(new PlayerBullet(tempObject.getX()+15,tempObject.getY(),ID.PlayerBullet, handler));
					}
				}
			}
			
		}
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(game.gameState == STATE.Game){
			for(int i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getId() == ID.Player){
					if(key == KeyEvent.VK_A) keyDown[0]=false;
					if(key == KeyEvent.VK_D) keyDown[1]=false;
					
					if(!keyDown[0] && !keyDown[1]) tempObject.setVelX(0);
				}
			}
		}
	}

}
