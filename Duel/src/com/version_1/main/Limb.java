package com.version_1.main;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Limb extends StageObject{
	
	Random r = new Random();
	Handler handler;
	
	private int upperArmX[] = {10,10,110,110};
    private int upperArmY[] = {110,120,170,160};
	private Polygon upperArm = new Polygon(upperArmX, upperArmY, upperArmX.length);
	

	public Limb(int x, int y, Handler handler) {
		super(x, y);
		this.handler = handler;
		
		
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
			
		g.setColor(Color.white);
		g.
		
		g.fillPolygon(upperArm);
	
		
	}


}
