package com.kinselection.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Individual extends WorldObject{
	
	Random r = new Random();
	Handler handler;
	
	private int hp = hpCap;
	private int relative = 0;
	private int tempDif = 100;
	
	private int event, fertChange, kindChange, hpColor, newX, newY;
	

	public Individual(int x, int y, int gen, int fert, int kind, int hpCap, Handler handler) {
		super(x, y, gen, fert, kind, hpCap);
		this.handler = handler;
		
		
	}

	public void tick() {
		
		age +=1;
		
		if(hp > 0) hp -= 1;
		else handler.removeObject(this);
		
		event = r.nextInt(10);
		
		if(event <= 4){
			if( this.age >= fert){
				childNumb+=1;
				
				// Randomizing either the R,G, or B of parent color
//				event = r.nextInt(3);
//				if(event == 0) color = new Color((int) World.clamp( (color.getRed() - r.nextInt(20) - 10), 0, 225), color.getBlue(), color.getGreen() );		
//				else if(event == 1) color = new Color( color.getRed(), (int) World.clamp( (color.getBlue() - r.nextInt(20) - 10), 0, 225), color.getGreen() );
//				else if(event == 2) color = new Color(color.getRed(), color.getBlue(), (int) World.clamp( (color.getGreen() - r.nextInt(20) - 10), 0, 225) );
				
				if (x < 50){
					newX = 400;
					newY = y + 30;
				}
				else newX = (x-30) + (childNumb * 15);
				
				if (y > 750){
					newX = 245;
					newY = 10;
				}
				else newY = y + 15;
				
				event = r.nextInt(20);
				if(event > 14) kindChange = r.nextInt(3)-1;
				else if(event == 0) kindChange = r.nextInt(20)-10;
				else kindChange = 0;
				
				event = r.nextInt(20);
				if(event > 14) fertChange = r.nextInt(3)-1;
				else if(event == 0) fertChange = r.nextInt(10)-5;
				else fertChange = 0;
				
				if(kind >=16) hpCap -=1;
				
				WorldObject offspring = new Individual( newX, newY , this.getGen()+1, (int) World.clamp(fert+fertChange, 1, 10), (int) World.clamp(kind+kindChange, 2, 20), hpCap, handler);

				handler.addObject(offspring);
				
			}
			
		}
		
		else if(event <= 8) {
			if(World.plague > 10) hpCap -=1;
			hp -= World.plague;
		}
		
		else if(event == 9){
			hp -= (int) hp / kind;
			for(int i=0; i < handler.object.size(); i++){
				WorldObject tempObject = handler.object.get(i);
				
				if( Math.abs(tempObject.getGen()-this.getGen()) < tempDif || tempObject.getKind() < tempDif && tempObject.hp < (int) hp/kind){
					tempDif = Math.abs(tempObject.getGen()-this.getGen());
					relative = i;
				}
				if(relative != 0 && relative < handler.object.size()){
					WorldObject relativeObject = handler.object.get(relative);
					relativeObject.hp += (int) hp / kind *2;
				}
			}
		}
		
		hp -= World.famine;
		if(World.famine >= 10){
			hpCap -= 1;
		}
		
	}

	public void render(Graphics g) {
			
		g.setColor(new Color( (int) (kind * 11.25), 225, 180));
		g.fillOval(x, y, 10, 10);
		
		hp = (int) World.clamp(hp, 0, 10);
		hpColor = (int) (22.5 * hp);
		g.setColor( new Color(hpColor,hpColor,hpColor));
		g.fillOval(x+3, y+3, 5, 5);
		
	}


}
