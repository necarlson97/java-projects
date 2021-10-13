package Boxes;

import java.awt.Color;
import java.awt.Graphics;

public class EmptyBox extends Box{

	public EmptyBox(int row, int col) {
		super(row, col, Color.white);
	}
	
	public void renderDebug(Graphics g){
		g.setColor(Color.black);
		g.drawRect(col*boxSize, row*boxSize, boxSize, boxSize);
	}

}
