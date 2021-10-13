import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Player extends Duelist implements Runnable{
	
	int deathCount = 300;
	
	public Player(int x, int y){
		super(x, y, new Color(225, 190, 64),"Duel Player v2.png");
	
		gunBarrelX = x+50;
		gunBarrelY = y+75;
		torsoX = x+30;
		torsoY = y+60;
		
		drawCounter = -1;
	}
	
	public void run(){
		
		if(!alive) deathCount --;
		if(deathCount == 0) Duel.player = new Player(x, y);
		
		if(drawCounter >0) {
			drawingGun = true;
			gunDrawn = false;
			drawCounter --;
		}
		else if(drawCounter == 0){ drawingGun = false; gunDrawn = true; }
		else{ drawingGun = false; gunDrawn = false; }
		
	}
	
	public void render(Graphics g){
		super.render(g, duelistColor);
	}
	

}
