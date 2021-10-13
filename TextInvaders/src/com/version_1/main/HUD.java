package com.version_1.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static int LIVES = 3;

	
	private int score = 0;
	private int level = 0;
	
	private Handler handler;

	public void tick(){
			
			LIVES = (int) Game.clamp(LIVES, 0, 5);
			
		}
		
		public void render(Graphics g){
			g.setColor(Color.white);
			g.drawString("Score: " + score, 10, 20);
			g.drawString("Level: " + level, 10, 40);
		}
		
		public void score(int score){
			this.score = score;
		}
		
		public int getScore(){
			return score;
		}
		
		public int getLevel(){
			return level;
		}
		
		public void setLevel(int level){
			this.level = level;
		}
}
