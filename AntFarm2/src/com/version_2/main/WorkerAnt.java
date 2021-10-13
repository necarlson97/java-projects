package com.version_2.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class WorkerAnt extends WorldObject{
	
	Random r = new Random();
	Handler handler;
	
	private String task;
	
	private int velX, velY;
	
	
	private ArrayList gpx = new ArrayList(Arrays.asList(10,50,r.nextInt(110),110+r.nextInt(50)-25) );
	private ArrayList gpy = new ArrayList(Arrays.asList(50,50,r.nextInt(110),110+r.nextInt(50)-25) );
	
	private int gpi = 1;
	
	private int weight = 0;
	private String prevTask;

	public WorkerAnt(float x, float y, String task, Handler handler) {
		super(x, y);
		this.handler = handler;
		this.task = task;
		
			
	}

	public void tick() {
		
		System.out.println(weight+" gpi"+gpi+" t"+task);
		
		if(weight == 50){
			prevTask = task;
			task = "dump";
		}
			
		
		if(task == "build"){
			if(x == (int) gpx.get(gpi) && y == (int) gpy.get(gpi) && gpi < gpx.size() ) {
				gpi = (int) World.clamp(gpi+=1, 1, gpx.size() -1 );
			}
		}
		
		if(task == "dump"){
			if (x == (int) gpx.get(gpi) && y == (int) gpy.get(gpi)){
				if(gpi == 0 || gpi == 1){
					weight = 0;
					task = prevTask;
				}
				else gpi -=1;
			}
		}
		
		// Gravity
		if(y<49) velY = 1;
		//Movements
		else{
				
			if(x < (int) gpx.get(gpi)) velX = 1;
			else if(x == (int) gpx.get(gpi)) velX = 0;
			else if(x > (int) gpx.get(gpi)) velX =-1;
			
			if(x < (int) gpx.get(gpi)) velY = 1;
			else if(y == (int)  gpy.get(gpi)) velY = 0;
			else if(y > (int) gpy.get(gpi)) velY = -1;
			
		}
		
		
		if(World.pixels[(int) ( x+ y*200 )] == 0x1f0f00){
			World.pixels[(int) ( x+ y*200 )] = 0xFFCC66;
			weight+=1;
		}
		
		if(World.pixels[(int) ( x+1+ y*200 )] == 0x1f0f00) World.pixels[(int) ( x+1+ y*200 )] = 0xFFCC66;
		if(World.pixels[(int) ( x+2+ y*200 )] == 0x1f0f00) World.pixels[(int) ( x+2+ y*200 )] = 0xFFCC66;
		
		if(World.pixels[(int) ( x+ (y+1)*200 )] == 0x1f0f00) World.pixels[(int) ( x+ (y+1)*200 )] = 0xFFCC66;
		if(World.pixels[(int) ( x+2+ (y+1)*200 )] == 0x1f0f00) World.pixels[(int) ( x+2+ (y+1)*200 )] = 0xFFCC66;
		
		if(World.pixels[(int) ( x+ (y+2)*200 )] == 0x1f0f00) World.pixels[(int) ( x+ (y+2)*200 )] = 0xFFCC66;
		if(World.pixels[(int) ( x+2+ (y+2)*200 )] == 0x1f0f00) World.pixels[(int) ( x+2+ (y+2)*200 )] = 0xFFCC66;
		
		if(World.pixels[(int) ( x+ (y+3)*200 )] == 0x1f0f00) World.pixels[(int) ( x+ (y+3)*200 )] = 0xFFCC66;
		if(World.pixels[(int) ( x+2+ (y+3)*200 )] == 0x1f0f00) World.pixels[(int) ( x+2+ (y+3)*200 )] = 0xFFCC66;
		
		if(World.pixels[(int) ( x+ (y+4)*200 )] == 0x1f0f00) World.pixels[(int) ( x+ (y+4)*200 )] = 0xFFCC66;
		if(World.pixels[(int) ( x+1+ (y+4)*200 )] == 0x1f0f00) World.pixels[(int) ( x+1+ (y+4)*200 )] = 0xFFCC66;
		if(World.pixels[(int) ( x+2+ (y+4)*200 )] == 0x1f0f00){
			World.pixels[(int) ( x+2+ (y+4)*200 )] = 0xFFCC66;
			weight+=1;
		}
		
		x+=velX;
		y+=velY;
		
	}

	public void render(Graphics g) {
			
		g.setColor(Color.black);
		g.fillRect((int) x, (int) y, 3, 5);
		
	}


}
