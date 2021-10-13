package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter{
	
	private Game game;
	private Handler handler;
	private Random r = new Random();
	
	public Menu(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
	}
	
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx,my,210,150,200,50) ){
			game.gameState = STATE.Game;
			
			handler.addObject(new Player(Game.WIDTH/2 -32, Game.HEIGHT/2 -32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
		}
		if(mouseOver(mx,my,210,250,200,50) ){
			game.gameState = STATE.Help;
		}
		if(mouseOver(mx,my,240, 365, 100, 50) && game.gameState == STATE.Help){
			game.gameState = STATE.Menu;
			return;
		}
		if(mouseOver(mx,my,210,350,200,50) ){
			System.exit(1);
			
		}
		
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx > x && mx < x + width){
			if(my > y && my < y + height){
				return true;
			}else return false;		
		}else return false;
	}


	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(game.gameState == STATE.Menu){
			Font fnt1 = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			
			
			g.setColor(Color.white);
			
			g.drawRect(220, 150, 200, 50);
			g.drawRect(220, 250, 200, 50);
			g.drawRect(220, 350, 200, 50);
			
			g.setFont(fnt1);
			g.drawString("Menu", 250, 60);
			
			g.setFont(fnt2);
			g.drawString("Yes",280, 190);
			g.drawString("Hm?",280, 290);
			g.drawString("Quite",280, 390);
		}
		else if(game.gameState == STATE.Help){
			Font fnt1 = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);
			
			g.setColor(Color.white);
			
			g.setFont(fnt1);
			g.drawString("Hm?",250, 60);
			
			g.setFont(fnt3);
			g.drawString("Figure it out.",250, 200);
			
			g.setFont(fnt2);
			g.drawString("Back",250, 400);
			g.drawRect(240, 365, 100, 50);
		}
		
		
		
	}

}
