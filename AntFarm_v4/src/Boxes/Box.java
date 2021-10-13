package Boxes;

import java.awt.Color;
import java.awt.Graphics;

import Main.AntFarm;

public abstract class Box {
	
	int row;
	int col;
	
	Color color;
	
	int boxSize = AntFarm.boxSize;
	
	public Box(int row, int col, Color color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	public void run(){
		
	}
	
	public void render(Graphics g){
		
		g.setColor(color);
		g.fillRect(col*boxSize, row*boxSize, boxSize, boxSize);
	}
	
	public void renderDebug(Graphics g){
		g.setColor(Color.white);
		g.drawRect(col*boxSize, row*boxSize, boxSize, boxSize);
	}

}
