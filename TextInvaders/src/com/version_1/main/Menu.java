package com.version_1.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Menu extends KeyAdapter{
	
	private Game game;
	private Handler handler;
	private Random r = new Random();
	
	public Menu(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(game.gameState == STATE.Menu){
			GameObject tempObject = handler.object.getFirst();
		
		
			if(key == KeyEvent.VK_W){
				tempObject.setY(220);
			}
			if(key == KeyEvent.VK_S){
				tempObject.setY(270);
			}
			if(key == KeyEvent.VK_ENTER){
				if(tempObject.getY() == 220){
					game.gameState = STATE.Game;
				}
				if(tempObject.getY() == 270){
					System.exit(1);
				}
			}
		}	
	}
	
	public void keyReleased(KeyEvent e){
		
	}


	public void tick(){
		
		
	}
	
	public void render(Graphics g){
		Font fnt1 = new Font("press start 2p", 1, 30);
		Font fnt2 = new Font("press start 2p", 1, 20);
		
		
		g.setColor(Color.white);
		
		g.setFont(fnt1);
		g.drawString("Text Invaders", 50, 50);
		
		g.setFont(fnt2);
		g.drawString("Start",150, 240);
		g.drawString("Exit",150, 290);
		
		
		
	}

}
