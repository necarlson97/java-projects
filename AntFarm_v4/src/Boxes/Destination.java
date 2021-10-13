package Boxes;

import java.awt.Color;

public abstract class Destination extends Box{
	
	String name;

	public Destination(int row, int col, Color color, String name) {
		super(row, col, color);
		this.name = name;
	}

}
